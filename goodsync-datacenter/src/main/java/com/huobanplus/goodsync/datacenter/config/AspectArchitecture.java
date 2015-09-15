package com.huobanplus.goodsync.datacenter.config;

import com.huobanplus.goodsync.datacenter.common.Message;
import com.huobanplus.goodsync.datacenter.common.MessageHandler;
import com.huobanplus.goodsync.datacenter.common.PreBatchDel;
import com.huobanplus.goodsync.datacenter.service.BatchDeletable;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;


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
    public void beforeMsg(Message message) throws IOException {
        String beforeMessage = "正在" + message.operation() + message.desc() + "...";
        messageHandler.sendMessage(beforeMessage);
    }

    @After("@annotation(message)")
    public void afterMsg(Message message) throws IOException {
        String afterMessage = message.operation() + message.desc() + "成功";
        messageHandler.sendMessage(afterMessage);
    }

    @AfterThrowing("@annotation(message)")
    public void errorMsg(Message message) throws IOException {
        String throwMessage = message.operation() + message.desc() + "失败";
        messageHandler.sendMessage(throwMessage);
    }
}
