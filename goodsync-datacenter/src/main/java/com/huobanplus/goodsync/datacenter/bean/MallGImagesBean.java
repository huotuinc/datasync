package com.huobanplus.goodsync.datacenter.bean;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by liual on 2015-09-01.
 */
@Entity
@Table(name = "Mall_Gimages")
@Data
public class MallGImagesBean implements Cloneable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Gimage_Id")
    private Integer gimageId;
    @Column(name = "Goods_Id")
    private int goodId;
    @Column(name = "Is_Remote")
    private int isRemote;
    @Column(name = "Source")
    private String source;
    @Column(name = "Orderby")
    private int orderBy;
    @Column(name = "Src_Size_Width")
    private int srcSizeWidth;
    @Column(name = "Src_Size_Height")
    private int srcSizeHeight;
    @Column(name = "Small")
    private String small;
    @Column(name = "Big")
    private String big;
    @Column(name = "Thumbnail")
    private String thumbnail;
    @Column(name = "Up_Time")
    private Date upTime;
    @Column(name = "Supplier_Id")
    private long supplierId;
    @Column(name = "Supplier_Gimage_Id")
    private int supplierGimageId;
    @Column(name = "Sync_Time")
    private Date syncTime;
    @Column(name = "Customer_Id")
    private int customerId;
    @Override
    public Object clone() throws CloneNotSupportedException {
        MallGImagesBean o = null;
        try {
            o = (MallGImagesBean) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return o;
    }
}
