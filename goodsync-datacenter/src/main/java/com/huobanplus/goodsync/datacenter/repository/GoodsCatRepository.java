package com.huobanplus.goodsync.datacenter.repository;

import com.huobanplus.goodsync.datacenter.bean.MallGoodsCatBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by liual on 2015-09-02.
 */
public interface GoodsCatRepository extends JpaRepository<MallGoodsCatBean, Integer> {

    List<MallGoodsCatBean> findByCustomerId(int customerId);

    @Query("delete from MallGoodsCatBean where customerId=?1")
    void deleteByCustomerId(int customerId);
}
