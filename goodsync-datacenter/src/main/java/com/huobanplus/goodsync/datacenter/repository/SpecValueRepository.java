package com.huobanplus.goodsync.datacenter.repository;

import com.huobanplus.goodsync.datacenter.bean.MallSpecValuesBean;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by liual on 2015-09-02.
 */
public interface SpecValueRepository extends JpaRepository<MallSpecValuesBean, Integer> {
    List<MallSpecValuesBean> findByCustomerId(int customerId);
}
