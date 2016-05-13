package com.huobanplus.goodsync.handler.huobanmall.service;

import com.huobanplus.goodsync.datacenter.bean.*;
import com.huobanplus.goodsync.datacenter.bean.MallBrandBean;
import com.huobanplus.goodsync.datacenter.bean.MallGImagesBean;
import com.huobanplus.goodsync.datacenter.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by liual on 2015-09-09.
 */
@Service
public class ExportService {
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
    @Autowired
    private SyncInfoService syncInfoService;
    
    public void exportAdd(int currentCustomerId, int targetCustomerId, List<Integer> goods) throws IOException, CloneNotSupportedException {
        //导出brand及保存前后关联id
        List<MallBrandBean> originalBrandList = brandService.findByCustomerId(currentCustomerId);
        List<MallSyncInfoBean> brandSyncInfoList = brandService.batchSave(targetCustomerId, originalBrandList);

        //导出goodCat及保存前后关联id
        List<MallGoodsCatBean> originalCatList = goodsCatService.findByCustomerId(currentCustomerId);
        SyncResultBean<MallGoodsCatBean> goodsCatResult = goodsCatService.batchSave(targetCustomerId, originalCatList);
        //商品分类中还有一些关联字段需要处理
        goodsCatService.handleAssociatedInfo(goodsCatResult.getTargetList(), goodsCatResult.getSyncInfoList());

        //导出商品类型及保存前后关联id
        List<MallGoodsTypeBean> originalTypeList = goodsTypeService.findByCustomerId(currentCustomerId);
        SyncResultBean<MallGoodsTypeBean> goodsTypeResult = goodsTypeService.batchSave(targetCustomerId, originalTypeList);

        //导出商品详细及保存前后关联id
        List<MallGoodsBean> originalGoodList = goodsService.findGoods(currentCustomerId, goods);
        SyncResultBean<MallGoodsBean> goodsResult = goodsService.batchSave(targetCustomerId, originalGoodList);
        //处理相关联数据，先处理catId，brandId和typeId
        goodsService.handleAssociatedInfo(goodsResult.getTargetList(), goodsCatResult.getSyncInfoList(), brandSyncInfoList, goodsTypeResult.getSyncInfoList());

        //导出商品图片及保存前后关联id
        List<MallGImagesBean> originalGoodImgList = gImageService.findGImage(currentCustomerId, goods);
        SyncResultBean<MallGImagesBean> goodImgResult = gImageService.batchSave(targetCustomerId, originalGoodImgList);
        gImageService.handleAssociatedInfo(goodImgResult.getTargetList(), goodsResult.getSyncInfoList());

        //继续更新商品图片相关的字段，必须在商品图片表添加之后
        goodsService.handleDefaultImg(goodsResult.getTargetList(), goodImgResult.getSyncInfoList());

        //导出商品规格到目标商户并保存前后关联id
        List<MallSpecificationBean> originalSpecList = specificationService.findByCustomerId(currentCustomerId);
        SyncResultBean<MallSpecificationBean> specResult = specificationService.batchSave(targetCustomerId, originalSpecList);

        //导出规格值到目标商户并保存前后关联id
        List<MallSpecValuesBean> originalSpecValueList = specValueService.findByCustomerId(currentCustomerId);
        SyncResultBean<MallSpecValuesBean> specValueResult = specValueService.batchSave(targetCustomerId, originalSpecValueList);
        //处理specId
        specValueService.handleSpecId(specValueResult.getTargetList(), specResult.getSyncInfoList());

        //导出货品到目标商户并保存前后关联id
        List<MallProductBean> originalProductList = productService.findProduct(currentCustomerId, goods);
        SyncResultBean<MallProductBean> productResult = productService.batchSave(targetCustomerId, originalProductList);
        //处理关联字段
        productService.handleAssociatedInfo(productResult.getTargetList(), goodsResult.getSyncInfoList(), specResult.getSyncInfoList(), specValueResult.getSyncInfoList());

        //处理商品中的关联冗余字段，spec,pdt_spec,spec_desc
        goodsService.handleSpecAndPdtInfo(goodsResult.getTargetList(), specResult.getSyncInfoList(), productResult.getSyncInfoList(), specValueResult.getSyncInfoList());

        //导出goodstypedesc信息到目标商户
        List<MallGoodsTypeSpecBean> originalTypeSpec = goodsTypeSpecService.findByCustomerId(currentCustomerId);
        goodsTypeSpecService.batchSave(targetCustomerId, originalTypeSpec, specResult.getSyncInfoList(), goodsTypeResult.getSyncInfoList());


        //导出goodsSpecIndex信息到目标商户
        List<MallGoodsSpecIndexBean> originalSpecIndex = specIndexService.findSpecIndex(currentCustomerId, goods);
        specIndexService.batchSave(targetCustomerId,
                originalSpecIndex,
                goodsTypeResult.getSyncInfoList(),
                specResult.getSyncInfoList(),
                specValueResult.getSyncInfoList(),
                goodsResult.getSyncInfoList(),
                productResult.getSyncInfoList());
    }

