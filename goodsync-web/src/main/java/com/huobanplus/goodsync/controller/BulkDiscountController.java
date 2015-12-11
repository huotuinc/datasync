package com.huobanplus.goodsync.controller;

import com.huobanplus.goodsync.common.ApiResult;
import com.huobanplus.goodsync.common.ResultCodeEnum;
import com.huobanplus.goodsync.datacenter.service.BulkDiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by liual on 2015-12-08.
 */
@Controller
public class BulkDiscountController {
    @Autowired
    private BulkDiscountService bulkDiscountService;

    @RequestMapping(value = "/bulkDiscount", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult bulkDiscount(String goodListStr, double discount) {
        String[] goodList = goodListStr.split(",");
        for (String s : goodList) {
            bulkDiscountService.goodDiscount(Integer.parseInt(s), discount);
        }
        return ApiResult.resultWith(ResultCodeEnum.SUCCESS.getResultCode(), ResultCodeEnum.SUCCESS.getMsg());
    }
}
