package com.huobanplus.goodsync.datacenter.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by liual on 2015-09-10.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Message {
    /**
     * 操作
     *
     * @return
     */
    String operation();

    /**
     * 信息，比如商品，货品等
     *
     * @return
     */
    String desc();
}
