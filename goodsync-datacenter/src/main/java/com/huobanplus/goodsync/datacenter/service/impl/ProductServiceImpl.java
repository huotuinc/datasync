package com.huobanplus.goodsync.datacenter.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huobanplus.goodsync.datacenter.bean.MallProductBean;
import com.huobanplus.goodsync.datacenter.bean.MallSyncInfoBean;
import com.huobanplus.goodsync.datacenter.bean.SyncResultBean;
import com.huobanplus.goodsync.datacenter.common.Constant;
import com.huobanplus.goodsync.datacenter.json.ProductProps;
import com.huobanplus.goodsync.datacenter.repository.ProductRepository;
import com.huobanplus.goodsync.datacenter.service.ProductService;
import com.huobanplus.goodsync.datacenter.service.SyncInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liual on 2015-09-01.
 */
@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private SyncInfoService syncInfoService;

    @Transactional(readOnly = true)
    @Override
    public List<MallProductBean> findByCustomerId(int customerId) {
        return productRepository.findByCustomerId(customerId);
    }

    @Override
    public MallProductBean save(MallProductBean productBean) {
        return productRepository.save(productBean);
    }

    @Override
    public SyncResultBean<MallProductBean> batchSave(List<MallProductBean> originalList, int targetCustomerId) {
        List<MallProductBean> targetList = new ArrayList<>();
        List<MallSyncInfoBean> syncInfoList = new ArrayList<>();
        originalList.forEach(original -> {
            MallSyncInfoBean syncInfo = new MallSyncInfoBean();
            syncInfo.setFromId(original.getProductId());
            syncInfo.setFromCustomerId(original.getCustomerId());
            original.setProductId(null);
            original.setCustomerId(targetCustomerId);
            original.setUserIntegralInfo(null);
            original.setUserPriceInfo(null);
            original.setTestUserIntegralInfo(null);
            MallProductBean target = productRepository.save(original);
            syncInfo.setToId(target.getProductId());
            syncInfo.setToCustomerId(targetCustomerId);
            syncInfo.setType(Constant.PRODUCT);
            syncInfo = syncInfoService.save(syncInfo);
            targetList.add(target);
            syncInfoList.add(syncInfo);
        });
        return new SyncResultBean<>(targetList, syncInfoList);
    }

    @Override
    public void handleAssociatedInfo(List<MallProductBean> targetList, List<MallSyncInfoBean> goodSyncInfoList, List<MallSyncInfoBean> specSyncInfoList, List<MallSyncInfoBean> specValueSyncInfoList) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        for (MallProductBean target : targetList) {
            int targetGoodId = syncInfoService.getTargetId(target.getGoodsId(), Constant.GOOD, goodSyncInfoList);
            target.setGoodsId(targetGoodId);
            String originalProps = target.getProps();
            List<ProductProps> propsList = objectMapper.readValue(originalProps, List.class);
            propsList.forEach(prop -> {
                int targetSpecId = syncInfoService.getTargetId(prop.getSpecId(), Constant.SPEC, specSyncInfoList);
                int targetSpecValueId = syncInfoService.getTargetId(prop.getSpecValueId(), Constant.SPEC_VALUE, specValueSyncInfoList);
                prop.setSpecId(targetSpecId);
                prop.setSpecValueId(targetSpecValueId);
            });
            target.setProps(objectMapper.writeValueAsString(propsList));
            productRepository.save(target);
        }
    }
}
