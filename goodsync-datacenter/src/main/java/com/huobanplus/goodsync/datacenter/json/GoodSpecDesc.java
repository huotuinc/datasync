package com.huobanplus.goodsync.datacenter.json;

import lombok.Data;

import java.util.List;

/**
 * Created by liual on 2015-09-05.
 */
@Data
public class GoodSpecDesc {
    private int SpecId;
    private String ShowType;
    private String SpecValue;
    private int SpecValueId;
    private String SpecImage;
    private List<String> GoodsImageIds;
}
