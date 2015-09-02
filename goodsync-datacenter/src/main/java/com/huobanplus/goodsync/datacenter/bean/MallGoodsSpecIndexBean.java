package com.huobanplus.goodsync.datacenter.bean;

import lombok.Data;

/**
 * Created by liual on 2015-09-01.
 */
@Data
public class MallGoodsSpecIndexBean {
    private MallSpecValuesBean specValuesBean;
    private Integer specValueId;
    private String specValue;

    private MallProductBean productBean;
    private int productId;

    private MallGoodsTypeBean goodsTypeBean;
    private int typeId;

    private MallSpecificationBean specificationBean;
    private int specId;

    private MallGoodsBean goodBean;
    private int goodsId;
}
