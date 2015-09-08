package com.huobanplus.goodsync.datacenter.config;

import com.huobanplus.goodsync.datacenter.common.PreBatchDel;
import com.huobanplus.goodsync.datacenter.service.BatchDeletable;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;


/**
 * Created by liual on 2015-09-08.
 */
@Aspect
@Component
public class AspectArchitecture {

    @Before("execution(* batchSave(..))")
    public void batchDelete() {
        System.out.println("--------------------sdfsdfsdf-----------------");
    }

    @Before("@annotation(preBatchDel) && args(targetCustomerId,..) && target(bean)")
    public void batchDeleteTest(PreBatchDel preBatchDel, int targetCustomerId, BatchDeletable bean) {
        System.out.println("in");
    }
}