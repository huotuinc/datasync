package com.huobanplus.goodsync.datacenter.bean;

import lombok.Data;

import javax.persistence.*;

/**
 * 无关联，先导入
 * Created by liual on 2015-09-01.
 */
@Entity
@Table(name = "Mall_Brand")
@Data
public class MallBrandBean {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Brand_Id")
    private Integer brandId;
    @Column(name = "Supplier_Id")
    private long supplierId;
    @Column(name = "Supplier_Brand_Id")
    private int supplierBrandId;
    @Column(name = "Brand_Name")
    private String brandName;
    @Column(name = "Brand_Url")
    private String brandUrl;
    @Column(name = "Brand_Desc")
    private String brandDesc;
    @Column(name = "Brand_Logo")
    private String brandLogo;
    @Column(name = "Brand_Keywords")
    private String brandKeywords;
    @Column(name = "Disabled")
    private int disabled;
    @Column(name = "Ordernum")
    private int orderNum;
    @Column(name = "Customer_Id")
    private int customerId;
}
