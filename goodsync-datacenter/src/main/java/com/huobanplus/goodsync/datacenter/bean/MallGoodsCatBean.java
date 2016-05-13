package com.huobanplus.goodsync.datacenter.bean;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by liual on 2015-09-01.
 */
@Entity
@Table(name = "Mall_Goods_Cat")
@Data
public class MallGoodsCatBean implements Cloneable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Cat_Id")
    private Integer catId;
    @Column(name = "Parent_Id")
    private int parentId;
    @Column(name = "Supplier_Id")
    private int supplierId;
    @Column(name = "Supplier_Cat_Id")
    private int supplierCatId;
    @Column(name = "Cat_Path")
    private String catPath;
    @Column(name = "Is_Leaf")
    private int isLeaf;
    @Column(name = "Type_Id")
    private int typeId;
    @Column(name = "Cat_Name")
    private String catName;
    @Column(name = "Disabled")
    private int disabled;
    @Column(name = "P_Order")
    private int pOrder;
    @Column(name = "Goods_Count")
    private int goodsCount;
    @Column(name = "Tabs")
    private String tabs;
    @Column(name = "Finder")
    private String finder;
    @Column(name = "Addon")
    private String addon;
    @Column(name = "Child_Count")
    private int childCount;
    @Column(name = "Customer_Id")
    private int customerId;
    @Column(name = "Cat_Pic")
    private String catPic;
    @Column(name = "Rewrite_Name")
    private String rewriteName;

    @Override
    public Object clone() throws CloneNotSupportedException {
        MallGoodsCatBean o = null;
        try {
            o = (MallGoodsCatBean) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return o;
    }

    public int getCatDepth() {
        return catPath.substring(1, catPath.length() - 1).split("\\|").length;
    }

    public String space() {
        int catDepth = getCatDepth();
        String space = "--";
        for (int i = 0; i < catDepth; i++) {
            space += space;
        }
        return space;
    }
}
