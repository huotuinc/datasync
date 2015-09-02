package com.huobanplus.goodsync.datacenter.repository;

import com.huobanplus.goodsync.datacenter.bean.MallGoodsBean;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by liual on 2015-09-02.
 */
public interface GoodsRepository extends JpaRepository<MallGoodsBean, Integer> {
    List<MallGoodsBean> findByCustomerId(int customerId);
}
