package com.huobanplus.goodsync.datacenter.service.impl;

import com.huobanplus.goodsync.datacenter.bean.MallSpecificationBean;
import com.huobanplus.goodsync.datacenter.bean.MallSyncInfoBean;
import com.huobanplus.goodsync.datacenter.bean.SyncResultBean;
import com.huobanplus.goodsync.datacenter.common.Constant;
import com.huobanplus.goodsync.datacenter.repository.SpecificationRepository;
import com.huobanplus.goodsync.datacenter.service.SpecificationService;
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
    public SyncResultBean<MallSpecificationBean> batchSave(List<MallSpecificationBean> originalSpec, int targetCustomerId) {
        List<MallSpecificationBean> targetSpecList = new ArrayList<>();
        List<MallSyncInfoBean> syncInfoList = new ArrayList<>();
        originalSpec.forEach(spec -> {
            MallSyncInfoBean syncInfo = new MallSyncInfoBean();
            syncInfo.setFromId(spec.getSpecId());
            syncInfo.setFromCustomerId(spec.getCustomerId());
            spec.setSpecId(null);
            spec.setCustomerId(targetCustomerId);
            MallSpecificationBean target = specificationRepository.save(spec);
            syncInfo.setToId(target.getSpecId());
            syncInfo.setToCustomerId(targetCustomerId);
            syncInfo.setType(Constant.SPEC);
            syncInfo = syncInfoService.save(syncInfo);
            syncInfoList.add(syncInfo);
            targetSpecList.add(target);
        });
        return new SyncResultBean<>(targetSpecList, syncInfoList);
    }
}
