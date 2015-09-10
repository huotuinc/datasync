package com.huobanplus.goodsync.datacenter.service.impl;

import com.huobanplus.goodsync.datacenter.bean.MallSyncInfoBean;
import com.huobanplus.goodsync.datacenter.repository.SyncInfoRepository;
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
public class SyncInfoServiceImpl implements SyncInfoService {
    @Autowired
    private SyncInfoRepository syncInfoRepository;

    @Override
    public MallSyncInfoBean save(MallSyncInfoBean syncInfoBean) {
        return syncInfoRepository.saveAndFlush(syncInfoBean);
    }

    @Override
    public List<MallSyncInfoBean> batchSave(List<MallSyncInfoBean> syncInfoBeans) {
        List<MallSyncInfoBean> list = new ArrayList<>();
        syncInfoBeans.forEach(syncInfo -> {
            MallSyncInfoBean syncInfoBean = syncInfoRepository.save(syncInfo);
            list.add(syncInfoBean);
        });
        return list;
    }

    @Override
    public int getFromId(int targetId, String type, List<MallSyncInfoBean> syncInfoList) {
        for (MallSyncInfoBean syncInfo : syncInfoList) {
            if (type.equals(syncInfo.getType())) {
                if (syncInfo.getToId() == targetId) {
                    return syncInfo.getFromId();
                }
            }
        }
        return 0;
    }

    @Override
    public int getTargetId(int fromId, String type, List<MallSyncInfoBean> syncInfoList) {
        for (MallSyncInfoBean syncInfo : syncInfoList) {
            if (type.equals(syncInfo.getType())) {
                if (syncInfo.getFromId() == fromId) {
                    return syncInfo.getToId();
                }
            }
        }
        return 0;
    }

    @Override
    public void deleteByCustomerId(int customerId) {
        syncInfoRepository.deleteByCustomerId(customerId);
    }

    @Override
    public int countByToCustomerId(int customerId) {
        return syncInfoRepository.countByToCustomerId(customerId);
    }

    @Override
    public List<MallSyncInfoBean> findByToCustomerId(int toCustomerId) {
        return syncInfoRepository.findByToCustomerId(toCustomerId);
    }

}
