package com.huobanplus.goodsync.datacenter.bean;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by liual on 2015-09-01.
 */
@Entity
@Table(name = "Mall_Products")
@Setter
@Getter
@Cacheable(false)
public class MallProductBean implements Cloneable {
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
    private Date upTime;
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

    @ElementCollection
    @CollectionTable(
            name = "Mall_Goods_Lv_Price",
            joinColumns = @JoinColumn(name = "Product_Id")
    )
    private List<MallGoodLvPrice> goodLvPriceList;

    @Override
    public Object clone() throws CloneNotSupportedException {
        MallProductBean o = null;
        try {
            o = (MallProductBean) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return o;
    }
}
