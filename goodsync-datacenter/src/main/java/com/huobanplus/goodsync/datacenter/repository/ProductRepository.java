package com.huobanplus.goodsync.datacenter.repository;

import com.huobanplus.goodsync.datacenter.bean.MallProductBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by liual on 2015-09-01.
 */
public interface ProductRepository extends JpaRepository<MallProductBean, Integer> {
    List<MallProductBean> findByCustomerId(int customerId);

    @Query("select product from MallProductBean product where product.customerId=?1 and product.goodsId in ?2")
    List<MallProductBean> findByGoods(int customerId, List<Integer> goods);

    @Modifying
    @Query("delete from MallProductBean where customerId=?1")
    void deleteByCustomerId(int customerId);
    
    List<MallProductBean> findByGoodsId(int goodId);
}
