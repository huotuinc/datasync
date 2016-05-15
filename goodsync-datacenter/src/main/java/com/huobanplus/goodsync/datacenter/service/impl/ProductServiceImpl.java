package com.huobanplus.goodsync.datacenter.service.impl;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huobanplus.goodsync.datacenter.bean.*;
import com.huobanplus.goodsync.datacenter.common.ClassHandler;
import com.huobanplus.goodsync.datacenter.common.Constant;
import com.huobanplus.goodsync.datacenter.common.Message;
import com.huobanplus.goodsync.datacenter.json.ProductProps;
import com.huobanplus.goodsync.datacenter.model.PriceLevelDesc;
import com.huobanplus.goodsync.datacenter.repository.ProductRepository;
import com.huobanplus.goodsync.datacenter.service.GoodsService;
import com.huobanplus.goodsync.datacenter.service.LevelService;
import com.huobanplus.goodsync.datacenter.service.ProductService;
import com.huobanplus.goodsync.datacenter.service.SyncInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by liual on 2015-09-01.
 */
@Service
@Transactional(value = "transactionManager")
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private SyncInfoService syncInfoService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private LevelService levelService;

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
            MallProductBean targetProduct = productRepository.findOne(targetId);
            if (targetProduct != null) {
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
                MallProductBean newTarget = (MallProductBean) original.clone();
                newTarget.setCustomerId(targetCustomerId);
                newTarget.setProductId(null);
                newTarget.setUserIntegralInfo(null);
                newTarget.setUserPriceInfo(null);
//                newTarget.setTestUserIntegralInfo(null);
                newTarget = productRepository.saveAndFlush(newTarget);
                syncInfo.setToId(newTarget.getProductId());
                syncInfo.setToCustomerId(targetCustomerId);
                syncInfo.setType(Constant.PRODUCT);
                syncInfo = syncInfoService.save(syncInfo);
                targetProductList.add(newTarget);
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

    @Override
    public List<MallProductBean> findByGoodsId(int goodId) {
        return productRepository.findByGoodsId(goodId);
    }


    @Override
    @Transactional(value = "transactionManager")
    public void batchSetUserPrice(Map<Integer, String> levelsToSet, List<MallGoodsBean> goods, int customerId) throws ScriptException {
        ScriptEngine scriptEngine = new ScriptEngineManager().getEngineByName("JavaScript");

        List<MallLevel> levels = levelService.findByCustomerId(296);
        for (MallGoodsBean good : goods) {
            List<MallProductBean> productBeans = this.findByGoodsId(good.getGoodsId());

            double minPrice = 0, maxPrice = 0;
            for (MallProductBean product : productBeans) {
                String userPriceInfo = "";
                if (product.getGoodLvPriceList() == null || product.getGoodLvPriceList().size() == 0) {
                    List<MallGoodLvPrice> goodLvPriceList = new ArrayList<>();
                    for (MallLevel level : levels) {
                        String eval = levelsToSet.get(level.getId());
                        double resultPrice = -1; //默认的价格
                        if (!StringUtils.isEmpty(eval)) {
                            resultPrice = getResultPrice(eval, product.getCost(), product.getPrice(), product.getMktPrice(), scriptEngine);
                        }
                        MallGoodLvPrice goodLvPrice = new MallGoodLvPrice();
                        goodLvPrice.setPrice(resultPrice);
                        goodLvPrice.setCustomerId(good.getCustomerId());
                        goodLvPrice.setGoodsId(good.getGoodsId());
                        goodLvPrice.setLevel(level.getId());
                        goodLvPriceList.add(goodLvPrice);
                        //货品冗余字段

                        userPriceInfo += level.getId() + ":" + resultPrice + ":" + goodLvPrice.getMaxIntegral() + "|";

                        if (minPrice == 0 || minPrice >= resultPrice) {
                            minPrice = resultPrice;
                        }
                        if (resultPrice >= maxPrice) {
                            maxPrice = resultPrice;
                        }
                    }
                    product.setGoodLvPriceList(goodLvPriceList);
                } else {
                    for (MallGoodLvPrice goodLvPrice : product.getGoodLvPriceList()) {
                        String eval = levelsToSet.get(goodLvPrice.getLevel());
                        double resultPrice = -1;
                        if (!StringUtils.isEmpty(eval)) {
                            resultPrice = getResultPrice(eval, product.getCost(), product.getPrice(), product.getMktPrice(), scriptEngine);
                            goodLvPrice.setPrice(resultPrice);
                        }
                        //货品冗余字段
                        userPriceInfo += goodLvPrice.getLevel() + ":" + resultPrice + ":" + goodLvPrice.getMaxIntegral() + "|";

                        if (minPrice == 0 || minPrice >= resultPrice) {
                            minPrice = resultPrice;
                        }
                        if (resultPrice >= maxPrice) {
                            maxPrice = resultPrice;
                        }
                    }
                }
                //处理冗余字段
                product.setUserPriceInfo(userPriceInfo.substring(0, userPriceInfo.length() - 1));
            }

            productRepository.save(productBeans);
            //处理商品冗余字段
            if (StringUtils.isEmpty(good.getPriceLevelDesc())) {
                good.setPriceLevelDesc("[]");
            }
            List<PriceLevelDesc> priceLevelDescList = JSON.parseArray(good.getPriceLevelDesc(), PriceLevelDesc.class);
            if (priceLevelDescList.size() > 0) {
                for (PriceLevelDesc priceLevelDesc : priceLevelDescList) {
                    priceLevelDesc.setMinPrice(minPrice);
                    priceLevelDesc.setMaxPrice(maxPrice);
                }
            } else {
                priceLevelDescList = new ArrayList<>();
                for (MallLevel level : levels) {
                    PriceLevelDesc priceLevelDesc = new PriceLevelDesc();
                    priceLevelDesc.setLevelId(level.getId());
                    priceLevelDesc.setMinPrice(minPrice);
                    priceLevelDesc.setMaxPrice(maxPrice);
                    priceLevelDescList.add(priceLevelDesc);
                }
            }
            good.setPriceLevelDesc(JSON.toJSONString(priceLevelDescList));
            goodsService.save(good);
        }
    }

    /**
     * 根据公式得到目标价格
     *
     * @param eval
     * @param cost        成本价
     * @param price       销售价
     * @param marketPrice 市场价
     * @return
     */
    private double getResultPrice(String eval, double cost, double price, double marketPrice, ScriptEngine scriptEngine) throws ScriptException {
        eval = eval.replaceAll("a", String.valueOf(cost));
        eval = eval.replaceAll("b", String.valueOf(marketPrice));
        eval = eval.replaceAll("c", String.valueOf(price));
        double resultPrice = Double.parseDouble(scriptEngine.eval(eval).toString());
        resultPrice = BigDecimal.valueOf(resultPrice).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(); //目标价格

        return resultPrice;
    }
}
