package com.huobanplus.goodsync.datacenter.bean;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by liual on 2015-09-01.
 */
@Entity
@Table(name = "Mall_Goods_Type")
@Data
public class MallGoodsTypeBean implements Cloneable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Type_Id")
    private Integer typeId;
    @Column(name = "Name")
    private String name;
    @Column(name = "Alias")
    private String alias;
    @Column(name = "Is_Physical")
    private int isPhysical;
    @Column(name = "Supplier_Id")
    private long supplierId;
    @Column(name = "Supplier_Type_Id")
    private int supplierTypeId;
    @Column(name = "Schema_Id")
    private String schemaId;
    @Column(name = "Props")
    private String props;
    @Column(name = "Spec")
    private String spec;
    @Column(name = "Setting")
    private String setting;
    @Column(name = "Minfo")
    private String minfo;
    @Column(name = "Params")
    private String params;
    @Column(name = "Dly_Func")
    private int dlyFunc;
    @Column(name = "Ret_Func")
    private int retFunc;
    @Column(name = "Reship")
    private int reship;
    @Column(name = "Disabled")
    private int disabled;
    @Column(name = "Is_Def")
    private int isDef;
    @Column(name = "Lastmodify")
    private Date lastModify;
    @Column(name = "Customer_Id")
    private int customerId;
    @Column(name = "Vir_CustomType")
    private int virCustomType;

    @Override
    public Object clone() throws CloneNotSupportedException {
        MallGoodsTypeBean o = null;
        try {
            o = (MallGoodsTypeBean) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return o;
    }
}
