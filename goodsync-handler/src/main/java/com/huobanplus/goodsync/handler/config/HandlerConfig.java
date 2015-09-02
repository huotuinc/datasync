package com.huobanplus.goodsync.handler.config;

import com.huobanplus.goodsync.handler.GoodSyncHandlerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * Created by liual on 2015-09-01.
 */
@Configuration
@ComponentScan("com.huobanplus.goodsync.handler")
public class HandlerConfig {
    @Autowired
    private GoodSyncHandlerBuilder goodSyncHandlerBuilder;
    @PostConstruct
    public void init(){
        goodSyncHandlerBuilder.addHandler();
    }
}
