package com.huobanplus.goodsync.datacenter.dao.impl;

import com.huobanplus.goodsync.datacenter.bean.MallGoodsLvPrice;
import com.huobanplus.goodsync.datacenter.bean.MallProductBean;
import com.huobanplus.goodsync.datacenter.dao.BulkDiscountDao;
import com.huobanplus.goodsync.datacenter.model.PriceDesc;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by liual on 2015-12-08.
 */
@Repository
public class BulkDiscountDaoImpl extends BaseDaoImpl implements BulkDiscountDao {

    @Override
    public void productDiscount(int goodId, double discount) {
        String sql = "UPDATE Mall_Products SET Price=ROUND(Price*?,2) WHERE Goods_Id=?";
        jdbcTemplate.update(sql, discount, goodId);
    }

    @Override
    public void goodDiscount(int goodId, double discount) {
        String sql = "UPDATE Mall_Goods SET Price=round(Price*?,2) WHERE Goods_Id=?";
        jdbcTemplate.update(sql, discount, goodId);
    }


    @Override
    public List<PriceDesc> findMinMax(int goodId) {
        String sql = "SELECT  Level_Id ,MIN(Price) AS MinPrice ,MAX(Price) AS MaxPrice ," +
                "MIN(MaxIntegral) AS MinCashIntegral ,MAX(MaxIntegral) AS MaxCashIntegral " +
                "FROM  Mall_Goods_Lv_Price WHERE  Goods_Id = ? GROUP BY Level_Id";
        List<PriceDesc> priceDescs = jdbcTemplate.query(sql, ((resultSet, num) -> {
            PriceDesc priceDesc = new PriceDesc();
            priceDesc.setLevelId(resultSet.getInt("Level_Id"));
            priceDesc.setMinPrice(resultSet.getDouble("MinPrice"));
            priceDesc.setMaxPrice(resultSet.getDouble("MaxPrice"));
            priceDesc.setMinIntegral(resultSet.getInt("MinCashIntegral"));
            priceDesc.setMaxIntegral(resultSet.getInt("MaxCashIntegral"));
            return priceDesc;
        }), goodId);
        return priceDescs;
    }

    @Override
    public void updatePriceDesc(int goodId, String priceDesc) {
        String sql = "UPDATE Mall_Goods SET Price_LevelDesc=? WHERE Goods_Id=?";
        jdbcTemplate.update(sql, priceDesc, goodId);
    }

    public void deleteLvPriceByGoodId(int goodId) {
        String sql = "DELETE FROM Mall_Goods_Lv_Price WHERE Goods_Id=?";
        jdbcTemplate.update(sql, goodId);
    }

    @Override
    public void saveGoodLvPrice(MallGoodsLvPrice mallGoodsLvPrice) {
        String sql = "INSERT INTO Mall_Goods_Lv_Price VALUES(?,?,?,?,?,?)";
        jdbcTemplate.update(sql, mallGoodsLvPrice.getProductId(), mallGoodsLvPrice.getLevelId(), mallGoodsLvPrice.getGoodsId(),
                mallGoodsLvPrice.getPrice(), mallGoodsLvPrice.getCustomerId(), mallGoodsLvPrice.getMaxIntegral());
    }

    @Override
    public List<MallProductBean> findUserPriceInfoByGoodId(int goodId) {
        String sql = "SELECT Product_Id,UserPriceInfo,Goods_Id,Customer_Id from Mall_Products where Goods_Id=?";
        List<MallProductBean> productBeans = jdbcTemplate.query(sql, (resultSet, i) -> {
            MallProductBean productBean = new MallProductBean();
            productBean.setUserPriceInfo(resultSet.getString("UserPriceInfo"));
            productBean.setProductId(resultSet.getInt("Product_Id"));
            productBean.setGoodsId(resultSet.getInt("Goods_Id"));
            productBean.setCustomerId(resultSet.getInt("Customer_Id"));
            return productBean;
        }, goodId);
        return productBeans;
    }

    @Override
    public void updateProductUserPriceInfo(int productId, String userPriceInfo) {
        String sql = "UPDATE Mall_Products SET UserPriceInfo=? WHERE Product_Id=?";
        jdbcTemplate.update(sql, userPriceInfo, productId);
    }
}
