package com.huobanplus.goodsync.datacenter.service;

import com.huobanplus.goodsync.datacenter.bean.MallProductBean;

import java.util.List;

/**
 * 货品
 * Created by liual on 2015-09-01.
 */
public interface ProductService {
    /**
     * 得到某商户的货品列表
     *
     * @param customerId
     * @return
     */
    List<MallProductBean> findByCustomerId(int customerId);

    /**
     * 保存
     *
     * @param productBean
     * @return
     */
    MallProductBean save(MallProductBean productBean);

    /**
     * 批量保存
     *
     * @param productBeans
     */
    void batchSave(List<MallProductBean> productBeans);
}
