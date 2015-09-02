package com.huobanplus.goodsync.datacenter.service.impl;

import com.huobanplus.goodsync.datacenter.bean.MallGoodsCatBean;
import com.huobanplus.goodsync.datacenter.bean.MallSyncInfoBean;
import com.huobanplus.goodsync.datacenter.bean.SyncResultBean;
import com.huobanplus.goodsync.datacenter.common.Constant;
import com.huobanplus.goodsync.datacenter.repository.GoodsCatRepository;
import com.huobanplus.goodsync.datacenter.service.GoodsCatService;
import com.huobanplus.goodsync.datacenter.service.SyncInfoService;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
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
    public SyncResultBean<MallGoodsCatBean> batchSave(List<MallGoodsCatBean> originalBeans, int targetCustomerId) {
        List<MallSyncInfoBean> syncInfoList = new ArrayList<>();
        List<MallGoodsCatBean> targetGoodsCat = new ArrayList<>();
        originalBeans.forEach(originalBean -> {
            MallSyncInfoBean syncInfoBean = new MallSyncInfoBean();
            syncInfoBean.setFromId(originalBean.getCatId());
            syncInfoBean.setFromCustomerId(originalBean.getCustomerId());
            originalBean.setCatId(null);
            originalBean.setCustomerId(targetCustomerId);
            MallGoodsCatBean targetCat = goodsCatRepository.save(originalBean);
            targetGoodsCat.add(targetCat);
            syncInfoBean.setToId(targetCat.getCatId());
            syncInfoBean.setToCustomerId(targetCustomerId);
            syncInfoBean.setType(Constant.GOOD_CAT);
            syncInfoBean = syncInfoService.save(syncInfoBean);
            syncInfoList.add(syncInfoBean);
        });
        return new SyncResultBean<>(targetGoodsCat, syncInfoList);
    }

    @Override
    public void handleAssociatedInfo(SyncResultBean<MallGoodsCatBean> resultBean) {
        resultBean.getTargetList().forEach(goodsCat -> {
            int targetParentId = syncInfoService.getTargetId(goodsCat.getParentId(), Constant.GOOD_CAT, resultBean.getSyncInfoList());
            goodsCat.setParentId(targetParentId);
            String originalPath = goodsCat.getCatPath();
            String[] pathArray = originalPath.substring(1, originalPath.length() - 1).split("/|");
            //得到targetId并重新拼接path
            StringBuilder targetPath = new StringBuilder("|");
            for (String originalId : pathArray) {
                int targetId = syncInfoService.getTargetId(Integer.parseInt(originalId), Constant.GOOD_CAT, resultBean.getSyncInfoList());
                targetPath.append(targetId).append("|");
            }
            goodsCat.setCatPath(targetPath.toString());
            goodsCatRepository.save(goodsCat);
        });
    }
}
