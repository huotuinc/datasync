package com.huobanplus.goodsync.datacenter.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by liual on 2015-09-01.
 */
@Repository
public class BaseDaoImpl {
    @Autowired
    protected JdbcTemplate jdbcTemplate;
}
