package com.huobanplus.goodsync.datacenter.service.impl;

import com.huobanplus.goodsync.datacenter.bean.MallGoodsTypeBean;
import com.huobanplus.goodsync.datacenter.bean.MallSyncInfoBean;
import com.huobanplus.goodsync.datacenter.bean.SyncResultBean;
import com.huobanplus.goodsync.datacenter.common.ClassHandler;
import com.huobanplus.goodsync.datacenter.common.Constant;
import com.huobanplus.goodsync.datacenter.common.Message;
import com.huobanplus.goodsync.datacenter.common.PreBatchDel;
import com.huobanplus.goodsync.datacenter.repository.GoodsTypeRepository;
import com.huobanplus.goodsync.datacenter.service.GoodsTypeService;
import com.huobanplus.goodsync.datacenter.service.SyncInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liual on 2015-09-02.
 */
@Service
@Transactional
public class GoodsTypeServiceImpl implements GoodsTypeService {
    @Autowired
    private GoodsTypeRepository goodsTypeRepository;
    @Autowired
    private SyncInfoService syncInfoService;

    @Override
    public MallGoodsTypeBean save(MallGoodsTypeBean goodsTypeBean) {
        return goodsTypeRepository.save(goodsTypeBean);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MallGoodsTypeBean> findByCustomerId(int customerId) {
        return goodsTypeRepository.findByCustomerId(customerId);
    }

    @Override
    @Message(operation = "保存", desc = "商品类型信息")
    public SyncResultBean<MallGoodsTypeBean> batchSave(int targetCustomerId, List<MallGoodsTypeBean> originalBeans) throws CloneNotSupportedException {
        List<MallGoodsTypeBean> targetGoodsType = new ArrayList<>();
        List<MallSyncInfoBean> syncInfoList = new ArrayList<>();
        for (MallGoodsTypeBean original : originalBeans) {
            MallSyncInfoBean syncInfo = new MallSyncInfoBean();
            syncInfo.setFromId(original.getTypeId());
            syncInfo.setFromCustomerId(original.getCustomerId());
            MallGoodsTypeBean target = (MallGoodsTypeBean) original.clone();
            target.setTypeId(null);
            target.setCustomerId(targetCustomerId);
            target = goodsTypeRepository.saveAndFlush(target);
            syncInfo.setToId(target.getTypeId());
            syncInfo.setToCustomerId(targetCustomerId);
            syncInfo.setType(Constant.GOOD_TYPE);
            syncInfo = syncInfoService.save(syncInfo);
            syncInfoList.add(syncInfo);
            targetGoodsType.add(target);
        }
        return new SyncResultBean<>(targetGoodsType, syncInfoList);
    }

    @Override
    @Message(operation = "更新", desc = "商品类型信息")
    public List<MallGoodsTypeBean> batchUpdate(int targetCustomerId, List<MallGoodsTypeBean> originalType, List<MallSyncInfoBean> syncInfoList)
            throws IllegalAccessException, InvocationTargetException, InstantiationException, CloneNotSupportedException {
        List<MallGoodsTypeBean> targetGoodsType = new ArrayList<>();
        for (MallGoodsTypeBean original : originalType) {
            int targetId = syncInfoService.getTargetId(original.getTypeId(), Constant.GOOD_TYPE, syncInfoList);
            if (targetId > 0) {
                MallGoodsTypeBean targetType = goodsTypeRepository.findOne(targetId);
                ClassHandler.ClassCopy(original, targetType);
                targetType.setCustomerId(targetCustomerId);
                targetGoodsType.add(targetType);
                goodsTypeRepository.saveAndFlush(targetType);
            } else {
                MallSyncInfoBean syncInfo = new MallSyncInfoBean();
                syncInfo.setFromId(original.getTypeId());
                syncInfo.setFromCustomerId(original.getCustomerId());
                MallGoodsTypeBean targetType = (MallGoodsTypeBean) original.clone();
                targetType.setTypeId(null);
                targetType.setCustomerId(targetCustomerId);
                targetType = goodsTypeRepository.saveAndFlush(targetType);
                syncInfo.setToId(targetType.getTypeId());
                syncInfo.setToCustomerId(targetCustomerId);
                syncInfo = syncInfoService.save(syncInfo);
                syncInfoList.add(syncInfo);
                targetGoodsType.add(targetType);
            }
        }
        return targetGoodsType;
    }

    @Override
    public void deleteByCustomerId(int customerId) {
        goodsTypeRepository.deleteByCustomerId(customerId);
    }
}
