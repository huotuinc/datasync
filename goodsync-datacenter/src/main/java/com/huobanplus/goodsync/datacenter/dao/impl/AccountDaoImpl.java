package com.huobanplus.goodsync.datacenter.dao.impl;

import com.huobanplus.goodsync.datacenter.dao.AccountDao;
import com.huobanplus.goodsync.datacenter.dao.impl.BaseDaoImpl;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by liual on 2015-09-02.
 */
@Repository
public class AccountDaoImpl extends BaseDaoImpl implements AccountDao {

    @Override
    public int findByAccountAndPassword(String account, String password) {
        String sql = "SELECT SC_UserID FROM Swt_CustomerManage WHERE SC_UserLoginName=? AND SC_UserLoginPassword=?";
        List<Integer> list = jdbcTemplate.query(sql, new Object[]{account, password}, (rs, rowNum) -> {
            return rs.getInt("SC_UserID");
        });
        if (list.size() == 0) {
            return 0;
        } else {
            return list.get(0);
        }
    }
}
