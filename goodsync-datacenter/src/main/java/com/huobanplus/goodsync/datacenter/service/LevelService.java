package com.huobanplus.goodsync.datacenter.service;

import com.huobanplus.goodsync.datacenter.bean.MallLevel;

import java.util.List;

/**
 * Created by allan on 5/9/16.
 */
public interface LevelService {
    List<MallLevel> findByCustomerId(int customerId);
}
