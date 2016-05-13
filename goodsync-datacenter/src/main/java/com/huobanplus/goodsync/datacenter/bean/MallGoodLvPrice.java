package com.huobanplus.goodsync.datacenter.bean;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by allan on 5/9/16.
 */
@Embeddable
@Setter
@Getter
public class MallGoodLvPrice {
    @Column(name = "Level_Id")
    private int level;
    @Column(name = "Goods_Id")
    private int goodsId;
    @Column(name = "Customer_Id")
    private int customerId;
    @Column(name = "Price")
    private double price;
    @Column(name = "MaxIntegral")
    private int maxIntegral;
}
