package com.huobanplus.goodsync.datacenter.service;

import com.fasterxml.jackson.core.JsonParseException;
import com.huobanplus.goodsync.datacenter.bean.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
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
     * 得到商品实体
     *
     * @param goodsId
     * @return
     */
    MallGoodsBean findByGoodsId(int goodsId);

    /**
     * 根据分类id得到商品列表
     *
     * @param cateId
     * @return
     */
    List<MallGoodsBean> findByCateId(int cateId);

    /**
     * 得到某商户的商品信息
     *
     * @param customerId
     * @param goodList
     * @return
     */
    List<MallGoodsBean> findGoods(int customerId, List<Integer> goodList);

    /**
     * 得到某商户的商品信息
     *
     * @param customerId
     * @return
     */
    List<MallGoodsBean> findByCustomerId(int customerId);

    /**
     * 导入商品到目标商户
     *
     * @param originalGoodsList 需要导入的原始数据
     * @param targetCustomerId  目标商户id
     * @return 返回结果，包含了导入成功后目标商户的商品列表和前后id的关联信息
     */
    SyncResultBean<MallGoodsBean> batchSave(int targetCustomerId, List<MallGoodsBean> originalGoodsList) throws CloneNotSupportedException;

    /**
     * 批量更新
     *
     * @param originalGoods
     * @param syncInfoList
     */
    List<MallGoodsBean> batchUpdate(List<MallGoodsBean> originalGoods, List<MallSyncInfoBean> syncInfoList, int targetCustomerId) throws IllegalAccessException, InvocationTargetException, InstantiationException, CloneNotSupportedException;

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

    /**
     * 处理商品默认图片
     *
     * @param targetList
     * @param goodsImgSyncInfo
     */
    void handleDefaultImg(List<MallGoodsBean> targetList, List<MallSyncInfoBean> goodsImgSyncInfo);

    /**
     * 处理规格，货品等相关联数据
     * <p>需要处理的字段：</p>
     * <ul>
     * <li>spec</li>
     * <li>pdt_desc</li>
     * <li>spec_desc</li>
     * </ul>
     *
     * @param targetList
     * @param specSyncInfo
     * @param productSyncInfo
     * @param specValueSyncInfo
     */
    void handleSpecAndPdtInfo(List<MallGoodsBean> targetList, List<MallSyncInfoBean> specSyncInfo, List<MallSyncInfoBean> productSyncInfo, List<MallSyncInfoBean> specValueSyncInfo) throws IOException;


    /**
     * 处理所有关联字段
     * <p>及上述三个处理方法的综合</p>
     * <p>在批量更新时使用</p>
     *
     * @param targetList
     * @param syncInfoList
     */
    void handleAssociatedAllInfo(List<MallGoodsBean> targetList, List<MallSyncInfoBean> syncInfoList) throws IOException;

    /**
     * 删除某商户的信息
     *
     * @param customerId
     */
    void deleteByCustomerId(int customerId);
}
