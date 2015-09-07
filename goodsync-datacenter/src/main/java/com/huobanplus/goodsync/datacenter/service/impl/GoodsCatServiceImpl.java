package com.huobanplus.goodsync.datacenter.service.impl;

import com.huobanplus.goodsync.datacenter.bean.MallGoodsCatBean;
import com.huobanplus.goodsync.datacenter.bean.MallSyncInfoBean;
import com.huobanplus.goodsync.datacenter.bean.SyncResultBean;
import com.huobanplus.goodsync.datacenter.common.Constant;
import com.huobanplus.goodsync.datacenter.repository.GoodsCatRepository;
import com.huobanplus.goodsync.datacenter.service.GoodsCatService;
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
    public SyncResultBean<MallGoodsCatBean> batchSave(List<MallGoodsCatBean> originalBeans, int targetCustomerId) throws CloneNotSupportedException {
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
