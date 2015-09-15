package com.huobanplus.goodsync.controller;

import com.huobanplus.goodsync.datacenter.bean.MallGoodsBean;
import com.huobanplus.goodsync.datacenter.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by liual on 2015-09-06.
 */
@Controller
public class IndexController {
    @Autowired
    private GoodsService goodsService;

    @RequestMapping({"/index", ""})
    public String index() {
        return "index";
    }

    @RequestMapping("/export")
    public String export(HttpServletRequest request, Model model) {
        int currentCustomerId = (int) request.getSession().getAttribute("customerId");
        List<MallGoodsBean> goodsList = goodsService.findByCustomerId(currentCustomerId);
        model.addAttribute("goodsList", goodsList);
        return "export";
    }
}
