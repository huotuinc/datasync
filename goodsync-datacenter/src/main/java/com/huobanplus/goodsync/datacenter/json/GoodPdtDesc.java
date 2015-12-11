package com.huobanplus.goodsync.datacenter.json;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by liual on 2015-09-05.
 */
public class GoodPdtDesc {
    @JsonProperty("ProductId")
    private int ProductId;
    @JsonProperty("Desc")
    private String Desc;

    @JsonIgnore
    public int getProductId() {
        return ProductId;
    }

    @JsonIgnore
    public void setProductId(int productId) {
        ProductId = productId;
    }

    @JsonIgnore
    public String getDesc() {
        return Desc;
    }

    @JsonIgnore
    public void setDesc(String desc) {
        Desc = desc;
    }
}
