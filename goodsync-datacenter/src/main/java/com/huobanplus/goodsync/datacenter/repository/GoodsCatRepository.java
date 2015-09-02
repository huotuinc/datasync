package com.huobanplus.goodsync.datacenter.repository;

import com.huobanplus.goodsync.datacenter.bean.MallGoodsCatBean;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by liual on 2015-09-02.
 */
public interface GoodsCatRepository extends JpaRepository<MallGoodsCatBean, Integer> {

    List<MallGoodsCatBean> findByCustomerId(int customerId);
}
