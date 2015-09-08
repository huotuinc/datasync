package com.huobanplus.goodsync.datacenter.repository;

import com.huobanplus.goodsync.datacenter.bean.MallProductBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by liual on 2015-09-01.
 */
public interface ProductRepository extends JpaRepository<MallProductBean, Integer> {
    List<MallProductBean> findByCustomerId(int customerId);

    @Query("delete from MallProductBean where customerId=?1")
    void deleteByCustomerId(int customerId);
}
