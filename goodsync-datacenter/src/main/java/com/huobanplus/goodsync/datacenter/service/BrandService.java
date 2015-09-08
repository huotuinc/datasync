package com.huobanplus.goodsync.datacenter.service;

import com.huobanplus.goodsync.datacenter.bean.MallBrandBean;
import com.huobanplus.goodsync.datacenter.bean.MallSyncInfoBean;

import java.util.List;

/**
 * 品牌
 * Created by liual on 2015-09-02.
 */
public interface BrandService extends BatchDeletable {
    /**
     * 保存品牌信息
     *
     * @param brandBean
     * @return
     */
    MallBrandBean save(MallBrandBean brandBean);

    /**
     * 得到某商户的所有品牌信息
     *
     * @param customerId
     * @return
     */
    List<MallBrandBean> findByCustomerId(int customerId);

    /**
     * 批量保存
     *
     * @param originalBrand 原始brand，及from
     * @param customerId    目标商户id
     * @return 返回from和to 的关系数据
     */
    List<MallSyncInfoBean> batchSave(int customerId, List<MallBrandBean> originalBrand) throws CloneNotSupportedException;

    /**
     * 得到品牌实体
     *
     * @param brandId
     * @return
     */
    MallBrandBean findByBrandId(int brandId);

    /**
     * 删除某商户的信息
     *
     * @param customerId
     */
    void deleteByCustomerId(int customerId);
}
