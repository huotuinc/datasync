package com.huobanplus.goodsync.datacenter.dao;

import com.huobanplus.goodsync.datacenter.bean.MallGoodsTypeSpecBean;

import java.util.List;

/**
 * Created by liual on 2015-09-05.
 */
public interface GoodsTypeSpecDao {
    List<MallGoodsTypeSpecBean> findByCustomerId(int customerId);

    int add(MallGoodsTypeSpecBean goodsTypeSpec);

    int update(MallGoodsTypeSpecBean goodsTypeSpec);
}
