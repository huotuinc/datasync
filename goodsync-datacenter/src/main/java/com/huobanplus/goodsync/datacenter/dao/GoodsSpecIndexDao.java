package com.huobanplus.goodsync.datacenter.dao;

import com.huobanplus.goodsync.datacenter.bean.MallGoodsSpecIndexBean;

import java.util.List;

/**
 * Created by liual on 2015-09-05.
 */
public interface GoodsSpecIndexDao {
    List<MallGoodsSpecIndexBean> findByCustomerId(int customerId);

    List<MallGoodsSpecIndexBean> findByGoods(int customerId, List<Integer> goods);

    int add(MallGoodsSpecIndexBean specIndex);

    void deleteByCustomerId(int customerId);
}
