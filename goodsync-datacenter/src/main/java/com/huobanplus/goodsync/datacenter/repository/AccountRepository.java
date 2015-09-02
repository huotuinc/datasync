package com.huobanplus.goodsync.datacenter.repository;

/**
 * 账户数据访问接口
 * Created by liual on 2015-09-02.
 */
public interface AccountRepository {
    /**
     * 登录检查
     *
     * @param account
     * @param password
     * @return 返回customerid，0表示登录失败
     */
    int findByAccountAndPassword(String account, String password);
}
