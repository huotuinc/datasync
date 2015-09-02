package com.huobanplus.goodsync.datacenter.service;

import com.huobanplus.goodsync.datacenter.bean.MallGoodsCatBean;
import com.huobanplus.goodsync.datacenter.bean.MallSyncInfoBean;
import com.huobanplus.goodsync.datacenter.bean.SyncResultBean;

import java.util.List;

/**
 * 商品分类
 * Created by liual on 2015-09-02.
 */
public interface GoodsCatService {
    /**
     * 保存商品分类实体
     *
     * @param goodsCatBean
     * @return
     */
    MallGoodsCatBean save(MallGoodsCatBean goodsCatBean);

    /**
     * 根据商户id得到商品分类列表
     *
     * @param customerId
     * @return
     */
    List<MallGoodsCatBean> findByCustomerId(int customerId);

    /**
     * 批量导入新商户，返回导入前后的id关联信息
     *
     * @param originalBeans
     * @param targetCustomerId
     * @return
     */
    SyncResultBean<MallGoodsCatBean> batchSave(List<MallGoodsCatBean> originalBeans, int targetCustomerId);

    /**
     * 处理商品分类内部相关联的id
     *
     * @param resultBean
     */
    void handleAssociatedInfo(SyncResultBean<MallGoodsCatBean> resultBean);
}
