package com.huobanplus.goodsync.datacenter.service.impl;

import com.huobanplus.goodsync.datacenter.bean.MallProductBean;
import com.huobanplus.goodsync.datacenter.repository.ProductRepository;
import com.huobanplus.goodsync.datacenter.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by liual on 2015-09-01.
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<MallProductBean> findByCustomerId(int customerId) {
        return productRepository.findByCustomerId(customerId);
    }

    @Override
    public MallProductBean save(MallProductBean productBean) {
        return productRepository.save(productBean);
    }

    @Override
    public void batchSave(List<MallProductBean> productBeans) {
        
    }
}
