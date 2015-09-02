package com.huobanplus.goodsync.datacenter.service;

/**
 * 账户相关业务层
 * Created by liual on 2015-09-02.
 */
public interface AccountService {
    /**
     * 登录检查，返回customerId
     * <p>返回0表示登录失败</p>
     *
     * @param account  账户名
     * @param password md5转换过的密码
     * @return 0表示登录失败
     */
    int login(String account, String password);
}
