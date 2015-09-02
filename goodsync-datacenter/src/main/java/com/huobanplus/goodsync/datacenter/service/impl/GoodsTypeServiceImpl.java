package com.huobanplus.goodsync.datacenter.service.impl;

import com.huobanplus.goodsync.datacenter.bean.MallGoodsTypeBean;
import com.huobanplus.goodsync.datacenter.bean.MallSyncInfoBean;
import com.huobanplus.goodsync.datacenter.bean.SyncResultBean;
import com.huobanplus.goodsync.datacenter.common.Constant;
import com.huobanplus.goodsync.datacenter.repository.GoodsTypeRepository;
import com.huobanplus.goodsync.datacenter.service.GoodsTypeService;
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
    public SyncResultBean<MallGoodsTypeBean> batchSave(List<MallGoodsTypeBean> originalBeans, int targetCustomerId) {
        List<MallGoodsTypeBean> targetGoodsType = new ArrayList<>();
        List<MallSyncInfoBean> syncInfoList = new ArrayList<>();
        originalBeans.forEach(originalBean -> {
            MallSyncInfoBean syncInfo = new MallSyncInfoBean();
            syncInfo.setFromId(originalBean.getTypeId());
            syncInfo.setFromCustomerId(originalBean.getCustomerId());
            originalBean.setTypeId(null);
            originalBean.setCustomerId(targetCustomerId);
            MallGoodsTypeBean target = goodsTypeRepository.save(originalBean);
            syncInfo.setToId(target.getTypeId());
            syncInfo.setToCustomerId(targetCustomerId);
            syncInfo.setType(Constant.GOOD_TYPE);
            syncInfo = syncInfoService.save(syncInfo);
            syncInfoList.add(syncInfo);
            targetGoodsType.add(target);
        });
        return new SyncResultBean<>(targetGoodsType, syncInfoList);
    }
}
