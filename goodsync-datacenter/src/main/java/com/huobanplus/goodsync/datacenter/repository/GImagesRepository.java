package com.huobanplus.goodsync.datacenter.repository;

import com.huobanplus.goodsync.datacenter.bean.MallGImagesBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by liual on 2015-09-02.
 */
public interface GImagesRepository extends JpaRepository<MallGImagesBean, Integer> {
    List<MallGImagesBean> findByCustomerId(int customerId);

    @Query("select gimg from MallGImagesBean gimg where gimg.customerId=?1 and gimg.goodId in ?2")
    List<MallGImagesBean> findByGoods(int customerId, List<Integer> goodsId);

    @Modifying
    @Query("delete from MallGImagesBean where customerId=?1")
    void deleteByCustomerId(int customerId);
}
