package com.huobanplus.goodsync.datacenter.json;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by liual on 2015-09-05.
 */
public class ProductProps {
    @JsonProperty("SpecId")
    private int SpecId;
    @JsonProperty("SpecValueId")
    private int SpecValueId;
    @JsonProperty("SpecValue")
    private String SpecValue;

    @JsonIgnore
    public int getSpecId() {
        return SpecId;
    }

    @JsonIgnore
    public void setSpecId(int specId) {
        SpecId = specId;
    }

    @JsonIgnore
    public int getSpecValueId() {
        return SpecValueId;
    }

    @JsonIgnore
    public void setSpecValueId(int specValueId) {
        SpecValueId = specValueId;
    }

    @JsonIgnore
    public String getSpecValue() {
        return SpecValue;
    }

    @JsonIgnore
    public void setSpecValue(String specValue) {
        SpecValue = specValue;
    }
}
