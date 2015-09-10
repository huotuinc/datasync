package com.huobanplus.goodsync.datacenter.service.impl;

import com.huobanplus.goodsync.datacenter.bean.MallBrandBean;
import com.huobanplus.goodsync.datacenter.bean.MallSyncInfoBean;
import com.huobanplus.goodsync.datacenter.bean.SyncResultBean;
import com.huobanplus.goodsync.datacenter.common.ClassHandler;
import com.huobanplus.goodsync.datacenter.common.Constant;
import com.huobanplus.goodsync.datacenter.common.Message;
import com.huobanplus.goodsync.datacenter.common.PreBatchDel;
import com.huobanplus.goodsync.datacenter.repository.BrandRepository;
import com.huobanplus.goodsync.datacenter.service.BrandService;
import com.huobanplus.goodsync.datacenter.service.SyncInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liual on 2015-09-02.
 */
@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private SyncInfoService syncInfoService;

    @Override
    public MallBrandBean save(MallBrandBean brandBean) {
        return brandRepository.save(brandBean);
    }

    @Override
    public List<MallBrandBean> findByCustomerId(int customerId) {
        return brandRepository.findByCustomerId(customerId);
    }

    @Override
    @Message(type = "同步", info = "商品品牌")
    public List<MallSyncInfoBean> batchSave(int customerId, List<MallBrandBean> originalBrand) throws CloneNotSupportedException {
        List<MallSyncInfoBean> savedList = new ArrayList<>();
        for (MallBrandBean brand : originalBrand) {
            MallSyncInfoBean syncInfoBean = new MallSyncInfoBean();
            syncInfoBean.setFromId(brand.getBrandId());
            syncInfoBean.setFromDesc("伙伴商城");
            syncInfoBean.setFromCustomerId(brand.getCustomerId());
            //保存开始
            MallBrandBean targetBrand = (MallBrandBean) brand.clone();
            targetBrand.setBrandId(null);
            targetBrand.setCustomerId(customerId);
            targetBrand = brandRepository.saveAndFlush(targetBrand);

            //记录目标信息
            syncInfoBean.setToId(targetBrand.getBrandId());
            syncInfoBean.setToDesc("伙伴商城");
            syncInfoBean.setToCustomerId(customerId);
            syncInfoBean.setType("brand");
            syncInfoBean = syncInfoService.save(syncInfoBean);
            savedList.add(syncInfoBean);
        }
        return savedList;
    }

    @Override
    public List<MallBrandBean> batchUpdate(List<MallBrandBean> originalBrand, List<MallSyncInfoBean> syncInfoList, int targetCustomerId)
            throws IllegalAccessException, InvocationTargetException, InstantiationException, CloneNotSupportedException {
        List<MallBrandBean> targetBrandList = new ArrayList<>();
        for (MallBrandBean original : originalBrand) {
            int targetId = syncInfoService.getTargetId(original.getBrandId(), Constant.BRAND, syncInfoList);
            if (targetId > 0) {
                //存在则更新
                MallBrandBean targetBrand = brandRepository.findOne(targetId);
                ClassHandler.ClassCopy(original, targetBrand);
                targetBrand.setCustomerId(targetCustomerId);
                targetBrandList.add(targetBrand);
                brandRepository.save(targetBrand);
            } else {
                //不存在则新增
                MallSyncInfoBean syncInfo = new MallSyncInfoBean();
                syncInfo.setFromId(original.getBrandId());
                syncInfo.setFromCustomerId(original.getCustomerId());
                MallBrandBean targetBrand = (MallBrandBean) original.clone();
                targetBrand.setBrandId(null);
                targetBrand = brandRepository.saveAndFlush(targetBrand);
                syncInfo.setToId(targetBrand.getBrandId());
                syncInfo.setToCustomerId(targetCustomerId);
                syncInfo.setType(Constant.BRAND);
                syncInfo = syncInfoService.save(syncInfo);
                syncInfoList.add(syncInfo);
                targetBrandList.add(targetBrand);
            }
        }
        return targetBrandList;
    }

    @Override
    public MallBrandBean findByBrandId(int brandId) {
        return brandRepository.findOne(brandId);
    }

    @Override
    public void deleteByCustomerId(int customerId) {
        brandRepository.deleteByCustomerId(customerId);
    }
}
