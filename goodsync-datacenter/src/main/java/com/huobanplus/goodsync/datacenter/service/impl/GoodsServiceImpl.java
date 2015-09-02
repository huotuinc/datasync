package com.huobanplus.goodsync.datacenter.service.impl;

import com.huobanplus.goodsync.datacenter.bean.*;
import com.huobanplus.goodsync.datacenter.common.Constant;
import com.huobanplus.goodsync.datacenter.repository.GoodsRepository;
import com.huobanplus.goodsync.datacenter.service.BrandService;
import com.huobanplus.goodsync.datacenter.service.GoodsService;
import com.huobanplus.goodsync.datacenter.service.SyncInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
    @Autowired
    private BrandService brandService;

    @Override
    public MallGoodsBean save(MallGoodsBean goodsBean) {
        return goodsRepository.save(goodsBean);
    }

    @Override
    public List<MallGoodsBean> findByCustomerId(int customerId) {
        return goodsRepository.findByCustomerId(customerId);
    }

    @Override
    public SyncResultBean<MallGoodsBean> batchSave(List<MallGoodsBean> originalGoodsList, int targetCustomerId) {
        List<MallSyncInfoBean> syncInfoList = new ArrayList<>();
        List<MallGoodsBean> targetGoodsList = new ArrayList<>();
        originalGoodsList.forEach(originalGood -> {
            MallSyncInfoBean syncInfo = new MallSyncInfoBean();
            syncInfo.setFromId(originalGood.getGoodsId());
            syncInfo.setFromCustomerId(originalGood.getCustomerId());
            originalGood.setGoodsId(null);
            originalGood.setCustomerId(targetCustomerId);
            MallGoodsBean target = goodsRepository.save(originalGood);
            syncInfo.setToId(target.getGoodsId());
            syncInfo.setToCustomerId(targetCustomerId);
            syncInfo.setType(Constant.GOOD);
            syncInfo = syncInfoService.save(syncInfo);
            syncInfoList.add(syncInfo);
            targetGoodsList.add(target);
        });
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
}
