package com.huobanplus.goodsync.handler;

import com.huobanplus.goodsync.handler.bean.AuthorBaseBean;
import com.huobanplus.goodsync.handler.bean.ExportResult;
import org.eclipse.persistence.exceptions.DatabaseException;

import java.io.IOException;

/**
 * 商品相关数据导入导出处理
 * <p>导出导入规则相对web端登陆的伙伴商城用户</p>
 * <p style='color:red;'>note :</p>
 * <p>1.导出的基础数据为登录商户的商品数据，其他平台的商品数据将被覆盖</p>
 * <p>2.从其他平台导入的数据将覆盖登录商户中的相关商品数据</p>
 * Created by liual on 2015-09-01.
 */
public interface SyncHandler {
    /**
     * 平台授权
     * <p>不同平台自定义授权方式</p>
     * <ul>
     * <li>伙伴商城：传入用户名密码</li>
     * <li>淘宝：todo</li>
     * </ul>
     *
     * @param authorBase 授权信息
     * @return
     */
    AuthorBaseBean authorization(AuthorBaseBean authorBase);

    /**
     * 商品数据导出
     *
     * @param authorBase
     * @param loginCustomerId 当前登录商户id
     * @param goodList        待导出的商品列表，逗号分隔，all表示全部
     * @throws IOException
     */
    void goodExport(AuthorBaseBean authorBase, int loginCustomerId, String goodList) throws IOException,DatabaseException, CloneNotSupportedException;

    /**
     * 商品数据导入
     */
    void goodImport(AuthorBaseBean authorBase, int loginCustomerId);
}
