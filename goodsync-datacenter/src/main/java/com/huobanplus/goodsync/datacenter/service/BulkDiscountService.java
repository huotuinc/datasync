package com.huobanplus.goodsync.datacenter.service;

/**
 * Created by liual on 2015-12-08.
 */
public interface BulkDiscountService {
    void goodDiscount(int goodId, double discount);

    void userPriceDiscount(int goodId, double discount);
}
