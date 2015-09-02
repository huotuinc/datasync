package com.huobanplus.goodsync.datacenter.repository;

import com.huobanplus.goodsync.datacenter.bean.MallGImagesBean;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by liual on 2015-09-02.
 */
public interface GImagesRepository extends JpaRepository<MallGImagesBean, Integer> {
    List<MallGImagesBean> findByCustomerId(int customerId);
}
