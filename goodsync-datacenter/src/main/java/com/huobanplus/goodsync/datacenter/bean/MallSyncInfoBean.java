package com.huobanplus.goodsync.datacenter.bean;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by liual on 2015-09-01.
 */
@Entity
@Table(name = "Mall_SyncInfo")
@Data
public class MallSyncInfoBean {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private int fromId;
    private String fromDesc;
    private int toId;
    private String toDesc;
    @Column(name = "SYNC_TYPE")
    private String type;
    private int fromCustomerId;
    private int toCustomerId;
}
