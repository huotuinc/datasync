package com.huobanplus.goodsync.datacenter.model;

import com.alibaba.fastjson.annotation.JSONField;

public class PriceDesc {
    @JSONField(name = "LevelId")
    private int levelId;
    @JSONField(name = "MinPrice")
    private double minPrice;
    @JSONField(name = "MaxPrice")
    private double maxPrice;
    @JSONField(name = "MinIntegral")
    private int minIntegral;
    @JSONField(name = "MaxIntegral")
    private int maxIntegral;

    public int getLevelId() {
        return levelId;
    }

    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public int getMinIntegral() {
        return minIntegral;
    }

    public void setMinIntegral(int minIntegral) {
        this.minIntegral = minIntegral;
    }

    public int getMaxIntegral() {
        return maxIntegral;
    }

    public void setMaxIntegral(int maxIntegral) {
        this.maxIntegral = maxIntegral;
    }
}