package com.huobanplus.goodsync.datacenter.service.impl;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huobanplus.goodsync.datacenter.bean.MallProductBean;
import com.huobanplus.goodsync.datacenter.bean.MallSyncInfoBean;
import com.huobanplus.goodsync.datacenter.bean.SyncResultBean;
import com.huobanplus.goodsync.datacenter.common.ClassHandler;
import com.huobanplus.goodsync.datacenter.common.Constant;
import com.huobanplus.goodsync.datacenter.common.Message;
import com.huobanplus.goodsync.datacenter.common.PreBatchDel;
import com.huobanplus.goodsync.datacenter.json.ProductProps;
import com.huobanplus.goodsync.datacenter.repository.ProductRepository;
import com.huobanplus.goodsync.datacenter.service.ProductService;
import com.huobanplus.goodsync.datacenter.service.SyncInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

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
    public List<MallProductBean> findProduct(int customerId, List<Integer> goods) {
        if (goods == null) {
            return productRepository.findByCustomerId(customerId);
        }
        return productRepository.findByGoods(customerId, goods);
    }

    @Override
    public MallProductBean save(MallProductBean productBean) {
        return productRepository.save(productBean);
    }

    @Override
    @Message(operation = "保存", desc = "货品信息")
    public SyncResultBean<MallProductBean> batchSave(int targetCustomerId, List<MallProductBean> originalList) throws CloneNotSupportedException {
        List<MallProductBean> targetList = new ArrayList<>();
        List<MallSyncInfoBean> syncInfoList = new ArrayList<>();
        for (MallProductBean original : originalList) {
            MallSyncInfoBean syncInfo = new MallSyncInfoBean();
            syncInfo.setFromId(original.getProductId());
            syncInfo.setFromCustomerId(original.getCustomerId());
            MallProductBean target = (MallProductBean) original.clone();
            target.setProductId(null);
            target.setCustomerId(targetCustomerId);
            target.setUserIntegralInfo(null);
            target.setUserPriceInfo(null);
//            target.setTestUserIntegralInfo(null);
            target.setUpTime(new Date());
            target.setLastModify(new Date());
            target = productRepository.saveAndFlush(target);
            syncInfo.setToId(target.getProductId());
            syncInfo.setToCustomerId(targetCustomerId);
            syncInfo.setType(Constant.PRODUCT);
            syncInfo = syncInfoService.save(syncInfo);
            targetList.add(target);
            syncInfoList.add(syncInfo);
        }
        return new SyncResultBean<>(targetList, syncInfoList);
    }

    @Override
    @Message(operation = "更新", desc = "货品信息")
    public List<MallProductBean> batchUpdate(int targetCustomerId, List<MallProductBean> originalList, List<MallSyncInfoBean> syncInfoList)
            throws IllegalAccessException, InvocationTargetException, InstantiationException, CloneNotSupportedException {
        List<MallProductBean> targetProductList = new ArrayList<>();
        for (MallProductBean original : originalList) {
            int targetId = syncInfoService.getTargetId(original.getProductId(), Constant.PRODUCT, syncInfoList);
            if (targetId > 0) {
                MallProductBean targetProduct = productRepository.findOne(targetId);
                ClassHandler.ClassCopy(original, targetProduct);
                targetProduct.setCustomerId(targetCustomerId);
                targetProduct.setUserIntegralInfo(null);
                targetProduct.setUserPriceInfo(null);
//                targetProduct.setTestUserIntegralInfo(null);
                targetProduct.setLastModify(new Date());
                targetProductList.add(targetProduct);
                productRepository.save(targetProduct);
            } else {
                MallSyncInfoBean syncInfo = new MallSyncInfoBean();
                syncInfo.setFromId(original.getProductId());
                syncInfo.setFromCustomerId(original.getCustomerId());
                MallProductBean targetProduct = (MallProductBean) original.clone();
                targetProduct.setCustomerId(targetCustomerId);
                targetProduct.setProductId(null);
                targetProduct.setUserIntegralInfo(null);
                targetProduct.setUserPriceInfo(null);
//                targetProduct.setTestUserIntegralInfo(null);
                targetProduct = productRepository.saveAndFlush(targetProduct);
                syncInfo.setToId(targetProduct.getProductId());
                syncInfo.setToCustomerId(targetCustomerId);
                syncInfo.setType(Constant.PRODUCT);
                syncInfo = syncInfoService.save(syncInfo);
                targetProductList.add(targetProduct);
                syncInfoList.add(syncInfo);
            }
        }
        return targetProductList;
    }

    @Override
    @Message(operation = "处理", desc = "货品关联字段")
    public void handleAssociatedInfo(List<MallProductBean> targetList, List<MallSyncInfoBean> goodSyncInfoList, List<MallSyncInfoBean> specSyncInfoList, List<MallSyncInfoBean> specValueSyncInfoList) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        for (MallProductBean target : targetList) {
            int targetGoodId = syncInfoService.getTargetId(target.getGoodsId(), Constant.GOOD, goodSyncInfoList);
            target.setGoodsId(targetGoodId);
            String originalProps = target.getProps();
            List<Map> propsList = objectMapper.readValue(originalProps, List.class);
            List<ProductProps> targetProps = new ArrayList<>();
            propsList.forEach(prop -> {
                ProductProps productProps = new ProductProps();
                int targetSpecId = syncInfoService.getTargetId((Integer) prop.get("SpecId"), Constant.SPEC, specSyncInfoList);
                int targetSpecValueId = syncInfoService.getTargetId((Integer) prop.get("SpecValueId"), Constant.SPEC_VALUE, specValueSyncInfoList);
                productProps.setSpecId(targetSpecId);
                productProps.setSpecValueId(targetSpecValueId);
                productProps.setSpecValue((String) prop.get("SpecValue"));
                targetProps.add(productProps);
            });
            target.setProps(objectMapper.writeValueAsString(targetProps));
            productRepository.save(target);
        }
    }

    @Override
    @Message(operation = "处理", desc = "货品关联字段")
    public void handleAssociatedInfo(List<MallProductBean> targetList, List<MallSyncInfoBean> syncInfo) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        for (MallProductBean target : targetList) {
            int targetGoodId = syncInfoService.getTargetId(target.getGoodsId(), Constant.GOOD, syncInfo);
            target.setGoodsId(targetGoodId);
            String originalProps = target.getProps();
            List<Map> propsList = objectMapper.readValue(originalProps, List.class);
            List<ProductProps> targetProps = new ArrayList<>();
            propsList.forEach(prop -> {
                ProductProps productProps = new ProductProps();
                int targetSpecId = syncInfoService.getTargetId((Integer) prop.get("SpecId"), Constant.SPEC, syncInfo);
                int targetSpecValueId = syncInfoService.getTargetId((Integer) prop.get("SpecValueId"), Constant.SPEC_VALUE, syncInfo);
                productProps.setSpecId(targetSpecId);
                productProps.setSpecValueId(targetSpecValueId);
                productProps.setSpecValue((String) prop.get("SpecValue"));
                targetProps.add(productProps);
            });
            target.setProps(objectMapper.writeValueAsString(targetProps));
            productRepository.save(target);
        }
    }

    @Override
    public void deleteByCustomerId(int customerId) {
        productRepository.deleteByCustomerId(customerId);
    }
}
