package com.huobanplus.goodsync.datacenter.bean;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by liual on 2015-09-01.
 */
@Entity
@Table(name = "Mall_Spec_Values")
@Data
public class MallSpecValuesBean implements Cloneable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Spec_Value_Id")
    private Integer specValueId;
    @Column(name = "Spec_Id")
    private int specId;
    @Transient
    private MallSpecificationBean specificationBean;
    @Column(name = "Spec_Value")
    private String specValue;
    @Column(name = "Alias")
    private String alias;
    @Column(name = "Spec_Image")
    private String specImage;
    @Column(name = "P_Order")
    private int pOrder;
    @Column(name = "Supplier_Id")
    private long supplierId;
    @Column(name = "Supplier_Spec_Value_Id")
    private int supplierSpecValueId;
    @Column(name = "Customer_Id")
    private int customerId;

    @Override
    public Object clone() throws CloneNotSupportedException {
        MallSpecValuesBean o = null;
        try {
            o = (MallSpecValuesBean) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return o;
    }
}
