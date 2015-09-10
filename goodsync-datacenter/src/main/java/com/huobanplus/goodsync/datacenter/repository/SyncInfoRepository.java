package com.huobanplus.goodsync.datacenter.repository;

import com.huobanplus.goodsync.datacenter.bean.MallSyncInfoBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by liual on 2015-09-02.
 */
public interface SyncInfoRepository extends JpaRepository<MallSyncInfoBean, Integer> {
    @Modifying
    @Query("delete from MallSyncInfoBean where toCustomerId=?1")
    void deleteByCustomerId(int customerId);

    int countByToCustomerId(int customerId);

    List<MallSyncInfoBean> findByToCustomerId(int toCustomerId);
}
