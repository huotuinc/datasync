package com.huobanplus.goodsync.datacenter.service.impl;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huobanplus.goodsync.datacenter.bean.*;
import com.huobanplus.goodsync.datacenter.common.ClassHandler;
import com.huobanplus.goodsync.datacenter.common.Constant;
import com.huobanplus.goodsync.datacenter.common.Message;
import com.huobanplus.goodsync.datacenter.common.PreBatchDel;
import com.huobanplus.goodsync.datacenter.json.GoodPdtDesc;
import com.huobanplus.goodsync.datacenter.json.GoodSpecDesc;
import com.huobanplus.goodsync.datacenter.repository.GoodsRepository;
import com.huobanplus.goodsync.datacenter.service.BrandService;
import com.huobanplus.goodsync.datacenter.service.GoodsService;
import com.huobanplus.goodsync.datacenter.service.SyncInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

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
    public MallGoodsBean findByGoodsId(int goodsId) {
        return goodsRepository.findOne(goodsId);
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
    @Message(operation = "保存", desc = "商品信息")
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
            target.setLastModify(new Date());
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
    @Message(operation = "更新", desc = "商品信息")
    public List<MallGoodsBean> batchUpdate(List<MallGoodsBean> originalGoods, List<MallSyncInfoBean> syncInfoList, int targetCustomerId)
            throws IllegalAccessException, InvocationTargetException, InstantiationException, CloneNotSupportedException {
        List<MallGoodsBean> targetGoodList = new ArrayList<>();
        for (MallGoodsBean original : originalGoods) {
            int targetId = syncInfoService.getTargetId(original.getGoodsId(), Constant.GOOD, syncInfoList);
            if (targetId > 0) {
                MallGoodsBean targetGood = goodsRepository.findOne(targetId);
                String priceLevelDesc = targetGood.getPriceLevelDesc();
                ClassHandler.ClassCopy(original, targetGood);
                targetGood.setCustomerId(targetCustomerId);
                targetGood.setPriceLevelDesc(priceLevelDesc);
                targetGood.setLastModify(new Date());
                targetGoodList.add(targetGood);
            } else {
                MallSyncInfoBean syncInfo = new MallSyncInfoBean();
                syncInfo.setFromId(original.getGoodsId());
                syncInfo.setFromCustomerId(targetCustomerId);
                MallGoodsBean targetGood = (MallGoodsBean) original.clone();
                targetGood.setGoodsId(null);
                targetGood.setCustomerId(targetCustomerId);
                targetGood.setLastModify(new Date());
                targetGood.setPriceLevelDesc(null);
                targetGood = goodsRepository.saveAndFlush(targetGood);
                syncInfo.setToId(targetGood.getGoodsId());
                syncInfo.setToCustomerId(targetCustomerId);
                syncInfo.setType(Constant.GOOD);
                syncInfo = syncInfoService.save(syncInfo);
                syncInfoList.add(syncInfo);
                targetGoodList.add(targetGood);
            }
        }
        return targetGoodList;
    }

    @Override
    @Message(operation = "处理", desc = "商品信息关联字段")
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
    @Message(operation = "处理", desc = "商品信息关联图片字段")
    public void handleDefaultImg(List<MallGoodsBean> targetList, List<MallSyncInfoBean> goodsImgSyncInfo) {
        targetList.forEach(target -> {
            int originalImgId = StringUtils.isEmpty(target.getImageDefault()) ? 0 : Integer.parseInt(target.getImageDefault());
            int targetImgId = syncInfoService.getTargetId(originalImgId, Constant.GOOD_Img, goodsImgSyncInfo);
            target.setImageDefault(String.valueOf(targetImgId));
            goodsRepository.save(target);
        });
    }

    @Override
    @Message(operation = "处理", desc = "商品信息冗余字段")
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
    @Message(operation = "处理", desc = "商品信息关联字段")
    public void handleAssociatedAllInfo(List<MallGoodsBean> targetList, List<MallSyncInfoBean> syncInfoList) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        for (MallGoodsBean target : targetList) {
            int targetCatId = syncInfoService.getTargetId(target.getCatId(), Constant.GOOD_CAT, syncInfoList);
            int targetBrandId = syncInfoService.getTargetId(target.getBrandId(), Constant.BRAND, syncInfoList);
            int targetTypeId = syncInfoService.getTargetId(target.getTypeId(), Constant.GOOD_TYPE, syncInfoList);
            target.setCatId(targetCatId);
            target.setBrandId(targetBrandId);
            target.setTypeId(targetTypeId);

            int targetImgId = syncInfoService.getTargetId(Integer.parseInt(target.getImageDefault()), Constant.GOOD_Img, syncInfoList);
            target.setImageDefault(String.valueOf(targetImgId));

            //处理spec
            Map<String, String> originalSpec = objectMapper.readValue(target.getSpec(), Map.class);
            Map<String, String> targetSpec = new HashMap<>();
            originalSpec.entrySet().forEach(entry -> {
                int specId = Integer.parseInt(entry.getKey());
                int targetSpecId = syncInfoService.getTargetId(specId, Constant.SPEC, syncInfoList);
                targetSpec.put(String.valueOf(targetSpecId), entry.getValue());
            });
            target.setSpec(objectMapper.writeValueAsString(targetSpec));
            //处理pdt_desc
            List<Map> pdtDescList = objectMapper.readValue(target.getPdtDesc(), List.class);
            List<GoodPdtDesc> targetPdtDesc = new ArrayList<>();
            pdtDescList.forEach(pdtDesc -> {
                GoodPdtDesc goodPdtDesc = new GoodPdtDesc();
                int targetProductId = syncInfoService.getTargetId((Integer) pdtDesc.get("ProductId"), Constant.PRODUCT, syncInfoList);
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
                int targetSpecId = syncInfoService.getTargetId((Integer) specDesc.get("SpecId"), Constant.SPEC, syncInfoList);
                int targetSpecValueId = syncInfoService.getTargetId((Integer) specDesc.get("SpecValueId"), Constant.SPEC_VALUE, syncInfoList);
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