    public void exportUpdate(List<Integer> goods, int targetCustomerId, int currentCustomerId)
            throws IllegalAccessException, InstantiationException, InvocationTargetException, CloneNotSupportedException, IOException {
        //得到商户关联信息
        List<MallSyncInfoBean> syncInfoList = syncInfoService.findByToCustomerId(targetCustomerId);

        //批量更新信息到目标商户，如果为存在，则新增
        //更新brand
        List<MallBrandBean> originalBrands = brandService.findByCustomerId(currentCustomerId);
        brandService.batchUpdate(originalBrands, syncInfoList, targetCustomerId);
        //更新cat
        List<MallGoodsCatBean> originalCats = goodsCatService.findByCustomerId(currentCustomerId);
        List<MallGoodsCatBean> targetCats = goodsCatService.batchUpdate(targetCustomerId, originalCats, syncInfoList);
        //更新商品类型
        List<MallGoodsTypeBean> originalTypes = goodsTypeService.findByCustomerId(currentCustomerId);
        goodsTypeService.batchUpdate(targetCustomerId, originalTypes, syncInfoList);
        //更新商品详细
        List<MallGoodsBean> originalGoods = goodsService.findGoods(currentCustomerId, goods);
        List<MallGoodsBean> targetGoods = goodsService.batchUpdate(originalGoods, syncInfoList, targetCustomerId);
        //更新商品图片
        List<MallGImagesBean> originalImgs = gImageService.findGImage(currentCustomerId, goods);
        List<MallGImagesBean> targetImgs = gImageService.batchUpdate(originalImgs, syncInfoList, targetCustomerId);
        //更新商品规格
        List<MallSpecificationBean> originalSpecs = specificationService.findByCustomerId(currentCustomerId);
        specificationService.batchUpdate(targetCustomerId, originalSpecs, syncInfoList);
        //更新规格值
        List<MallSpecValuesBean> originalSpecValues = specValueService.findByCustomerId(currentCustomerId);
        List<MallSpecValuesBean> targetSpecValues = specValueService.batchUpdate(targetCustomerId, originalSpecValues, syncInfoList);
        //更新货品
        List<MallProductBean> originalPros = productService.findProduct(currentCustomerId, goods);
        List<MallProductBean> targetPros = productService.batchUpdate(targetCustomerId, originalPros, syncInfoList);
        //更新goods_type_desc
        List<MallGoodsTypeSpecBean> originalTypeSpec = goodsTypeSpecService.findByCustomerId(currentCustomerId);
        goodsTypeSpecService.batchSave(targetCustomerId, originalTypeSpec, syncInfoList);
        //更新goods_spec_index
        List<MallGoodsSpecIndexBean> originalSpecIndex = specIndexService.findSpecIndex(currentCustomerId, goods);
        specIndexService.batchSave(targetCustomerId, originalSpecIndex, syncInfoList);

        //更新各个关联字段和冗余字段
        //商品分类关联字段处理
        goodsCatService.handleAssociatedInfo(targetCats, syncInfoList);
        //商品关联字段处理
        goodsService.handleAssociatedAllInfo(targetGoods, syncInfoList);
        //商品图片关联字段处理
        gImageService.handleAssociatedInfo(targetImgs, syncInfoList);
        //规格值specid字段处理
        specValueService.handleSpecId(targetSpecValues, syncInfoList);
        //货品关联字段处理
        productService.handleAssociatedInfo(targetPros, syncInfoList);
    }
}
