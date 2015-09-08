package com.huobanplus.goodsync.datacenter.service;

import com.huobanplus.goodsync.datacenter.bean.MallGoodsTypeSpecBean;
import com.huobanplus.goodsync.datacenter.bean.MallSyncInfoBean;

import java.util.List;

/**
 * Created by liual on 2015-09-05.
 */
public interface GoodsTypeSpecService {
    List<MallGoodsTypeSpecBean> findByCustomerId(int customerId);

    int add(MallGoodsTypeSpecBean goodsTypeSpec);

    int update(MallGoodsTypeSpecBean goodsTypeSpec);

    /**
     * 导入信息到目标商户
     *
     * @param originalList
     * @param targetCustomerId
     * @param specSyncInfo
     * @param typeSyncInfo
     * @return
     */
    int batchSave(int targetCustomerId,List<MallGoodsTypeSpecBean> originalList, List<MallSyncInfoBean> specSyncInfo, List<MallSyncInfoBean> typeSyncInfo);

    /**
     * 删除某商户的信息
     *
     * @param customerId
     */
    void deleteByCustomerId(int customerId);
}
