package com.huobanplus.goodsync.datacenter.bean;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by liual on 2015-09-01.
 */
@Data
public class MallGoodsTypeSpecBean {
    private int specId;
    private int typeId;
    private String specStyle;
    private int customerId;
}
