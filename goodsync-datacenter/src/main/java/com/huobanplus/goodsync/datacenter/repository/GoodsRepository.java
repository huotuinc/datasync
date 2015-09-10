package com.huobanplus.goodsync.datacenter.repository;

import com.huobanplus.goodsync.datacenter.bean.MallGoodsBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by liual on 2015-09-02.
 */
public interface GoodsRepository extends JpaRepository<MallGoodsBean, Integer> {
    List<MallGoodsBean> findByCustomerId(int customerId);

    @Query("select good from MallGoodsBean good where good.customerId=?1 and good.goodsId in ?2")
    List<MallGoodsBean> findByCustomerIdAndGoodsIdIn(int customerId, List<Integer> goodList);

    @Modifying
    @Query("delete from MallGoodsBean where customerId=?1")
    void deleteByCustomerId(int customerId);
    
}
