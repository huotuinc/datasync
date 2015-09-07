package com.huobanplus.goodsync.datacenter.bean;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by liual on 2015-09-01.
 */
@Entity
@Table(name = "Mall_Specification")
@Data
public class MallSpecificationBean implements Cloneable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Spec_Id")
    private Integer specId;
    @Column(name = "Spec_Name")
    private String specName;
    @Column(name = "Alias")
    private String alias;
    @Column(name = "Spec_Show_Type")
    private String specShowType;
    @Column(name = "Spec_Type")
    private String specType;
    @Column(name = "Spec_Memo")
    private String specMemo;
    @Column(name = "P_Order")
    private int pOrder;
    @Column(name = "Disabled")
    private int disabled;
    @Column(name = "Supplier_Spec_Id")
    private int supplierSpecId;
    @Column(name = "Supplier_Id")
    private long supplierId;
    @Column(name = "Lastmodify")
    private Date lastModify;
    @Column(name = "Customer_Id")
    private int customerId;

    @Override
    public Object clone() throws CloneNotSupportedException {
        MallSpecificationBean o = null;
        try {
            o = (MallSpecificationBean) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return o;
    }
}
