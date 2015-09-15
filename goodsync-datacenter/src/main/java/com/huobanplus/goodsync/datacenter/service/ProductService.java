package com.huobanplus.goodsync.datacenter.service;

import com.fasterxml.jackson.core.JsonParseException;
import com.huobanplus.goodsync.datacenter.bean.MallProductBean;
import com.huobanplus.goodsync.datacenter.bean.MallSyncInfoBean;
import com.huobanplus.goodsync.datacenter.bean.SyncResultBean;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
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

    List<MallProductBean> findProduct(int customerId, List<Integer> goods);

    /**
     * 保存
     *
     * @param productBean
     * @return
     */
    MallProductBean save(MallProductBean productBean);

    /**
     * 导入货品到目标商户
     *
     * @param originalList
     * @param targetCustomerId
     * @return
     */
    SyncResultBean<MallProductBean> batchSave(int targetCustomerId, List<MallProductBean> originalList) throws CloneNotSupportedException;

    /**
     * 批量更新
     *
     * @param targetCustomerId
     * @param originalList
     * @param syncInfoList
     * @return
     */
    List<MallProductBean> batchUpdate(int targetCustomerId, List<MallProductBean> originalList, List<MallSyncInfoBean> syncInfoList) throws IllegalAccessException, InvocationTargetException, InstantiationException, CloneNotSupportedException;

    /**
     * 处理关联字段
     * <p>需要处理的字段</p>
     * <ul>
     * <li>goodId</li>
     * <li>props</li>
     * </ul>
     *
     * @param targetList
     * @param specSyncInfoList
     * @param specValueSyncInfoList
     */
    void handleAssociatedInfo(List<MallProductBean> targetList, List<MallSyncInfoBean> goodSyncInfoList, List<MallSyncInfoBean> specSyncInfoList, List<MallSyncInfoBean> specValueSyncInfoList)
            throws IOException;

    /**
     * 处理关联字段
     * <p>需要处理的字段，用于批量更新</p>
     * <ul>
     * <li>goodId</li>
     * <li>props</li>
     * </ul>
     *
     * @param targetList
     */
    void handleAssociatedInfo(List<MallProductBean> targetList, List<MallSyncInfoBean> syncInfo) throws IOException;

    /**
     * 删除某商户的信息
     *
     * @param customerId
     */
    void deleteByCustomerId(int customerId);
}
