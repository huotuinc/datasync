package com.huobanplus.goodsync.datacenter.service;

import com.huobanplus.goodsync.datacenter.bean.MallGoodsSpecIndexBean;
import com.huobanplus.goodsync.datacenter.bean.MallSyncInfoBean;

import java.util.List;

/**
 * Created by liual on 2015-09-05.
 */
public interface GoodsSpecIndexService extends BatchDeletable {
    /**
     * 得到某商户的specIndex列表
     *
     * @param customerId
     * @return
     */
    List<MallGoodsSpecIndexBean> findByCustomerId(int customerId);

    List<MallGoodsSpecIndexBean> findSpecIndex(int customerId, List<Integer> goods);

    /**
     * 添加一个specIndex实体
     *
     * @param specIndex
     * @return
     */
    int add(MallGoodsSpecIndexBean specIndex);

    /**
     * 将specIndex信息处理完关联数据后导入到目标商户
     *
     * @return
     */
    int batchSave(int targetCustomerId,
                  List<MallGoodsSpecIndexBean> originalList,
                  List<MallSyncInfoBean> typeSyncInfo,
                  List<MallSyncInfoBean> specSyncInfo,
                  List<MallSyncInfoBean> specValueSyncInfo,
                  List<MallSyncInfoBean> goodSyncInfo,
                  List<MallSyncInfoBean> productSyncInfo);

    /**
     * 将specIndex信息处理完关联数据后导入到目标商户
     *
     * @param targetCustomerId
     * @param originalList
     * @param syncInfoList
     * @return
     */
    int batchSave(int targetCustomerId, List<MallGoodsSpecIndexBean> originalList, List<MallSyncInfoBean> syncInfoList);

    /**
     * 删除某商户的信息
     *
     * @param customerId
     */
    void deleteByCustomerId(int customerId);
}
