package com.huobanplus.goodsync.datacenter.service.impl;

import com.alibaba.fastjson.JSON;
import com.huobanplus.goodsync.datacenter.bean.MallGoodsBean;
import com.huobanplus.goodsync.datacenter.bean.MallGoodsLvPrice;
import com.huobanplus.goodsync.datacenter.bean.MallProductBean;
import com.huobanplus.goodsync.datacenter.dao.BulkDiscountDao;
import com.huobanplus.goodsync.datacenter.model.PriceDesc;
import com.huobanplus.goodsync.datacenter.service.BulkDiscountService;
import com.huobanplus.goodsync.datacenter.service.GoodsService;
import com.huobanplus.goodsync.datacenter.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liual on 2015-12-08.
 */
@Service
public class BulkDiscountServiceImpl implements BulkDiscountService {
    @Autowired
    private BulkDiscountDao bulkDiscountDao;

    @Override
    public void goodDiscount(int goodId, double discount) {
        //更新商品销售价
        bulkDiscountDao.goodDiscount(goodId, discount);

        //更新货品销售价
        bulkDiscountDao.productDiscount(goodId, discount);

        //更新关联表和冗余字段
        //首先删除关联表中所有的信息
        bulkDiscountDao.deleteLvPriceByGoodId(goodId);
        //更新货品中的冗余字段
        List<MallProductBean> productBeans = bulkDiscountDao.findUserPriceInfoByGoodId(goodId);
        for (MallProductBean productBean : productBeans) {
            if (!StringUtils.isEmpty(productBean.getUserPriceInfo())) {
                String[] userPriceInfo = productBean.getUserPriceInfo().split("\\|");
                String resultUserPriceInfoStr = "";
                for (String userPriceStr : userPriceInfo) {
                    String[] userLvPrice = userPriceStr.split(":");
                    double userPrice = Double.parseDouble(userLvPrice[1]);
                    int maxIntegral = Integer.parseInt(userLvPrice[2]);
                    BigDecimal userPriceDecimal = new BigDecimal(userPrice * discount);
                    userPrice = userPriceDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    maxIntegral = (int) Math.floor(maxIntegral * discount);
                    String resultUserPriceStr = userLvPrice[0] + ":" + userPrice + ":" + maxIntegral;
                    resultUserPriceInfoStr += resultUserPriceStr + "|";

                    //构造Mall_Goods_Lv_Price并保存
                    MallGoodsLvPrice goodsLvPrice = new MallGoodsLvPrice();
                    goodsLvPrice.setProductId(productBean.getProductId());
                    goodsLvPrice.setLevelId(Integer.parseInt(userLvPrice[0]));
                    goodsLvPrice.setGoodsId(productBean.getGoodsId());
                    goodsLvPrice.setPrice(userPrice);
                    goodsLvPrice.setCustomerId(productBean.getCustomerId());
                    goodsLvPrice.setMaxIntegral(maxIntegral);
                    bulkDiscountDao.saveGoodLvPrice(goodsLvPrice);
                }

                //保存货品冗余字段
                bulkDiscountDao.updateProductUserPriceInfo(productBean.getProductId(), resultUserPriceInfoStr);
            }
        }
        //更新商品中的会员价冗余字段
        List<PriceDesc> priceDescs = bulkDiscountDao.findMinMax(goodId);
        String priceDesc = JSON.toJSONString(priceDescs);
        bulkDiscountDao.updatePriceDesc(goodId, priceDesc);
    }

    @Override
    public void userPriceDiscount(int goodId, double discount) {

    }
}


