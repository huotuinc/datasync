package com.huobanplus.goodsync.handler;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 得到商品导入导出处理实例
 * Created by liual on 2015-09-01.
 */
@Component
public class SyncHandlerBuilder {
    @Resource(name = "hbSyncHandler")
    private SyncHandler hbSyncHandler;

    /**
     * 得到实例
     *
     * @param type 类型，0表示伙伴商城，1表示淘宝，todo
     * @return
     */
    public SyncHandler buildHandler(int type) {
        SyncHandler syncHandler = null;
        switch (type) {
            case 0:
                syncHandler = hbSyncHandler;
        }
        return syncHandler;
    }
}
