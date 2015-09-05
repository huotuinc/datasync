package com.huobanplus.goodsync.datacenter.service.impl;

import com.huobanplus.goodsync.datacenter.dao.AccountDao;
import com.huobanplus.goodsync.datacenter.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by liual on 2015-09-02.
 */
@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountDao accountDao;

    @Override
    public int login(String account, String password) {
        return accountDao.findByAccountAndPassword(account, password);
    }
}
