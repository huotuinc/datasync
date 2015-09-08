package com.huobanplus.goodsync.handler.huobanmall;

import com.huobanplus.goodsync.datacenter.bean.*;
import com.huobanplus.goodsync.datacenter.service.*;
import com.huobanplus.goodsync.handler.SyncHandler;
import com.huobanplus.goodsync.handler.bean.AuthorBaseBean;
import com.huobanplus.goodsync.handler.bean.HBAuthorBean;
import org.eclipse.persistence.exceptions.DatabaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liual on 2015-09-01.
 */
@Service("hbSyncHandler")
public class HBSyncHandler implements SyncHandler {
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
    @Autowired
    private GImageService gImageService;
    @Autowired
    private SpecificationService specificationService;
    @Autowired
    private SpecValueService specValueService;
    @Autowired
    private ProductService productService;
    @Autowired
    private GoodsTypeSpecService goodsTypeSpecService;
    @Autowired
    private GoodsSpecIndexService specIndexService;

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
    @Transactional
    public void goodExport(AuthorBaseBean authorBase, int loginCustomerId, String goodList) throws IOException, CloneNotSupportedException {
        //导入之前删除原来的数据
        //todo


        HBAuthorBean hbAuthorBean = (HBAuthorBean) authorBase;
        List<Integer> goods = null;
        if (!"all".equals(goodList)) {
            String[] goodsArray = goodList.split(",");
            for (String goodId : goodsArray) {
                goods = new ArrayList<>();
                goods.add(Integer.valueOf(goodId));
            }
        }
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
        List<MallGoodsBean> originalGoodList = goodsService.findGoods(loginCustomerId, goods);
        SyncResultBean<MallGoodsBean> goodsResult = goodsService.batchSave(originalGoodList, hbAuthorBean.getCustomerId());
        //处理相关联数据，先处理catId，brandId和typeId
        goodsService.handleAssociatedInfo(goodsResult.getTargetList(), goodsCatResult.getSyncInfoList(), brandSyncInfoList, goodsTypeResult.getSyncInfoList());

        //导出商品图片及保存前后关联id
        List<MallGImagesBean> originalGoodImgList = gImageService.findByCustomerId(loginCustomerId);
        SyncResultBean<MallGImagesBean> goodImgResult = gImageService.batchSave(originalGoodImgList, hbAuthorBean.getCustomerId());
        gImageService.handleAssociatedInfo(goodImgResult.getTargetList(), goodsResult.getSyncInfoList());

        //继续更新商品图片相关的字段，必须在商品图片表添加之后
        goodsService.handleDefaultImg(goodsResult.getTargetList(), goodImgResult.getSyncInfoList());

        //导出商品规格到目标商户并保存前后关联id
        List<MallSpecificationBean> originalSpecList = specificationService.findByCustomerId(loginCustomerId);
        SyncResultBean<MallSpecificationBean> specResult = specificationService.batchSave(originalSpecList, hbAuthorBean.getCustomerId());
        //导出规格值到目标商户并保存前后关联id
        List<MallSpecValuesBean> originalSpecValueList = specValueService.findByCustomerId(loginCustomerId);
        SyncResultBean<MallSpecValuesBean> specValueResult = specValueService.batchSave(originalSpecValueList, hbAuthorBean.getCustomerId());
        //处理specId
        specValueService.handleSpecId(specValueResult.getTargetList(), specValueResult.getSyncInfoList());

        //导出货品到目标商户并保存前后关联id
        List<MallProductBean> originalProductList = productService.findByCustomerId(loginCustomerId);
        SyncResultBean<MallProductBean> productResult = productService.batchSave(originalProductList, hbAuthorBean.getCustomerId());
        //处理关联字段
        productService.handleAssociatedInfo(productResult.getTargetList(), goodsResult.getSyncInfoList(), specResult.getSyncInfoList(), specValueResult.getSyncInfoList());

        //处理商品中的关联冗余字段，spec,pdt_spec,spec_desc
        goodsService.handleSpecAndPdtInfo(goodsResult.getTargetList(), specResult.getSyncInfoList(), productResult.getSyncInfoList(), specValueResult.getSyncInfoList());

        //导出goodstypedesc信息到目标商户
        List<MallGoodsTypeSpecBean> originalTypeSpec = goodsTypeSpecService.findByCustomerId(loginCustomerId);
        goodsTypeSpecService.batchSave(originalTypeSpec, hbAuthorBean.getCustomerId(), specResult.getSyncInfoList(), goodsTypeResult.getSyncInfoList());


        //导出goodsSpecIndex信息到目标商户
        List<MallGoodsSpecIndexBean> originalSpecIndex = specIndexService.findByCustomerId(loginCustomerId);
        specIndexService.batchSave(originalSpecIndex,
                hbAuthorBean.getCustomerId(),
                goodsTypeResult.getSyncInfoList(),
                specResult.getSyncInfoList(),
                specValueResult.getSyncInfoList(),
                goodsResult.getSyncInfoList(),
                productResult.getSyncInfoList());
    }

    @Override
    public void goodImport(AuthorBaseBean authorBase, int loginCustomerId) {

    }


    private void batchPreDelete(int customerId) {
        brandService.deleteByCustomerId(customerId);
        gImageService.deleteByCustomerId(customerId);
        goodsCatService.deleteByCustomerId(customerId);

    }
}
