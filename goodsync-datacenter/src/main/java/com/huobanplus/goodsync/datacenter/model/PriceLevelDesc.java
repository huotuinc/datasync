package com.huobanplus.goodsync.datacenter.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * Created by allan on 5/9/16.
 */
@Data
public class PriceLevelDesc {
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
}
