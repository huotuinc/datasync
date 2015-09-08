package com.huobanplus.goodsync.datacenter.repository;

import com.huobanplus.goodsync.datacenter.bean.MallGoodsTypeBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by liual on 2015-09-02.
 */
public interface GoodsTypeRepository extends JpaRepository<MallGoodsTypeBean, Integer> {
    List<MallGoodsTypeBean> findByCustomerId(int customerId);

    @Query("delete from MallGoodsTypeBean where customerId=?1")
    void deleteByCustomerId(int customerId);
}
