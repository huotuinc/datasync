package com.huobanplus.goodsync.intercepter;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 商家登录
 * Created by liual on 2015-09-01.
 */
@Component
public class AccountInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object customerId = request.getSession().getAttribute("customerId");
        if (customerId == null) {
            response.sendRedirect("login.jsp");
            return false;
        }
        return true;
    }
}
