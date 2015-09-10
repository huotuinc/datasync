package com.huobanplus.goodsync.controller.huobanmall;

import com.huobanplus.goodsync.config.ApplicationConfig;
import com.huobanplus.goodsync.config.WebConfig;
import com.huobanplus.goodsync.datacenter.bean.MallGoodsBean;
import com.huobanplus.goodsync.datacenter.service.GoodsService;
import com.huobanplus.goodsync.handler.SyncHandlerBuilder;
import com.huobanplus.goodsync.handler.bean.HBAuthorBean;
import common.SpringMvcTest;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by liual on 2015-09-07.
 */
@ActiveProfiles("test")
@ContextConfiguration(classes = {ApplicationConfig.class, WebConfig.class})
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
//@Transactional
public class HBExportGoodControllerTest extends SpringMvcTest {
    @Autowired
    private SyncHandlerBuilder syncHandlerBuilder;
    @Autowired
    private GoodsService goodsService;
    private HBAuthorBean hbAuthorBean;

    @Before
    public void setUp() throws Exception {
        mockHttpSession.setAttribute("customerId", 5);

        hbAuthorBean = new HBAuthorBean();
        hbAuthorBean.setAccount("goodsynctest");
        hbAuthorBean.setPassword(DigestUtils.md5Hex("123456"));
        hbAuthorBean = (HBAuthorBean) syncHandlerBuilder.buildHandler(0).authorization(hbAuthorBean);

        mockHttpSession.setAttribute("authorResult", hbAuthorBean);
    }

    @Test
    public void testAuthorization() throws Exception {
        mockMvc.perform(post("/huobanmall/authority")
                .param("account", hbAuthorBean.getAccount())
                .param("password", "123456")
                .session(mockHttpSession))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testExport() throws Exception {
        List<MallGoodsBean> goodsList = goodsService.findByCustomerId(5);
        String goodsStr = goodsList.get(0).getGoodsId() + "," + goodsList.get(1).getGoodsId();
        mockMvc.perform(post("/huobanmall/export")
                .param("goodList", goodsStr)
                .session(mockHttpSession))
                .andDo(print())
                .andExpect(status().isOk());

//        mockMvc.perform(post("/huobanmall/export")
//                .param("goodList", goodsStr)
//                .session(mockHttpSession))
//                .andDo(print())
//                .andExpect(status().isOk());
    }
}