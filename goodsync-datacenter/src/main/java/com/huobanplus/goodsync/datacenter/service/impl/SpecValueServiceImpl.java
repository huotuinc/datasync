package com.huobanplus.goodsync.datacenter.service.impl;

import com.huobanplus.goodsync.datacenter.bean.MallSpecValuesBean;
import com.huobanplus.goodsync.datacenter.bean.MallSyncInfoBean;
import com.huobanplus.goodsync.datacenter.bean.SyncResultBean;
import com.huobanplus.goodsync.datacenter.common.Constant;
import com.huobanplus.goodsync.datacenter.repository.SpecValueRepository;
import com.huobanplus.goodsync.datacenter.service.SpecValueService;
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
    public SyncResultBean<MallSpecValuesBean> batchSave(List<MallSpecValuesBean> originalSpecValue, int targetCustomerId) throws CloneNotSupportedException {
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
    public void handleSpecId(List<MallSpecValuesBean> targetList, List<MallSyncInfoBean> specSyncInfoList) {
        targetList.forEach(target -> {
            int targetSpecId = syncInfoService.getTargetId(target.getSpecId(), Constant.SPEC_VALUE, specSyncInfoList);
            target.setSpecId(targetSpecId);
            specValueRepository.save(target);
        });
    }
}
