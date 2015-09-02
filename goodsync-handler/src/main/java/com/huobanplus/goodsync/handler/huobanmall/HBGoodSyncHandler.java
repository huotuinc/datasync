package com.huobanplus.goodsync.handler.huobanmall;

import com.huobanplus.goodsync.datacenter.bean.*;
import com.huobanplus.goodsync.datacenter.service.*;
import com.huobanplus.goodsync.handler.GoodSyncHandler;
import com.huobanplus.goodsync.handler.bean.AuthorBaseBean;
import com.huobanplus.goodsync.handler.bean.HBAuthorBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liual on 2015-09-01.
 */
@Service
public class HBGoodSyncHandler implements GoodSyncHandler {
    @Autowired
    private AccountService accountService;

    @Autowired
    private BrandService brandService;
    @Autowired
    private GoodsCatService goodsCatService;
    @Autowired
    private GoodsTypeService goodsTypeService;
    @Autowired
    private GoodsService goodsService;

    @Override
    public AuthorBaseBean authorization(AuthorBaseBean authorBase) {
        int customerId = accountService.login(authorBase.getAccount(), authorBase.getPassword());
        if (customerId == 0)
            return null;
        else {
            HBAuthorBean hbAuthorBean = (HBAuthorBean) authorBase;
            hbAuthorBean.setCustomerId(customerId);
            return hbAuthorBean;
        }
    }

    @Override
    public void goodExport(AuthorBaseBean authorBase, int loginCustomerId) {
        HBAuthorBean hbAuthorBean = (HBAuthorBean) authorBase;
        //导出brand及保存前后关联id
        List<MallBrandBean> originalBrandList = brandService.findByCustomerId(loginCustomerId);
        List<MallSyncInfoBean> brandSyncInfoList = brandService.batchSave(originalBrandList, hbAuthorBean.getCustomerId());

        //导出goodCat及保存前后关联id
        List<MallGoodsCatBean> originalCatList = goodsCatService.findByCustomerId(loginCustomerId);
        SyncResultBean<MallGoodsCatBean> goodsCatResult = goodsCatService.batchSave(originalCatList, hbAuthorBean.getCustomerId());
        //商品分类中还有一些关联字段需要处理
        goodsCatService.handleAssociatedInfo(goodsCatResult);

        //导出商品类型及保存前后关联id
        List<MallGoodsTypeBean> originalTypeList = goodsTypeService.findByCustomerId(loginCustomerId);
        SyncResultBean<MallGoodsTypeBean> goodsTypeResult = goodsTypeService.batchSave(originalTypeList, hbAuthorBean.getCustomerId());

        //导出商品详细及保存前后关联id
        List<MallGoodsBean> originalGoodList = goodsService.findByCustomerId(loginCustomerId);
        SyncResultBean<MallGoodsBean> goodsResult = goodsService.batchSave(originalGoodList, hbAuthorBean.getCustomerId());
        //处理相关联数据，先处理catId，brandId和typeId
        goodsService.handleAssociatedInfo(goodsResult.getTargetList(), goodsCatResult.getSyncInfoList(), brandSyncInfoList, goodsTypeResult.getSyncInfoList());

        //导出商品图片及保存前后关联id
        
    }

    @Override
    public void goodImport(AuthorBaseBean authorBase, int loginCustomerId) {

    }

}
