package com.huobanplus.goodsync.datacenter.service;

import com.huobanplus.goodsync.datacenter.bean.MallGoodsTypeBean;
import com.huobanplus.goodsync.datacenter.bean.SyncResultBean;

import java.util.List;

/**
 * 商品类型
 * Created by liual on 2015-09-02.
 */
public interface GoodsTypeService {
    /**
     * 保存商品类型实体
     *
     * @param goodsTypeBean
     * @return
     */
    MallGoodsTypeBean save(MallGoodsTypeBean goodsTypeBean);

    /**
     * 根据商户ID，得到商品类型实体
     *
     * @param customerId
     * @return
     */
    List<MallGoodsTypeBean> findByCustomerId(int customerId);

    /**
     * 将数据导入到目标商户
     *
     * @param originalBeans    源数据
     * @param targetCustomerId 目标商户id
     * @return 返回结果，包含了导入成功后目标商户的商品类型列表和前后id的关联信息
     */
    SyncResultBean<MallGoodsTypeBean> batchSave(List<MallGoodsTypeBean> originalBeans, int targetCustomerId);
}
