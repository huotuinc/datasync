package com.huobanplus.goodsync.controller;

import com.huobanplus.goodsync.common.ApiResult;
import com.huobanplus.goodsync.common.ResultCodeEnum;
import com.huobanplus.goodsync.datacenter.bean.MallGoodsBean;
import com.huobanplus.goodsync.datacenter.service.BulkDiscountService;
import com.huobanplus.goodsync.datacenter.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by liual on 2015-12-08.
 */
@Controller
public class BulkDiscountController {
    @Autowired
    private BulkDiscountService bulkDiscountService;
    @Autowired
    private GoodsService goodsService;

    @RequestMapping(value = "/bulkDiscount", method = RequestMethod.POST)
    @ResponseBody
    @Transactional(value = "jdbcTransactionManager")
    public ApiResult bulkDiscount(String goodListStr, double discount, HttpServletRequest request) {
        if ("all".equals(goodListStr)) {
            int currentCustomerId = (int) request.getSession().getAttribute("customerId");
            List<MallGoodsBean> goodsBeans = goodsService.findByCustomerId(currentCustomerId);
            for (MallGoodsBean good : goodsBeans){
                bulkDiscountService.goodDiscount(good.getGoodsId(), discount);
            }
        } else {
            String[] goodList = goodListStr.split(",");
            for (String s : goodList) {
                bulkDiscountService.goodDiscount(Integer.parseInt(s), discount);
            }
        }
        return ApiResult.resultWith(ResultCodeEnum.SUCCESS.getResultCode(), ResultCodeEnum.SUCCESS.getMsg());
    }
}
