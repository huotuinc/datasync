package com.huobanplus.goodsync.controller;

import com.huobanplus.goodsync.common.ApiResult;
import com.huobanplus.goodsync.common.ResultCodeEnum;
import com.huobanplus.goodsync.datacenter.bean.MallGoodsBean;
import com.huobanplus.goodsync.datacenter.bean.MallGoodsCatBean;
import com.huobanplus.goodsync.datacenter.bean.MallLevel;
import com.huobanplus.goodsync.datacenter.service.GoodsCatService;
import com.huobanplus.goodsync.datacenter.service.GoodsService;
import com.huobanplus.goodsync.datacenter.service.LevelService;
import com.huobanplus.goodsync.datacenter.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by allan on 5/9/16.
 */
@Controller
public class BatchSetUserPriceController {
    @Autowired
    private GoodsCatService goodsCatService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private ProductService productService;
    @Autowired
    private LevelService levelService;

    @RequestMapping(value = "/catList")
    private String catList(HttpServletRequest request, Model model) {
        int currentCustomerId = (int) request.getSession().getAttribute("customerId");
        List<MallGoodsCatBean> goodCatList = goodsCatService.findAllByCustomerId(currentCustomerId);
        model.addAttribute("goodCatList", goodCatList);
        return "good_cat_list";
    }

    @RequestMapping(value = "/batchSetUserPrice", method = RequestMethod.POST)
    @ResponseBody
    @Transactional(value = "transactionManager")
    private ApiResult bachSet(String evalStr, String cateIdList, HttpServletRequest request) {
        try {
            int currentCustomerId = (int) request.getSession().getAttribute("customerId");
            String[] cateIdArray = cateIdList.split(",");
            for (String cateIdStr : cateIdArray) {
                List<MallGoodsBean> goodsBeans = goodsService.findByCateId(Integer.parseInt(cateIdStr));
                List<MallLevel> levels = levelService.findByCustomerId(currentCustomerId);
                for (MallGoodsBean goodsBean : goodsBeans) {
                    productService.batchSetUserPrice(evalStr, goodsBean, levels);
                }
            }
            return ApiResult.resultWith(ResultCodeEnum.SUCCESS.getResultCode());
        } catch (Exception e) {
            return ApiResult.resultWith(ResultCodeEnum.SYSTEM_BAD_REQUEST.getResultCode(), e.getMessage());
        }
    }
}
