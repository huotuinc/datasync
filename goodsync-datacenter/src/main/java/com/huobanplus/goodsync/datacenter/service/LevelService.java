package com.huobanplus.goodsync.datacenter.service;

import com.huobanplus.goodsync.datacenter.bean.MallLevel;

import java.util.List;

/**
 * Created by allan on 5/9/16.
 */
public interface LevelService {
    List<MallLevel> findByCustomerId(int customerId);

    /**
     * 得到等级列表,先普通会员,在小伙伴的排序
     *
     * @param customerId
     * @return
     */
    List<MallLevel> findByCustomerIdWithOrder(int customerId);
}
