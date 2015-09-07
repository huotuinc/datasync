package com.huobanplus.goodsync.controller;

import com.huobanplus.goodsync.datacenter.service.AccountService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by liual on 2015-09-06.
 */
@Controller
public class AccountController {
    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public int login(String account, String password, HttpServletRequest request) {
        int customerId = accountService.login(account, DigestUtils.md5Hex(password));
        if (customerId == 0) {
            return 0;
        } else {
            request.getSession().setAttribute("customerId", customerId);
            return 1;
        }
    }
}
