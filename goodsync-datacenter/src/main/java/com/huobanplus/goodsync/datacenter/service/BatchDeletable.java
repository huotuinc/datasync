package com.huobanplus.goodsync.datacenter.service;

/**
 * Created by liual on 2015-09-08.
 */
public interface BatchDeletable {
    /**
     * 删除某商户的信息
     *
     * @param customerId
     */
    void deleteByCustomerId(int customerId);
}
