package com.huobanplus.goodsync.datacenter.bean;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by liual on 2015-09-01.
 */
@Entity
@Table(name = "Mall_Products")
@Data
public class MallProductBean {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Product_Id")
    private Integer productId;
    @Column(name = "Goods_Id")
    private int goodsId;
    @Column(name = "Barcode")
    private String barCode;
    @Column(name = "Title")
    private String title;
    @Column(name = "Bn")
    private String bn;
    @Column(name = "Price")
    private double price;
    @Column(name = "Cost")
    private double cost;
    @Column(name = "Mktprice")
    private double mktPrice;
    @Column(name = "Name")
    private String name;
    @Column(name = "Weight")
    private double weight;
    @Column(name = "Unit")
    private String unit;
    @Column(name = "Store")
    private int store;
    @Column(name = "Store_Place")
    private String storePlace;
    @Column(name = "Freez")
    private int freez;
    @Column(name = "Pdt_Desc")
    private String pdtDesc;
    @Column(name = "Props")
    private String props;
    @Column(name = "Uptime")
    private long upTime;
    @Column(name = "Last_Modify")
    private Date lastModify;
    @Column(name = "Disabled")
    private int disabled;
    @Column(name = "Marketable")
    private int markedable;
    @Column(name = "Is_Local_Stock")
    private int isLocalStock;
    @Column(name = "Customer_Id")
    private int customerId;
    @Column(name = "UserPriceInfo")
    private String userPriceInfo;
    @Column(name = "UserIntegralInfo")
    private String userIntegralInfo;
    @Column(name = "TestUserIntegralInfo")
    private String testUserIntegralInfo;
}
