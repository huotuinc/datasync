package com.huobanplus.goodsync.datacenter.repository;

import com.huobanplus.goodsync.datacenter.bean.MallSyncInfoBean;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by liual on 2015-09-02.
 */
public interface SyncInfoRepository extends JpaRepository<MallSyncInfoBean, Integer> {
}
