package com.huobanplus.goodsync.datacenter.repository;

import com.huobanplus.goodsync.datacenter.bean.MallGImagesBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by liual on 2015-09-02.
 */
public interface GImagesRepository extends JpaRepository<MallGImagesBean, Integer> {
    List<MallGImagesBean> findByCustomerId(int customerId);

    @Modifying
    @Query("delete from MallGImagesBean where customerId=?1")
    void deleteByCustomerId(int customerId);
}
