package com.huobanplus.goodsync.datacenter.service;

import com.huobanplus.goodsync.datacenter.bean.MallGImagesBean;
import com.huobanplus.goodsync.datacenter.bean.MallSyncInfoBean;
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
    SyncResultBean<MallGImagesBean> batchSave(int targetCustomerId,List<MallGImagesBean> originalImages) throws CloneNotSupportedException;

    /**
     * 处理相关联字段，及goodsId
     *
     * @param targetList
     * @param goodSyncInfoList
     */
    void handleAssociatedInfo(List<MallGImagesBean> targetList, List<MallSyncInfoBean> goodSyncInfoList);

    /**
     * 删除某商户的信息
     *
     * @param customerId
     */
    void deleteByCustomerId(int customerId);
}
