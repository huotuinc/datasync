package com.huobanplus.goodsync.datacenter.service;

import com.huobanplus.goodsync.datacenter.bean.MallSpecValuesBean;
import com.huobanplus.goodsync.datacenter.bean.MallSyncInfoBean;
import com.huobanplus.goodsync.datacenter.bean.SyncResultBean;

import java.util.List;

/**
 * 规格值
 * Created by liual on 2015-09-02.
 */
public interface SpecValueService {
    /**
     * 保存规格值实体
     *
     * @param specValue
     * @return
     */
    MallSpecValuesBean save(MallSpecValuesBean specValue);

    /**
     * 得到对应商户的规格值列表
     *
     * @param customerId
     * @return
     */
    List<MallSpecValuesBean> findByCustomerId(int customerId);

    /**
     * 导入规格值到目标商户
     *
     * @param originalSpecValue
     * @param targetCustomerId
     * @return
     */
    SyncResultBean<MallSpecValuesBean> batchSave(List<MallSpecValuesBean> originalSpecValue, int targetCustomerId);

    /**
     * 处理关联的规格id字段
     *
     * @param targetList
     * @param specSyncInfoList
     */
    void handleSpecId(List<MallSpecValuesBean> targetList, List<MallSyncInfoBean> specSyncInfoList);
}