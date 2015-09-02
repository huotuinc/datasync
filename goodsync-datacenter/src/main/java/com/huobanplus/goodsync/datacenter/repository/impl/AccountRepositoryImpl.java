package com.huobanplus.goodsync.datacenter.repository.impl;

import com.huobanplus.goodsync.datacenter.repository.AccountRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by liual on 2015-09-02.
 */
@Repository
public class AccountRepositoryImpl extends BaseDaoImpl implements AccountRepository {

    @Override
    public int findByAccountAndPassword(String account, String password) {
        String sql = "SELECT SC_UserID FROM Swt_CustomerManage WHERE SC_UserLoginName=? AND SC_UserLoginPassword=?";
        Integer customerId = jdbcTemplate.queryForObject(sql, new Object[]{account, password}, Integer.class);
        if (customerId == null)
            return 0;
        return customerId;
    }
}
