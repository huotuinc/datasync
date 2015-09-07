package com.huobanplus.goodsync.datacenter.service.impl;

import com.huobanplus.goodsync.datacenter.bean.MallBrandBean;
import com.huobanplus.goodsync.datacenter.bean.MallSyncInfoBean;
import com.huobanplus.goodsync.datacenter.repository.BrandRepository;
import com.huobanplus.goodsync.datacenter.service.BrandService;
import com.huobanplus.goodsync.datacenter.service.SyncInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<MallSyncInfoBean> batchSave(List<MallBrandBean> originalBrand, int customerId) throws CloneNotSupportedException {
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
    public MallBrandBean findByBrandId(int brandId) {
        return brandRepository.findOne(brandId);
    }
}
