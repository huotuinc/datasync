package com.huobanplus.goodsync.datacenter.service;

import com.huobanplus.goodsync.datacenter.bean.MallGoodsBean;

import java.util.List;

/**
 * 商品相关
 * Created by liual on 2015-09-01.
 */
public interface MallGoodService {
    /**
     * 得到相应商户的所有商品信息
     * @param customerId
     * @return
     */
    List<MallGoodsBean> findByCustomerId(int customerId);

    /**
     * 保存商品信息
     * @param goodBean
     * @return
     */
    MallGoodsBean save(MallGoodsBean goodBean);

    /**
     * 批量保存商品信息
     * @param goodBeans
     * @return
     */
    void batchSave(List<MallGoodsBean> goodBeans);
}
