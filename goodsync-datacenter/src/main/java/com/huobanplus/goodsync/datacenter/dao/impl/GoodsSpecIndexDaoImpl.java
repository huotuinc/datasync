package com.huobanplus.goodsync.datacenter.dao.impl;

import com.huobanplus.goodsync.datacenter.bean.MallGoodsSpecIndexBean;
import com.huobanplus.goodsync.datacenter.dao.GoodsSpecIndexDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by liual on 2015-09-05.
 */
@Repository
public class GoodsSpecIndexDaoImpl extends BaseDaoImpl implements GoodsSpecIndexDao {

    @Override
    public List<MallGoodsSpecIndexBean> findByCustomerId(int customerId) {
        String sql = "SELECT * FROM Mall_Goods_Spec_Index WHERE GSI_Customer_Id=?";
        List<MallGoodsSpecIndexBean> list = jdbcTemplate.query(sql, new Object[]{customerId}, (rs, rowNum) -> {
            MallGoodsSpecIndexBean specIndex = new MallGoodsSpecIndexBean();
            specIndex.setTypeId(rs.getInt("Type_Id"));
            specIndex.setSpecId(rs.getInt("Spec_Id"));
            specIndex.setSpecValueId(rs.getInt("Spec_Value_Id"));
            specIndex.setSpecValue(rs.getString("Spec_Value"));
            specIndex.setGoodsId(rs.getInt("Goods_Id"));
            specIndex.setProductId(rs.getInt("Product_Id"));
            specIndex.setCustomerId(rs.getInt("GSI_Customer_Id"));
            return specIndex;
        });
        return list;
    }

    @Override
    public int add(MallGoodsSpecIndexBean specIndex) {
        String sql = "INSERT INTO Mall_Goods_Spec_Index(Type_Id,Spec_Id,Spec_Value_Id,Spec_Value,Goods_Id,Product_Id,GSI_Customer_Id) " +
                "VALUES(?,?,?,?,?,?,?)";
        Object[] param = new Object[]{specIndex.getTypeId(), specIndex.getSpecId(), specIndex.getSpecValueId(), specIndex.getSpecValue(), specIndex.getGoodsId(), specIndex.getProductId(), specIndex.getCustomerId()};
        return jdbcTemplate.update(sql, param);
    }

    @Override
    public void deleteByCustomerId(int customerId) {
        String sql = "DELETE FROM Mall_Goods_Spec_Index WHERE GSI_Customer_Id=?";
        jdbcTemplate.update(sql, new Object[]{customerId});
    }
}
