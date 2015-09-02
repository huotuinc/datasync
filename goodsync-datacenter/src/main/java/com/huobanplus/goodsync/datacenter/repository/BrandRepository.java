package com.huobanplus.goodsync.datacenter.repository;

import com.huobanplus.goodsync.datacenter.bean.MallBrandBean;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by liual on 2015-09-02.
 */
public interface BrandRepository extends JpaRepository<MallBrandBean, Integer> {
    List<MallBrandBean> findByCustomerId(int customerId);
}
