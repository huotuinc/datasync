package com.huobanplus.goodsync.datacenter.service.impl;

import com.huobanplus.goodsync.datacenter.bean.MallGoodsSpecIndexBean;
import com.huobanplus.goodsync.datacenter.bean.MallSyncInfoBean;
import com.huobanplus.goodsync.datacenter.common.Constant;
import com.huobanplus.goodsync.datacenter.common.Message;
import com.huobanplus.goodsync.datacenter.common.PreBatchDel;
import com.huobanplus.goodsync.datacenter.dao.GoodsSpecIndexDao;
import com.huobanplus.goodsync.datacenter.service.GoodsSpecIndexService;
import com.huobanplus.goodsync.datacenter.service.SyncInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by liual on 2015-09-05.
 */
@Service
@Transactional
public class GoodsSpecIndexServiceImpl implements GoodsSpecIndexService {
    @Autowired
    private GoodsSpecIndexDao goodsSpecIndexDao;
    @Autowired
    private SyncInfoService syncInfoService;

    @Override
    public List<MallGoodsSpecIndexBean> findByCustomerId(int customerId) {
        return goodsSpecIndexDao.findByCustomerId(customerId);
    }

    @Override
    public List<MallGoodsSpecIndexBean> findSpecIndex(int customerId, List<Integer> goods) {
        if (goods == null) {
            return goodsSpecIndexDao.findByCustomerId(customerId);
        }
        return goodsSpecIndexDao.findByGoods(customerId, goods);
    }

    @Override
    public int add(MallGoodsSpecIndexBean specIndex) {
        return goodsSpecIndexDao.add(specIndex);
    }

    @Override
    @PreBatchDel
    @Message(operation = "保存", desc = "商品规格关联信息")
    public int batchSave(int targetCustomerId, List<MallGoodsSpecIndexBean> originalList, List<MallSyncInfoBean> typeSyncInfo, List<MallSyncInfoBean> specSyncInfo, List<MallSyncInfoBean> specValueSyncInfo, List<MallSyncInfoBean> goodSyncInfo, List<MallSyncInfoBean> productSyncInfo) {
        int index = 0;
        for (MallGoodsSpecIndexBean specIndex : originalList) {
            int targetTypeId = syncInfoService.getTargetId(specIndex.getTypeId(), Constant.GOOD_TYPE, typeSyncInfo);
            int targetSpecId = syncInfoService.getTargetId(specIndex.getSpecId(), Constant.SPEC, specSyncInfo);
            int targetSpecValueId = syncInfoService.getTargetId(specIndex.getSpecValueId(), Constant.SPEC_VALUE, specValueSyncInfo);
            int targetGoodId = syncInfoService.getTargetId(specIndex.getGoodsId(), Constant.GOOD, goodSyncInfo);
            int targetProId = syncInfoService.getTargetId(specIndex.getProductId(), Constant.PRODUCT, productSyncInfo);
            specIndex.setTypeId(targetTypeId);
            specIndex.setSpecId(targetSpecId);
            specIndex.setSpecValueId(targetSpecValueId);
            specIndex.setGoodsId(targetGoodId);
            specIndex.setProductId(targetProId);
            specIndex.setCustomerId(targetCustomerId);
            goodsSpecIndexDao.add(specIndex);
            index++;
        }
        return index;
    }

    @Override
    @PreBatchDel
    @Message(operation = "更新", desc = "商品规格关联信息")
    public int batchSave(int targetCustomerId, List<MallGoodsSpecIndexBean> originalList, List<MallSyncInfoBean> syncInfoList) {
        int index = 0;
        for (MallGoodsSpecIndexBean original : originalList) {
            int targetTypeId = syncInfoService.getTargetId(original.getTypeId(), Constant.GOOD_TYPE, syncInfoList);
            int targetSpecId = syncInfoService.getTargetId(original.getSpecId(), Constant.SPEC, syncInfoList);
            int targetSpecValueId = syncInfoService.getTargetId(original.getSpecValueId(), Constant.SPEC_VALUE, syncInfoList);
            int targetGoodId = syncInfoService.getTargetId(original.getGoodsId(), Constant.GOOD, syncInfoList);
            int targetProId = syncInfoService.getTargetId(original.getProductId(), Constant.PRODUCT, syncInfoList);
            original.setTypeId(targetTypeId);
            original.setSpecId(targetSpecId);
            original.setSpecValueId(targetSpecValueId);
            original.setGoodsId(targetGoodId);
            original.setProductId(targetProId);
            original.setCustomerId(targetCustomerId);
            goodsSpecIndexDao.add(original);
            index++;
        }
        return index;
    }

    @Override
    public void deleteByCustomerId(int customerId) {
        goodsSpecIndexDao.deleteByCustomerId(customerId);
    }
}
