package com.huobanplus.goodsync.datacenter.service;

import com.huobanplus.goodsync.datacenter.bean.MallGImagesBean;
import com.huobanplus.goodsync.datacenter.bean.SyncResultBean;

import java.util.List;

/**
 * 商品关联图片
 * Created by liual on 2015-09-02.
 */
public interface GImageService {
    /**
     * 保存实体
     *
     * @param gImagesBean
     * @return
     */
    MallGImagesBean save(MallGImagesBean gImagesBean);

    /**
     * 得到某商户的商品图片信息
     *
     * @param customerId
     * @return
     */
    List<MallGImagesBean> findByCustomerId(int customerId);

    /**
     * 导入商品图片到对应商户
     *
     * @param originalImages
     * @param targetCustomerId
     * @return
     */
    SyncResultBean<MallGImagesBean> batchSave(List<MallGImagesBean> originalImages, int targetCustomerId);
}
