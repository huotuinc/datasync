package com.huobanplus.goodsync.controller;

import com.huobanplus.goodsync.common.ApiResult;
import com.huobanplus.goodsync.handler.bean.AuthorBaseBean;
import com.huobanplus.goodsync.handler.bean.ExportResult;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 导出商品到其他平台基础接口
 * Created by liual on 2015-09-06.
 */
public interface ExportGoodController {
    /**
     * 授权
     *
     * @param request
     * @return 授权结果, 1表示成功
     */
    ApiResult authorization(HttpServletRequest request);

    /**
     * 导出到相应平台
     *
     * @param goodList 选择的商品id，逗号分隔，all表示全部
     * @param request
     * @return 返回导出结果
     */
    ApiResult export(String goodList, HttpServletRequest request) throws IOException, CloneNotSupportedException;
}
