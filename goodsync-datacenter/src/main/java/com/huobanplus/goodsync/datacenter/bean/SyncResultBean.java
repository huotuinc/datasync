package com.huobanplus.goodsync.datacenter.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 数据同步结果
 * Created by liual on 2015-09-02.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SyncResultBean<T> {
    /**
     * 保存到目标后的数据列表
     */
    List<T> targetList;
    /**
     * 从源到目标后的关联信息列表
     */
    List<MallSyncInfoBean> syncInfoList;

}
