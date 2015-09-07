package com.huobanplus.goodsync.datacenter.dao.impl;

import com.huobanplus.goodsync.datacenter.bean.MallGoodsTypeSpecBean;
import com.huobanplus.goodsync.datacenter.dao.GoodsTypeSpecDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by liual on 2015-09-05.
 */
@Repository
public class GoodsTypeSpecDaoImpl extends BaseDaoImpl implements GoodsTypeSpecDao {
    @Override
    public List<MallGoodsTypeSpecBean> findByCustomerId(int customerId) {
        String sql = "SELECT * FROM Mall_Goods_Type_Spec WHERE GTS_Customer_Id=?0";
        List<MallGoodsTypeSpecBean> list = jdbcTemplate.query(sql, new Object[]{customerId}, (rs, rowNum) -> {
            MallGoodsTypeSpecBean goodsTypeSpecBean = new MallGoodsTypeSpecBean();
            goodsTypeSpecBean.setSpecId(rs.getInt("Spec_Id"));
            goodsTypeSpecBean.setTypeId(rs.getInt("Type_Id"));
            goodsTypeSpecBean.setSpecStyle(rs.getString("Spec_Style"));
            goodsTypeSpecBean.setCustomerId(rs.getInt("GTS_Customer_Id"));
            return goodsTypeSpecBean;
        });
        return list;
    }

    @Override
    public int add(MallGoodsTypeSpecBean goodsTypeSpec) {
        String sql = "INSERT INTO Mall_Goods_Type_Spec(Spec_Id,Type_Id,Spec_Style,Customer_Id) " +
                "values(?,?,?,?)";
        return jdbcTemplate.update(sql,
                goodsTypeSpec.getSpecId(),
                goodsTypeSpec.getTypeId(),
                goodsTypeSpec.getSpecStyle(),
                goodsTypeSpec.getCustomerId());
    }

    @Override
    public int update(MallGoodsTypeSpecBean goodsTypeSpec) {
        String sql = "UPDATE Mall_Goods_Type_Spec SET Spec_Id=?,Type_Id=?,Customer_Id=?";
        return jdbcTemplate.update(sql,
                goodsTypeSpec.getSpecId(),
                goodsTypeSpec.getTypeId(),
                goodsTypeSpec.getCustomerId());
    }
}
