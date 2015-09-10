package com.huobanplus.goodsync.handler.huobanmall;

import com.huobanplus.goodsync.datacenter.bean.*;
import com.huobanplus.goodsync.datacenter.service.*;
import com.huobanplus.goodsync.handler.SyncHandler;
import com.huobanplus.goodsync.handler.bean.AuthorBaseBean;
import com.huobanplus.goodsync.handler.bean.HBAuthorBean;
import com.huobanplus.goodsync.handler.huobanmall.service.ExportService;
import org.eclipse.persistence.exceptions.DatabaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liual on 2015-09-01.
 */
@Service("hbSyncHandler")
public class HBSyncHandler implements SyncHandler {
    @Autowired
    private AccountService accountService;
    @Autowired
    private SyncInfoService syncInfoService;
    @Autowired
    private ExportService exportService;
    @Autowired
    private HttpSession session;

    @Override
    public AuthorBaseBean authorization(AuthorBaseBean authorBase) {
        int customerId = accountService.login(authorBase.getAccount(), authorBase.getPassword());
        if (customerId == 0)
            return null;
        else {
            HBAuthorBean hbAuthorBean = (HBAuthorBean) authorBase;
            hbAuthorBean.setCustomerId(customerId);
            return hbAuthorBean;
        }
    }

    @Override
    @Transactional
    public void goodExport(AuthorBaseBean authorBase, int loginCustomerId, String goodList)
            throws IOException, CloneNotSupportedException, IllegalAccessException, InvocationTargetException, InstantiationException {
        HBAuthorBean hbAuthorBean = (HBAuthorBean) authorBase;

        List<Integer> goods = null;
        if (!"all".equals(goodList)) {
            goods = new ArrayList<>();
            String[] goodsArray = goodList.split(",");
            for (String goodId : goodsArray) {
                goods.add(Integer.valueOf(goodId));
            }
        }
        int count = syncInfoService.countByToCustomerId(hbAuthorBean.getCustomerId());

        if (count == 0) {
            //add
            System.out.println("start add");
            exportService.exportAdd(loginCustomerId, hbAuthorBean.getCustomerId(), goods);
        } else {
            //update
            System.out.println("start update");
            exportService.exportUpdate(goods, hbAuthorBean.getCustomerId(), loginCustomerId);
        }
    }

    @Override
    public void goodImport(AuthorBaseBean authorBase, int loginCustomerId) {

    }
}
