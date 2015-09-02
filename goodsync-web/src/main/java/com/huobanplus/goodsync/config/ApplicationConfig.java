package com.huobanplus.goodsync.config;

import com.huobanplus.goodsync.datacenter.config.DataCenterConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by liual on 2015-09-01.
 */
@Configuration
@ComponentScan(basePackages = {"com.huobanplus.goodsync"})
@Import({DataCenterConfig.class})
public class ApplicationConfig {
}
