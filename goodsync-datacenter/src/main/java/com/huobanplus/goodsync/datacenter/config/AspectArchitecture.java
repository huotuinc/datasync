package com.huobanplus.goodsync.datacenter.config;

import com.huobanplus.goodsync.datacenter.common.Message;
import com.huobanplus.goodsync.datacenter.common.MessageHandler;
import com.huobanplus.goodsync.datacenter.common.PreBatchDel;
import com.huobanplus.goodsync.datacenter.service.BatchDeletable;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Created by liual on 2015-09-08.
 */
@Aspect
@Component
public class AspectArchitecture {
    @Autowired
    private MessageHandler messageHandler;

    @Before("@annotation(preBatchDel) && args(targetCustomerId,..) && target(bean)")
    public void batchDelete(PreBatchDel preBatchDel, int targetCustomerId, BatchDeletable bean) {
        bean.deleteByCustomerId(targetCustomerId);
    }

    @Before("@annotation(message)")
    public void publishMsg(Message message) throws Throwable {
        messageHandler.sendMessage("哈哈哈哈");
    }
}
