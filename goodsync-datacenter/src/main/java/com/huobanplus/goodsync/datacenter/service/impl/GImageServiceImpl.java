package com.huobanplus.goodsync.datacenter.service.impl;

import com.huobanplus.goodsync.datacenter.bean.MallGImagesBean;
import com.huobanplus.goodsync.datacenter.bean.MallSyncInfoBean;
import com.huobanplus.goodsync.datacenter.bean.SyncResultBean;
import com.huobanplus.goodsync.datacenter.common.ClassHandler;
import com.huobanplus.goodsync.datacenter.common.Constant;
import com.huobanplus.goodsync.datacenter.common.Message;
import com.huobanplus.goodsync.datacenter.repository.GImagesRepository;
import com.huobanplus.goodsync.datacenter.service.GImageService;
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
    public List<MallGImagesBean> findByGoods(int customerId, List<Integer> goodsList) {
        return gImagesRepository.findByGoods(customerId, goodsList);
    }

    @Override
    public List<MallGImagesBean> findGImage(int customerId, List<Integer> goodsList) {
        if (goodsList == null) {
            return gImagesRepository.findByCustomerId(customerId);
        }
        return gImagesRepository.findByGoods(customerId, goodsList);
    }

    @Override
    @Message(operation = "保存", desc = "商品图片信息")
    public SyncResultBean<MallGImagesBean> batchSave(int targetCustomerId, List<MallGImagesBean> originalImages) throws CloneNotSupportedException {
        List<MallSyncInfoBean> syncInfoList = new ArrayList<>();
        List<MallGImagesBean> targetImageList = new ArrayList<>();
        for (MallGImagesBean original : originalImages) {
            MallSyncInfoBean syncInfo = new MallSyncInfoBean();
            syncInfo.setFromId(original.getGimageId());
            syncInfo.setFromCustomerId(original.getCustomerId());
            MallGImagesBean target = (MallGImagesBean) original.clone();
            target.setGimageId(null);
            target.setCustomerId(targetCustomerId);
            target = gImagesRepository.saveAndFlush(target);
            syncInfo.setToId(target.getGimageId());
            syncInfo.setToCustomerId(targetCustomerId);
            syncInfo.setType(Constant.GOOD_Img);
            syncInfo = syncInfoService.save(syncInfo);
            syncInfoList.add(syncInfo);
            targetImageList.add(target);
        }
        return new SyncResultBean<>(targetImageList, syncInfoList);
    }

    @Override
    @Message(operation = "更新", desc = "商品图片信息")
    public List<MallGImagesBean> batchUpdate(List<MallGImagesBean> originalImages, List<MallSyncInfoBean> syncInfoList, int targetCustomerId)
            throws IllegalAccessException, InstantiationException, InvocationTargetException, CloneNotSupportedException {
        List<MallGImagesBean> targetImgList = new ArrayList<>();
        for (MallGImagesBean original : originalImages) {
            int targetId = syncInfoService.getTargetId(original.getGimageId(), Constant.GOOD_Img, syncInfoList);
            MallGImagesBean targetImg = gImagesRepository.findOne(targetId);
            if (targetImg != null) {
                ClassHandler.ClassCopy(original, targetImg);
                targetImg.setCustomerId(targetCustomerId);
                targetImgList.add(targetImg);
                gImagesRepository.save(targetImg);
            } else {
                //新增
                MallSyncInfoBean syncInfo = new MallSyncInfoBean();
                syncInfo.setFromId(original.getGimageId());
                syncInfo.setFromCustomerId(original.getCustomerId());
                MallGImagesBean newTarget = (MallGImagesBean) original.clone();
                newTarget.setGimageId(null);
                newTarget.setCustomerId(targetCustomerId);
                newTarget = gImagesRepository.saveAndFlush(newTarget);
                syncInfo.setToId(newTarget.getGimageId());
                syncInfo.setToCustomerId(targetCustomerId);
                syncInfo.setType(Constant.GOOD_Img);
                syncInfo = syncInfoService.save(syncInfo);
                targetImgList.add(newTarget);
                syncInfoList.add(syncInfo);
            }
        }
        return targetImgList;
    }

    @Override
    @Message(operation = "处理", desc = "商品图片信息关联字段")
    public void handleAssociatedInfo(List<MallGImagesBean> targetList, List<MallSyncInfoBean> goodSyncInfoList) {
        targetList.forEach(target -> {
            int targetGoodId = syncInfoService.getTargetId(target.getGoodId(), Constant.GOOD, goodSyncInfoList);
            target.setGoodId(targetGoodId);
            gImagesRepository.save(target);
        });
    }

    @Override
    public void deleteByCustomerId(int customerId) {
        gImagesRepository.deleteByCustomerId(customerId);
    }
}
