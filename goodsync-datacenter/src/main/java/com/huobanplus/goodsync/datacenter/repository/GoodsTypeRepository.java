package com.huobanplus.goodsync.datacenter.repository;

import com.huobanplus.goodsync.datacenter.bean.MallGoodsTypeBean;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by liual on 2015-09-02.
 */
public interface GoodsTypeRepository extends JpaRepository<MallGoodsTypeBean, Integer> {
    List<MallGoodsTypeBean> findByCustomerId(int customerId);
}
