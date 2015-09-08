package com.huobanplus.goodsync.datacenter.service;

import com.huobanplus.goodsync.datacenter.bean.MallProductBean;
import com.huobanplus.goodsync.datacenter.config.DataCenterConfig;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by liual on 2015-09-07.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DataCenterConfig.class})
@ActiveProfiles("test")
@Transactional
public class ProductServiceTest {
    @Autowired
    private ProductService productService;
    @Autowired
    private BrandService brandService;

    @Test
    @Ignore
    public void testHandleAssociatedInfo() throws Exception {
        List<MallProductBean> list = productService.findByCustomerId(5);
        productService.handleAssociatedInfo(list, null, null, null);
    }

    @Test
    public void testBatchSave() throws Exception {
//        productService.batchSave(5, null);
//        brandService.batchSave(5, null);

        productService.deleteByCustomerId(5);
    }
}