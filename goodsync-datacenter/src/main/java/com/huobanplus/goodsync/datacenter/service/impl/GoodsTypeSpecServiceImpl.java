package com.huobanplus.goodsync.datacenter.service.impl;

import com.huobanplus.goodsync.datacenter.bean.MallGoodsTypeSpecBean;
import com.huobanplus.goodsync.datacenter.bean.MallSyncInfoBean;
import com.huobanplus.goodsync.datacenter.common.Constant;
import com.huobanplus.goodsync.datacenter.common.Message;
import com.huobanplus.goodsync.datacenter.common.PreBatchDel;
import com.huobanplus.goodsync.datacenter.dao.GoodsTypeSpecDao;
import com.huobanplus.goodsync.datacenter.service.GoodsTypeSpecService;
import com.huobanplus.goodsync.datacenter.service.SyncInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by liual on 2015-09-05.
 */
@Service
@Transactional
public class GoodsTypeSpecServiceImpl implements GoodsTypeSpecService {
    @Autowired
    private GoodsTypeSpecDao goodsTypeSpecDao;
    @Autowired
    private SyncInfoService syncInfoService;

    @Override
    public List<MallGoodsTypeSpecBean> findByCustomerId(int customerId) {
        return goodsTypeSpecDao.findByCustomerId(customerId);
    }

    @Override
    public int add(MallGoodsTypeSpecBean goodsTypeSpec) {
        return goodsTypeSpecDao.add(goodsTypeSpec);
    }

    @Override
    public int update(MallGoodsTypeSpecBean goodsTypeSpec) {
        return goodsTypeSpecDao.update(goodsTypeSpec);
    }

    @Override
    @PreBatchDel
    @Message(operation = "保存", desc = "商品分类规格关联信息")
    public int batchSave(int targetCustomerId, List<MallGoodsTypeSpecBean> originalList, List<MallSyncInfoBean> specSyncInfo, List<MallSyncInfoBean> typeSyncInfo) {
        int index = 0;
        for (MallGoodsTypeSpecBean original : originalList) {
            int targetSpecId = syncInfoService.getTargetId(original.getSpecId(), Constant.SPEC, specSyncInfo);
            int targetTypeId = syncInfoService.getTargetId(original.getTypeId(), Constant.GOOD_TYPE, typeSyncInfo);
            original.setSpecId(targetSpecId);
            original.setTypeId(targetTypeId);
            original.setCustomerId(targetCustomerId);
            goodsTypeSpecDao.add(original);
            index++;
        }
        return index;
    }

    @Override
    @PreBatchDel
    @Message(operation = "更新", desc = "商品分类规格关联信息")
    public int batchSave(int targetCustomerId, List<MallGoodsTypeSpecBean> originalList, List<MallSyncInfoBean> syncInfoList) {
        int index = 0;
        for (MallGoodsTypeSpecBean original : originalList) {
            int targetSpecId = syncInfoService.getTargetId(original.getSpecId(), Constant.SPEC, syncInfoList);
            int targetTypeId = syncInfoService.getTargetId(original.getTypeId(), Constant.GOOD_TYPE, syncInfoList);
            original.setSpecId(targetSpecId);
            original.setTypeId(targetTypeId);
            original.setCustomerId(targetCustomerId);
            goodsTypeSpecDao.add(original);
            index++;
        }
        return index;
    }

    @Override
    public void deleteByCustomerId(int customerId) {
        goodsTypeSpecDao.deleteByCustomerId(customerId);
    }
}
