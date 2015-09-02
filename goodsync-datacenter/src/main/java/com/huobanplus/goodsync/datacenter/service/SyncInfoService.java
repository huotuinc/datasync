package com.huobanplus.goodsync.datacenter.service;

import com.huobanplus.goodsync.datacenter.bean.MallSyncInfoBean;

import java.util.List;

/**
 * 前后关联记录
 * Created by liual on 2015-09-02.
 */
public interface SyncInfoService {
    MallSyncInfoBean save(MallSyncInfoBean syncInfoBean);

    /**
     * 批量插入
     *
     * @param syncInfoBeans
     * @return
     */
    List<MallSyncInfoBean> batchSave(List<MallSyncInfoBean> syncInfoBeans);

    /**
     * 根据targetId和type得到对应的源id
     *
     * @param targetId
     * @param type
     * @return
     */
    int getFromId(int targetId, String type, List<MallSyncInfoBean> syncInfoList);

    /**
     * 根据fromId和type得到对应的targetId
     *
     * @param fromId
     * @param type
     * @param syncInfoList
     * @return
     */
    int getTargetId(int fromId, String type, List<MallSyncInfoBean> syncInfoList);
}
