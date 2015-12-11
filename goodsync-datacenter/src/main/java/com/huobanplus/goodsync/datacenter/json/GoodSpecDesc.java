package com.huobanplus.goodsync.datacenter.json;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by liual on 2015-09-05.
 */
public class GoodSpecDesc {
    @JsonProperty("SpecId")
    private int SpecId;
    @JsonProperty("ShowType")
    private String ShowType;
    @JsonProperty("SpecValue")
    private String SpecValue;
    @JsonProperty("SpecValueId")
    private int SpecValueId;
    @JsonProperty("SpecImage")
    private String SpecImage;
    @JsonProperty("GoodsImageIds")
    private List<String> GoodsImageIds;

    @JsonIgnore
    public int getSpecId() {
        return SpecId;
    }

    @JsonIgnore
    public void setSpecId(int specId) {
        SpecId = specId;
    }

    @JsonIgnore
    public String getShowType() {
        return ShowType;
    }

    @JsonIgnore
    public void setShowType(String showType) {
        ShowType = showType;
    }

    @JsonIgnore
    public String getSpecValue() {
        return SpecValue;
    }

    @JsonIgnore
    public void setSpecValue(String specValue) {
        SpecValue = specValue;
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
    public String getSpecImage() {
        return SpecImage;
    }

    @JsonIgnore
    public void setSpecImage(String specImage) {
        SpecImage = specImage;
    }

    @JsonIgnore
    public List<String> getGoodsImageIds() {
        return GoodsImageIds;
    }

    @JsonIgnore
    public void setGoodsImageIds(List<String> goodsImageIds) {
        GoodsImageIds = goodsImageIds;
    }
}
