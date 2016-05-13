package com.huobanplus.goodsync.datacenter.repository;

import com.huobanplus.goodsync.datacenter.bean.MallLevel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by allan on 5/9/16.
 */
public interface LevelRepository extends JpaRepository<MallLevel, Integer> {
    List<MallLevel> findByCustomerId(int customerId);
}
