package com.huobanplus.goodsync.datacenter.service;

import com.huobanplus.goodsync.datacenter.bean.MallSpecificationBean;
import com.huobanplus.goodsync.datacenter.bean.MallSyncInfoBean;
import com.huobanplus.goodsync.datacenter.bean.SyncResultBean;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * 规格
 * Created by liual on 2015-09-02.
 */
public interface SpecificationService {
    /**
     * 保存规格实体
     *
     * @param specificationBean
     * @return
     */
    MallSpecificationBean save(MallSpecificationBean specificationBean);

    /**
     * 得到某商户的规格列表
     *
     * @param customerId
     * @return
     */
    List<MallSpecificationBean> findByCustomerId(int customerId);

    /**
     * 导入规格信息到目标商户
     *
     * @param originalSpec
     * @param targetCustomerId
     * @return
     */
    SyncResultBean<MallSpecificationBean> batchSave(int targetCustomerId, List<MallSpecificationBean> originalSpec) throws CloneNotSupportedException;

    /**
     * 批量更新
     *
     * @param targetCustomerId
     * @param originalSpec
     * @param syncInfoList
     * @return
     */
    List<MallSpecificationBean> batchUpdate(int targetCustomerId, List<MallSpecificationBean> originalSpec, List<MallSyncInfoBean> syncInfoList) throws IllegalAccessException, InvocationTargetException, InstantiationException, CloneNotSupportedException;

    /**
     * 删除某商户的信息
     *
     * @param customerId
     */
    void deleteByCustomerId(int customerId);
}
