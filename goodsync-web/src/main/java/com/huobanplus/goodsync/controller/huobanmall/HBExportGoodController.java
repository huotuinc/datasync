package com.huobanplus.goodsync.controller.huobanmall;

import com.huobanplus.goodsync.common.ApiResult;
import com.huobanplus.goodsync.common.ResultCodeEnum;
import com.huobanplus.goodsync.controller.ExportGoodController;
import com.huobanplus.goodsync.handler.SyncHandler;
import com.huobanplus.goodsync.handler.SyncHandlerBuilder;
import com.huobanplus.goodsync.handler.bean.AuthorBaseBean;
import com.huobanplus.goodsync.handler.bean.ExportResult;
import com.huobanplus.goodsync.handler.bean.HBAuthorBean;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by liual on 2015-09-06.
 */
@Controller
@RequestMapping("/huobanmall")
public class HBExportGoodController implements ExportGoodController {
    private static final Log log = LogFactory.getLog(HBExportGoodController.class);
    @Autowired
    private SyncHandlerBuilder syncHandlerBuilder;

    @Override
    @RequestMapping(value = "/authority", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult authorization(HttpServletRequest request) {
        SyncHandler syncHandler = syncHandlerBuilder.buildHandler(0);
        int currentCustomerId = (int) request.getSession().getAttribute("customerId");
        String account = request.getParameter("account");
        String password = request.getParameter("password");
        AuthorBaseBean authorBaseBean = new HBAuthorBean();
        authorBaseBean.setAccount(account);
        authorBaseBean.setPassword(DigestUtils.md5Hex(password));
        HBAuthorBean authorResult = (HBAuthorBean) syncHandler.authorization(authorBaseBean);
        if (authorResult == null) {
            return ApiResult.resultWith(ResultCodeEnum.AUTHORITY_FAILED.getResultCode(), "授权失败");
        }
        if (currentCustomerId == authorResult.getCustomerId()) {
            return ApiResult.resultWith(ResultCodeEnum.SYNC_SAME_ACCOUNT.getResultCode(), "授权账户为当前登录账户");
        }

        request.getSession().setAttribute("authorResult", authorResult);
        return ApiResult.resultWith(ResultCodeEnum.SUCCESS.getResultCode(), "授权成功");
    }

    @Override
    @RequestMapping(value = "/export", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult export(String goodList, HttpServletRequest request)
            throws IOException, CloneNotSupportedException, IllegalAccessException, InstantiationException, InvocationTargetException {
        HBAuthorBean authorResult = (HBAuthorBean) request.getSession().getAttribute("authorResult");
        if (authorResult == null) {
            return ApiResult.resultWith(ResultCodeEnum.AUTHORITY_FAILED.getResultCode(), "授权失败");
        }
        int currentCustomerId = (int) request.getSession().getAttribute("customerId");
        SyncHandler syncHandler = syncHandlerBuilder.buildHandler(0);
        syncHandler.goodExport(authorResult, currentCustomerId, goodList);
        return ApiResult.resultWith(ResultCodeEnum.SUCCESS.getResultCode(), "成功导入目标账户");
    }
}
