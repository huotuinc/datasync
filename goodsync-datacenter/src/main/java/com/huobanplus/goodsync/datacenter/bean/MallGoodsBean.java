package com.huobanplus.goodsync.datacenter.bean;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by liual on 2015-09-01.
 */
@Entity
@Table(name = "Mall_Goods")
@Data
public class MallGoodsBean {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Goods_Id")
    private Integer goodsId;
    @Column(name = "Cat_Id")
    private int catId;

    @Transient
    private MallGoodsCatBean goodsCat;
    
    @Column(name = "typeId")
    private int typeId;

    @Transient
    private MallGoodsTypeBean goodsTypeBean;

    @Column(name = "Goods_Type")
    private String goodsType;
    @Column(name = "Brand_Id")
    private int brandId;

    @Transient
    private MallBrandBean brandBean;

    @Column(name = "Brand")
    private String brand;
    @Column(name = "Supplier_Id")
    private int supplierId;
    @Column(name = "Supplier_Goods_Id")
    private int supplierGoodsId;
    @Column(name = "Wss_Params")
    private String wssParams;
    @Column(name = "Image_Default")
    private String imageDefault;
    @Column(name = "Udfimg")
    private String udfImg;
    @Column(name = "Thumbnail_Pic")
    private String thumbnailPic;
    @Column(name = "Small_Pic")
    private String smallPic;
    @Column(name = "Big_Pic")
    private String bigPic;
    @Column(name = "Image_File")
    private String imageFile;
    @Column(name = "Brief")
    private String brief;
    @Column(name = "Intro")
    private String intro;
    @Column(name = "Mktprice")
    private double mktPrice;
    @Column(name = "Cost")
    private double cost;
    @Column(name = "Price")
    private double price;
    @Column(name = "Bn")
    private String bn;
    @Column(name = "Name")
    private String name;
    @Column(name = "Marketable")
    private int marktable;
    @Column(name = "Weight")
    private double weight;
    @Column(name = "Unit")
    private String unit;
    @Column(name = "Store")
    private int store;
    @Column(name = "Store_Place")
    private String storePlace;
    @Column(name = "Score_Setting")
    private String scoreSetting;
    @Column(name = "Score")
    private int score;
    @Column(name = "Spec")
    private String spec;
    @Column(name = "Pdt_Desc")
    private String pdtDesc;
    @Column(name = "Spec_Desc")
    private String specDesc;
    @Column(name = "Params")
    private String params;
    @Column(name = "Uptime")
    private long upTime;
    @Column(name = "Downtime")
    private long downTime;
    @Column(name = "Last_Modify")
    private Date lastModify;
    @Column(name = "Disabled")
    private int disabled;
    @Column(name = "Notify_Num")
    private int notifyNum;
    @Column(name = "Rank")
    private double rank;
    @Column(name = "Rank_Count")
    private int rankCount;
    @Column(name = "Comments_Count")
    private int commentsCount;
    @Column(name = "View_W_Count")
    private int viewWCount;
    @Column(name = "View_Count")
    private int viewCount;
    @Column(name = "Buy_Count")
    private int buyCount;
    @Column(name = "Buy_W_Count")
    private int buyWCount;
    @Column(name = "Count_Stat")
    private String countStat;
    @Column(name = "P_Order")
    private int pOrder;
    @Column(name = "D_Order")
    private int dOrder;
    @Column(name = "P_1")
    private String p1;
    @Column(name = "P_2")
    private String p2;
    @Column(name = "P_3")
    private String p3;
    @Column(name = "P_4")
    private String p4;
    @Column(name = "P_5")
    private String p5;
    @Column(name = "P_6")
    private String p6;
    @Column(name = "P_7")
    private String p7;
    @Column(name = "P_8")
    private String p8;
    @Column(name = "P_9")
    private String p9;
    @Column(name = "P_10")
    private String p10;
    @Column(name = "P_11")
    private String p11;
    @Column(name = "P_12")
    private String p12;
    @Column(name = "P_13")
    private String p13;
    @Column(name = "P_14")
    private String p14;
    @Column(name = "P_15")
    private String p15;
    @Column(name = "P_16")
    private String p16;
    @Column(name = "P_17")
    private String p17;
    @Column(name = "P_18")
    private String p18;
    @Column(name = "P_19")
    private String p19;
    @Column(name = "P_20")
    private String p20;
    @Column(name = "P_21")
    private String p21;
    @Column(name = "P_22")
    private String p22;
    @Column(name = "P_23")
    private String p23;
    @Column(name = "P_24")
    private String p24;
    @Column(name = "P_25")
    private String p25;
    @Column(name = "P_26")
    private String p26;
    @Column(name = "P_27")
    private String p27;
    @Column(name = "P_28")
    private String p28;
    @Column(name = "Goods_Info_Update_Status")
    private int goodsInfoUpdateStatus;
    @Column(name = "Stock_Update_Status")
    private int stockUpdateStatus;
    @Column(name = "Marketable_Update_Status")
    private int markeableUpdateStatus;
    @Column(name = "Img_Update_Status")
    private int imgUpdateStatus;
    @Column(name = "Customer_Id")
    private int customerId;
    @Column(name = "Ex_Parameters")
    private String exParameters;
    @Column(name = "Ex_Consult")
    private String exConsult;
    @Column(name = "Ex_ShoppintTips")
    private String exShoppingTips;
    @Column(name = "GroupPurchase_For")
    private int groupPurchaseFor;
    @Column(name = "SellTags_Custom")
    private int sellTagsCustom;
    @Column(name = "SellTags")
    private String sellTags;
    @Column(name = "Vir_ProductId")
    private int virProductId;
    @Column(name = "LimitBuyNum")
    private int limitBuyNum;
    @Column(name = "RebateSaleRatio")
    private float rebateSaleRatio;
    @Column(name = "RebateQuatoRatio")
    private float rebateQuatoRatio;
    @Column(name = "Price_LevelDesc")
    private String priceLevelDesc;
    @Column(name = "customFieldValues")
    private String customFieldValues;
    @Column(name = "Subtitle")
    private String subTitle;
    @Column(name = "SalesCount")
    private int salesCount;
    @Column(name = "RebateSaleSetting")
    private String rebateSaleSetting;
    @Column(name = "RebateQuatoSetting")
    private String rebateQuatoSetting;
    @Column(name = "RebateMode")
    private int rebateMode;
    @Column(name = "Individuation")
    private int individuation;
}
