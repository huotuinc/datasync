package com.huobanplus.goodsync.datacenter.dao;

import com.huobanplus.goodsync.datacenter.bean.MallGoodsLvPrice;
import com.huobanplus.goodsync.datacenter.bean.MallProductBean;
import com.huobanplus.goodsync.datacenter.model.PriceDesc;

import java.util.List;

/**
 * Created by liual on 2015-12-08.
 */
public interface BulkDiscountDao {
    void productDiscount(int goodId, double discount);

    void goodDiscount(int goodId, double discount);

    List<PriceDesc> findMinMax(int goodId);

    void updatePriceDesc(int goodId, String priceDesc);


    void deleteLvPriceByGoodId(int goodId);

    void saveGoodLvPrice(MallGoodsLvPrice mallGoodsLvPrice);

    List<MallProductBean> findUserPriceInfoByGoodId(int goodId);

    void updateProductUserPriceInfo(int productId, String userPriceInfo);
}
