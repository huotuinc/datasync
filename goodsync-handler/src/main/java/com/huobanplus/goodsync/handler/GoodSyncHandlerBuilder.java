package com.huobanplus.goodsync.handler;

import com.huobanplus.goodsync.handler.huobanmall.HBGoodSyncHandler;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 得到商品导入导出处理实例
 * Created by liual on 2015-09-01.
 */
@Component
public class GoodSyncHandlerBuilder {
    public List<GoodSyncHandler> handlers;

    public List<GoodSyncHandler> getHandlers() {
        return handlers;
    }

    public void setHandlers(List<GoodSyncHandler> handlers) {
        this.handlers = handlers;
    }

    public void addHandler(GoodSyncHandler handler) {
        this.handlers.add(handler);
    }

    /**
     * 得到实例
     *
     * @param type 类型，0表示伙伴商城，1表示淘宝，todo
     * @return
     */
    public static GoodSyncHandler buildHandler(String type) {
        return null;
    }
}
