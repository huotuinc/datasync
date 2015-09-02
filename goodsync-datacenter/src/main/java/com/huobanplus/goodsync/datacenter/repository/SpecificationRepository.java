package com.huobanplus.goodsync.datacenter.repository;

import com.huobanplus.goodsync.datacenter.bean.MallSpecificationBean;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by liual on 2015-09-02.
 */
public interface SpecificationRepository extends JpaRepository<MallSpecificationBean, Integer> {
    List<MallSpecificationBean> findByCustomerId(int customerId);
}