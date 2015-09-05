package com.huobanplus.goodsync.datacenter.service;

import com.huobanplus.goodsync.datacenter.bean.MallProductBean;
import com.huobanplus.goodsync.datacenter.bean.MallSyncInfoBean;
import com.huobanplus.goodsync.datacenter.bean.SyncResultBean;

import java.io.IOException;
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
     * 导入货品到目标商户
     * @param originalList
     * @param targetCustomerId
     * @return
     */
    SyncResultBean<MallProductBean> batchSave(List<MallProductBean> originalList,int targetCustomerId);

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
    void handleAssociatedInfo(List<MallProductBean> targetList, List<MallSyncInfoBean> goodSyncInfoList, List<MallSyncInfoBean> specSyncInfoList, List<MallSyncInfoBean> specValueSyncInfoList) throws IOException;
}
