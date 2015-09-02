package com.huobanplus.goodsync.datacenter.service;

import com.huobanplus.goodsync.datacenter.bean.*;

import java.util.List;

/**
 * 主商品实体
 * Created by liual on 2015-09-02.
 */
public interface GoodsService {
    /**
     * 保存商品实体
     *
     * @param goodsBean
     * @return
     */
    MallGoodsBean save(MallGoodsBean goodsBean);

    /**
     * 根据商户id返回相应的商品列表
     *
     * @param customerId
     * @return
     */
    List<MallGoodsBean> findByCustomerId(int customerId);

    /**
     * 导入商品到目标上海沪
     *
     * @param originalGoodsList 需要导入的原始数据
     * @param targetCustomerId  目标商户id
     * @return 返回结果，包含了导入成功后目标商户的商品列表和前后id的关联信息
     */
    SyncResultBean<MallGoodsBean> batchSave(List<MallGoodsBean> originalGoodsList, int targetCustomerId);

    /**
     * 处理商品内部的关联id
     * <p>需要处理的字段：</p>
     * <ul>
     * <li>catId</li>
     * <li>typeId</li>
     * <li>brandId</li>
     * <li>brand</li>
     * </ul>
     *
     * @param targetList
     */
    void handleAssociatedInfo(List<MallGoodsBean> targetList, List<MallSyncInfoBean> goodsCatSyncInfo, List<MallSyncInfoBean> brandSyncInfo, List<MallSyncInfoBean> goodsTypeSyncInfo);
}
