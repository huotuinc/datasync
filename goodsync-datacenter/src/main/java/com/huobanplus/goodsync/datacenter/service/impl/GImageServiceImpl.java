package com.huobanplus.goodsync.datacenter.service.impl;

import com.huobanplus.goodsync.datacenter.bean.MallGImagesBean;
import com.huobanplus.goodsync.datacenter.bean.MallSyncInfoBean;
import com.huobanplus.goodsync.datacenter.bean.SyncResultBean;
import com.huobanplus.goodsync.datacenter.common.Constant;
import com.huobanplus.goodsync.datacenter.repository.GImagesRepository;
import com.huobanplus.goodsync.datacenter.service.GImageService;
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
public class GImageServiceImpl implements GImageService {
    @Autowired
    private GImagesRepository gImagesRepository;
    @Autowired
    private SyncInfoService syncInfoService;

    @Override
    public MallGImagesBean save(MallGImagesBean gImagesBean) {
        return gImagesRepository.save(gImagesBean);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MallGImagesBean> findByCustomerId(int customerId) {
        return gImagesRepository.findByCustomerId(customerId);
    }

    @Override
    public SyncResultBean<MallGImagesBean> batchSave(List<MallGImagesBean> originalImages, int targetCustomerId) {
        List<MallSyncInfoBean> syncInfoList = new ArrayList<>();
        List<MallGImagesBean> targetImageList = new ArrayList<>();
        originalImages.forEach(originalImage -> {
            MallSyncInfoBean syncInfo = new MallSyncInfoBean();
            syncInfo.setFromId(originalImage.getGimageId());
            syncInfo.setFromCustomerId(originalImage.getCustomerId());
            originalImage.setGimageId(null);
            originalImage.setCustomerId(targetCustomerId);
            MallGImagesBean target = gImagesRepository.save(originalImage);
            syncInfo.setToId(target.getGimageId());
            syncInfo.setToCustomerId(targetCustomerId);
            syncInfo.setType(Constant.GOOD_Img);
            syncInfo = syncInfoService.save(syncInfo);
            syncInfoList.add(syncInfo);
            targetImageList.add(target);
        });
        return new SyncResultBean<>(targetImageList, syncInfoList);
    }

    @Override
    public void handleAssociatedInfo(List<MallGImagesBean> targetList, List<MallSyncInfoBean> goodSyncInfoList) {
        targetList.forEach(target -> {
            int targetGoodId = syncInfoService.getTargetId(target.getGoodId(), Constant.GOOD_Img, goodSyncInfoList);
            target.setGoodId(targetGoodId);
            gImagesRepository.save(target);
        });
    }
}
