package com.huobanplus.goodsync.datacenter.repository;

import com.huobanplus.goodsync.datacenter.bean.MallSpecificationBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by liual on 2015-09-02.
 */
public interface SpecificationRepository extends JpaRepository<MallSpecificationBean, Integer> {
    List<MallSpecificationBean> findByCustomerId(int customerId);

    @Query("delete from MallSpecificationBean where customerId=?1")
    void deleteByCustomerId(int customerId);
}
