package com.huobanplus.goodsync.datacenter.service.impl;

import com.huobanplus.goodsync.datacenter.bean.MallSpecValuesBean;
import com.huobanplus.goodsync.datacenter.bean.MallSyncInfoBean;
import com.huobanplus.goodsync.datacenter.bean.SyncResultBean;
import com.huobanplus.goodsync.datacenter.common.ClassHandler;
import com.huobanplus.goodsync.datacenter.common.Constant;
import com.huobanplus.goodsync.datacenter.common.Message;
import com.huobanplus.goodsync.datacenter.common.PreBatchDel;
import com.huobanplus.goodsync.datacenter.repository.SpecValueRepository;
import com.huobanplus.goodsync.datacenter.service.SpecValueService;
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
@Transactional(value = "transactionManager")
public class SpecValueServiceImpl implements SpecValueService {
    @Autowired
    private SpecValueRepository specValueRepository;
    @Autowired
    private SyncInfoService syncInfoService;

    @Override
    public MallSpecValuesBean save(MallSpecValuesBean specValue) {
        return specValueRepository.save(specValue);
    }

    @Override
    public List<MallSpecValuesBean> findByCustomerId(int customerId) {
        return specValueRepository.findByCustomerId(customerId);
    }

    @Override
    @Message(operation = "保存", desc = "规格值信息")
    public SyncResultBean<MallSpecValuesBean> batchSave(int targetCustomerId, List<MallSpecValuesBean> originalSpecValue) throws CloneNotSupportedException {
        List<MallSpecValuesBean> targetList = new ArrayList<>();
        List<MallSyncInfoBean> syncInfoList = new ArrayList<>();
        for (MallSpecValuesBean original : originalSpecValue) {
            MallSyncInfoBean syncInfo = new MallSyncInfoBean();
            syncInfo.setFromId(original.getSpecValueId());
            syncInfo.setFromCustomerId(original.getCustomerId());
            MallSpecValuesBean target = (MallSpecValuesBean) original.clone();
            target.setSpecValueId(null);
            target.setCustomerId(targetCustomerId);
            target = specValueRepository.saveAndFlush(target);
            syncInfo.setToId(target.getSpecValueId());
            syncInfo.setToCustomerId(targetCustomerId);
            syncInfo.setType(Constant.SPEC_VALUE);
            syncInfo = syncInfoService.save(syncInfo);
            syncInfoList.add(syncInfo);
            targetList.add(target);
        }
        return new SyncResultBean<>(targetList, syncInfoList);
    }

    @Override
    @Message(operation = "更新", desc = "规格值信息")
    public List<MallSpecValuesBean> batchUpdate(int targetCustomerId, List<MallSpecValuesBean> originalSpecValue, List<MallSyncInfoBean> syncInfoList)
            throws IllegalAccessException, InvocationTargetException, InstantiationException, CloneNotSupportedException {
        List<MallSpecValuesBean> targetSpecValueList = new ArrayList<>();
        for (MallSpecValuesBean original : originalSpecValue) {
            int targetId = syncInfoService.getTargetId(original.getSpecValueId(), Constant.SPEC_VALUE, syncInfoList);
            MallSpecValuesBean targetSpecValue = specValueRepository.findOne(targetId);
            if (targetSpecValue != null) {
                ClassHandler.ClassCopy(original, targetSpecValue);
                targetSpecValue.setCustomerId(targetCustomerId);
                targetSpecValueList.add(targetSpecValue);
                specValueRepository.save(targetSpecValue);
            } else {
                MallSyncInfoBean syncInfo = new MallSyncInfoBean();
                syncInfo.setFromId(original.getSpecValueId());
                syncInfo.setFromCustomerId(targetCustomerId);
                MallSpecValuesBean newTarget = (MallSpecValuesBean) original.clone();
                newTarget.setSpecValueId(null);
                newTarget.setCustomerId(targetCustomerId);
                newTarget = specValueRepository.saveAndFlush(newTarget);
                syncInfo.setToId(newTarget.getSpecValueId());
                syncInfo.setToCustomerId(targetCustomerId);
                syncInfo.setType(Constant.SPEC_VALUE);
                syncInfo = syncInfoService.save(syncInfo);
                syncInfoList.add(syncInfo);
                targetSpecValueList.add(newTarget);
            }
        }
        return targetSpecValueList;
    }

    @Override
    @Message(operation = "处理", desc = "规格值关联字段")
    public void handleSpecId(List<MallSpecValuesBean> targetList, List<MallSyncInfoBean> specSyncInfoList) {
        targetList.forEach(target -> {
            int targetSpecId = syncInfoService.getTargetId(target.getSpecId(), Constant.SPEC, specSyncInfoList);
            target.setSpecId(targetSpecId);
            specValueRepository.save(target);
        });
    }

    @Override
    public void deleteByCustomerId(int customerId) {
        specValueRepository.deleteByCustomerId(customerId);
    }
}
