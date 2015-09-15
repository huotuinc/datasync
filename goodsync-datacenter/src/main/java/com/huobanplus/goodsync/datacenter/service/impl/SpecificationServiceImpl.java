package com.huobanplus.goodsync.datacenter.service.impl;

import com.huobanplus.goodsync.datacenter.bean.MallSpecificationBean;
import com.huobanplus.goodsync.datacenter.bean.MallSyncInfoBean;
import com.huobanplus.goodsync.datacenter.bean.SyncResultBean;
import com.huobanplus.goodsync.datacenter.common.ClassHandler;
import com.huobanplus.goodsync.datacenter.common.Constant;
import com.huobanplus.goodsync.datacenter.common.Message;
import com.huobanplus.goodsync.datacenter.common.PreBatchDel;
import com.huobanplus.goodsync.datacenter.repository.SpecificationRepository;
import com.huobanplus.goodsync.datacenter.service.SpecificationService;
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
public class SpecificationServiceImpl implements SpecificationService {
    @Autowired
    private SpecificationRepository specificationRepository;
    @Autowired
    private SyncInfoService syncInfoService;

    @Override
    public MallSpecificationBean save(MallSpecificationBean specificationBean) {
        return specificationRepository.save(specificationBean);
    }

    @Override
    public List<MallSpecificationBean> findByCustomerId(int customerId) {
        return specificationRepository.findByCustomerId(customerId);
    }

    @Override
    @Message(operation = "保存", desc = "规格信息")
    public SyncResultBean<MallSpecificationBean> batchSave(int targetCustomerId, List<MallSpecificationBean> originalSpec) throws CloneNotSupportedException {
        List<MallSpecificationBean> targetSpecList = new ArrayList<>();
        List<MallSyncInfoBean> syncInfoList = new ArrayList<>();
        for (MallSpecificationBean original : originalSpec) {
            MallSyncInfoBean syncInfo = new MallSyncInfoBean();
            syncInfo.setFromId(original.getSpecId());
            syncInfo.setFromCustomerId(original.getCustomerId());
            MallSpecificationBean target = (MallSpecificationBean) original.clone();
            target.setSpecId(null);
            target.setCustomerId(targetCustomerId);
            target = specificationRepository.saveAndFlush(target);
            syncInfo.setToId(target.getSpecId());
            syncInfo.setToCustomerId(targetCustomerId);
            syncInfo.setType(Constant.SPEC);
            syncInfo = syncInfoService.save(syncInfo);
            syncInfoList.add(syncInfo);
            targetSpecList.add(target);
        }
        return new SyncResultBean<>(targetSpecList, syncInfoList);
    }

    @Override
    @Message(operation = "更新", desc = "规格信息")
    public List<MallSpecificationBean> batchUpdate(int targetCustomerId, List<MallSpecificationBean> originalSpec, List<MallSyncInfoBean> syncInfoList)
            throws IllegalAccessException, InvocationTargetException, InstantiationException, CloneNotSupportedException {
        List<MallSpecificationBean> targetSpecList = new ArrayList<>();
        for (MallSpecificationBean original : originalSpec) {
            int targetId = syncInfoService.getTargetId(original.getSpecId(), Constant.SPEC, syncInfoList);
            if (targetId > 0) {
                MallSpecificationBean targetSpec = specificationRepository.findOne(targetId);
                ClassHandler.ClassCopy(original, targetSpec);
                targetSpec.setCustomerId(targetCustomerId);
                targetSpecList.add(targetSpec);
                specificationRepository.save(targetSpec);
            } else {
                MallSyncInfoBean syncInfo = new MallSyncInfoBean();
                syncInfo.setFromId(original.getSpecId());
                syncInfo.setFromCustomerId(original.getCustomerId());
                MallSpecificationBean targetSpec = (MallSpecificationBean) original.clone();
                targetSpec.setSpecId(null);
                targetSpec.setCustomerId(targetCustomerId);
                targetSpec = specificationRepository.saveAndFlush(targetSpec);
                syncInfo.setToId(targetSpec.getSpecId());
                syncInfo.setToCustomerId(targetCustomerId);
                syncInfo = syncInfoService.save(syncInfo);
                syncInfoList.add(syncInfo);
                targetSpecList.add(targetSpec);
            }
        }
        return targetSpecList;
    }

    @Override
    public void deleteByCustomerId(int customerId) {
        specificationRepository.deleteByCustomerId(customerId);
    }
}
