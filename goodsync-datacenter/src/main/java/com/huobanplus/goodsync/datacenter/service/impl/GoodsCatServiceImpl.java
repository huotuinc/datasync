package com.huobanplus.goodsync.datacenter.service.impl;

import com.huobanplus.goodsync.datacenter.bean.MallGoodsCatBean;
import com.huobanplus.goodsync.datacenter.bean.MallSyncInfoBean;
import com.huobanplus.goodsync.datacenter.bean.SyncResultBean;
import com.huobanplus.goodsync.datacenter.common.ClassHandler;
import com.huobanplus.goodsync.datacenter.common.Constant;
import com.huobanplus.goodsync.datacenter.common.Message;
import com.huobanplus.goodsync.datacenter.common.PreBatchDel;
import com.huobanplus.goodsync.datacenter.repository.GoodsCatRepository;
import com.huobanplus.goodsync.datacenter.service.GoodsCatService;
import com.huobanplus.goodsync.datacenter.service.SyncInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by liual on 2015-09-02.
 */
@Service
@Transactional
public class GoodsCatServiceImpl implements GoodsCatService {
    @Autowired
    private GoodsCatRepository goodsCatRepository;
    @Autowired
    private SyncInfoService syncInfoService;

    @Override
    public MallGoodsCatBean save(MallGoodsCatBean goodsCatBean) {
        return goodsCatRepository.save(goodsCatBean);
    }

    @Override
    public List<MallGoodsCatBean> findByCustomerId(int customerId) {
        return goodsCatRepository.findByCustomerId(customerId);
    }

    @Override
    @Message(operation = "保存", desc = "商品分类信息")
    public SyncResultBean<MallGoodsCatBean> batchSave(int targetCustomerId, List<MallGoodsCatBean> originalBeans) throws CloneNotSupportedException {
        List<MallSyncInfoBean> syncInfoList = new ArrayList<>();
        List<MallGoodsCatBean> targetGoodsCat = new ArrayList<>();
        for (MallGoodsCatBean original : originalBeans) {
            MallSyncInfoBean syncInfoBean = new MallSyncInfoBean();
            syncInfoBean.setFromId(original.getCatId());
            syncInfoBean.setFromCustomerId(original.getCustomerId());
            MallGoodsCatBean target = (MallGoodsCatBean) original.clone();
            target.setCatId(null);
            target.setCustomerId(targetCustomerId);
            target = goodsCatRepository.saveAndFlush(target);
            targetGoodsCat.add(target);
            syncInfoBean.setToId(target.getCatId());
            syncInfoBean.setToCustomerId(targetCustomerId);
            syncInfoBean.setType(Constant.GOOD_CAT);
            syncInfoBean = syncInfoService.save(syncInfoBean);
            syncInfoList.add(syncInfoBean);
        }
        originalBeans.forEach(originalBean -> {

        });
        return new SyncResultBean<>(targetGoodsCat, syncInfoList);
    }

    @Override
    @Message(operation = "更新", desc = "商品分类信息")
    public List<MallGoodsCatBean> batchUpdate(int targetCustomerId, List<MallGoodsCatBean> originalCats, List<MallSyncInfoBean> syncInfoList)
            throws IllegalAccessException, InvocationTargetException, InstantiationException, CloneNotSupportedException {
        List<MallGoodsCatBean> targetCatList = new ArrayList<>();
        for (MallGoodsCatBean original : originalCats) {
            int targetId = syncInfoService.getTargetId(original.getCatId(), Constant.GOOD_CAT, syncInfoList);
            MallGoodsCatBean targetCat = goodsCatRepository.findOne(targetId);
            if (targetCat != null) {
                ClassHandler.ClassCopy(original, targetCat);
                targetCat.setCustomerId(targetCustomerId);
                targetCatList.add(targetCat);
            } else {
                MallSyncInfoBean syncInfo = new MallSyncInfoBean();
                syncInfo.setFromId(original.getCatId());
                syncInfo.setFromCustomerId(original.getCustomerId());
                MallGoodsCatBean newTarget = (MallGoodsCatBean) original.clone();
                newTarget.setCatId(null);
                newTarget.setCustomerId(targetCustomerId);
                newTarget = goodsCatRepository.saveAndFlush(newTarget);
                syncInfo.setToId(newTarget.getCatId());
                syncInfo.setToCustomerId(targetCustomerId);
                syncInfo.setType(Constant.GOOD_CAT);
                syncInfo = syncInfoService.save(syncInfo);
                syncInfoList.add(syncInfo);
                targetCatList.add(newTarget);
            }
        }
        return targetCatList;
    }

    @Override
    @Message(operation = "处理", desc = "商品分类信息")
    public void handleAssociatedInfo(List<MallGoodsCatBean> targetCats, List<MallSyncInfoBean> syncInfoList) {
        targetCats.forEach(goodsCat -> {
            int targetParentId = syncInfoService.getTargetId(goodsCat.getParentId(), Constant.GOOD_CAT, syncInfoList);
            goodsCat.setParentId(targetParentId);
            String originalPath = goodsCat.getCatPath();
            String[] pathArray = originalPath.substring(1, originalPath.length() - 1).split("\\|");
            //得到targetId并重新拼接path
            StringBuilder targetPath = new StringBuilder("|");
            for (String originalId : pathArray) {
                int targetId = syncInfoService.getTargetId(Integer.parseInt(originalId), Constant.GOOD_CAT, syncInfoList);
                targetPath.append(targetId).append("|");
            }
            goodsCat.setCatPath(targetPath.toString());
            goodsCatRepository.save(goodsCat);
        });
    }

    @Override
    public void deleteByCustomerId(int customerId) {
        goodsCatRepository.deleteByCustomerId(customerId);
    }
}
