package com.huobanplus.goodsync.datacenter.bean;

import lombok.Data;

/**
 * Created by liual on 2015-12-08.
 */
@Data
public class MallGoodsLvPrice {
    private int productId;
    private int levelId;
    private int goodsId;
    private double price;
    private int customerId;
    private int maxIntegral;
}
