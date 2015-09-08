package com.huobanplus.goodsync.datacenter.service.impl;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huobanplus.goodsync.datacenter.bean.*;
import com.huobanplus.goodsync.datacenter.common.Constant;
import com.huobanplus.goodsync.datacenter.json.GoodPdtDesc;
import com.huobanplus.goodsync.datacenter.json.GoodSpecDesc;
import com.huobanplus.goodsync.datacenter.repository.GoodsRepository;
import com.huobanplus.goodsync.datacenter.service.BrandService;
import com.huobanplus.goodsync.datacenter.service.GoodsService;
import com.huobanplus.goodsync.datacenter.service.SyncInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liual on 2015-09-02.
 */
@Service
@Transactional
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private GoodsRepository goodsRepository;
    @Autowired
    private SyncInfoService syncInfoService;

    @Override
    public MallGoodsBean save(MallGoodsBean goodsBean) {
        return goodsRepository.save(goodsBean);
    }

    @Override
    public List<MallGoodsBean> findGoods(int customerId, List<Integer> goodList) {
        return goodList == null ? goodsRepository.findByCustomerId(customerId) : goodsRepository.findByCustomerIdAndGoodsIdIn(customerId, goodList);
    }

    @Override
    public List<MallGoodsBean> findByCustomerId(int customerId) {
        return goodsRepository.findByCustomerId(customerId);
    }

    @Override
    public SyncResultBean<MallGoodsBean> batchSave(int targetCustomerId, List<MallGoodsBean> originalGoodsList) throws CloneNotSupportedException {
        List<MallSyncInfoBean> syncInfoList = new ArrayList<>();
        List<MallGoodsBean> targetGoodsList = new ArrayList<>();
        for (MallGoodsBean originalGood : originalGoodsList) {
            MallSyncInfoBean syncInfo = new MallSyncInfoBean();
            syncInfo.setFromId(originalGood.getGoodsId());
            syncInfo.setFromCustomerId(originalGood.getCustomerId());
            MallGoodsBean target = (MallGoodsBean) originalGood.clone();
            target.setGoodsId(null);
            target.setCustomerId(targetCustomerId);
            target.setPriceLevelDesc(null);
            target.setRebateQuatoSetting(null);
            target.setRebateSaleSetting(null);
            target.setRebateMode(0);
            target.setIndividuation(0);
            target = goodsRepository.saveAndFlush(target);
            syncInfo.setToId(target.getGoodsId());
            syncInfo.setToCustomerId(targetCustomerId);
            syncInfo.setType(Constant.GOOD);
            syncInfo = syncInfoService.save(syncInfo);
            syncInfoList.add(syncInfo);
            targetGoodsList.add(target);
        }
        return new SyncResultBean<>(targetGoodsList, syncInfoList);
    }

    @Override
    public void handleAssociatedInfo(List<MallGoodsBean> targetList, List<MallSyncInfoBean> goodsCatSyncInfo, List<MallSyncInfoBean> brandSyncInfo, List<MallSyncInfoBean> goodsTypeSyncInfo) {
        targetList.forEach(targetGood -> {
            int targetCatId = syncInfoService.getTargetId(targetGood.getCatId(), Constant.GOOD_CAT, goodsCatSyncInfo);
            int targetBrandId = syncInfoService.getTargetId(targetGood.getBrandId(), Constant.BRAND, brandSyncInfo);
            int targetTypeId = syncInfoService.getTargetId(targetGood.getTypeId(), Constant.GOOD_TYPE, goodsTypeSyncInfo);
            targetGood.setCatId(targetCatId);
            targetGood.setBrandId(targetBrandId);
            targetGood.setTypeId(targetTypeId);
            goodsRepository.save(targetGood);
        });
    }

    @Override
    public void handleDefaultImg(List<MallGoodsBean> targetList, List<MallSyncInfoBean> goodsImgSyncInfo) {
        targetList.forEach(target -> {
            int targetImgId = syncInfoService.getTargetId(Integer.parseInt(target.getImageDefault()), Constant.GOOD_Img, goodsImgSyncInfo);
            target.setImageDefault(String.valueOf(targetImgId));
            goodsRepository.save(target);
        });
    }

    @Override
    public void handleSpecAndPdtInfo(List<MallGoodsBean> targetList, List<MallSyncInfoBean> specSyncInfo, List<MallSyncInfoBean> productSyncInfo, List<MallSyncInfoBean> specValueSyncInfo) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        for (MallGoodsBean target : targetList) {
            //处理spec
            Map<String, String> originalSpec = objectMapper.readValue(target.getSpec(), Map.class);
            Map<String, String> targetSpec = new HashMap<>();
            originalSpec.entrySet().forEach(entry -> {
                int specId = Integer.parseInt(entry.getKey());
                int targetSpecId = syncInfoService.getTargetId(specId, Constant.SPEC, specSyncInfo);
                targetSpec.put(String.valueOf(targetSpecId), entry.getValue());
            });
            target.setSpec(objectMapper.writeValueAsString(targetSpec));
            //处理pdt_desc
            List<Map> pdtDescList = objectMapper.readValue(target.getPdtDesc(), List.class);
            List<GoodPdtDesc> targetPdtDesc = new ArrayList<>();
            pdtDescList.forEach(pdtDesc -> {
                GoodPdtDesc goodPdtDesc = new GoodPdtDesc();
                int targetProductId = syncInfoService.getTargetId((Integer) pdtDesc.get("ProductId"), Constant.PRODUCT, productSyncInfo);
                goodPdtDesc.setProductId(targetProductId);
                goodPdtDesc.setDesc((String) pdtDesc.get("Desc"));
                targetPdtDesc.add(goodPdtDesc);
            });
            target.setPdtDesc(objectMapper.writeValueAsString(targetPdtDesc));
            //处理spec_desc
            List<Map> specDescList = objectMapper.readValue(target.getSpecDesc(), List.class);
            List<GoodSpecDesc> targetSpecDesc = new ArrayList<>();
            specDescList.forEach(specDesc -> {
                GoodSpecDesc goodSpecDesc = new GoodSpecDesc();
                int targetSpecId = syncInfoService.getTargetId((Integer) specDesc.get("SpecId"), Constant.SPEC, specSyncInfo);
                int targetSpecValueId = syncInfoService.getTargetId((Integer) specDesc.get("SpecValueId"), Constant.SPEC_VALUE, specValueSyncInfo);
                goodSpecDesc.setSpecId(targetSpecId);
                goodSpecDesc.setSpecValueId(targetSpecValueId);
                goodSpecDesc.setSpecValue((String) specDesc.get("SpecValue"));
                goodSpecDesc.setGoodsImageIds((List<String>) specDesc.get("GoodsImageIds"));
                goodSpecDesc.setShowType((String) specDesc.get("ShowType"));
                goodSpecDesc.setSpecImage((String) specDesc.get("SpecImage"));
                targetSpecDesc.add(goodSpecDesc);
            });
            target.setSpecDesc(objectMapper.writeValueAsString(targetSpecDesc));
            goodsRepository.save(target);
        }

    }

    @Override
    public void deleteByCustomerId(int customerId) {
        goodsRepository.deleteByCustomerId(customerId);
    }
}
