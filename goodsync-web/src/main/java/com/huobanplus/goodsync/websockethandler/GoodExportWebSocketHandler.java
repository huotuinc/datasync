package com.huobanplus.goodsync.websockethandler;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

/**
 * Created by liual on 2015-09-07.
 */
@Controller
public class GoodExportWebSocketHandler {
    private SimpMessagingTemplate template;

    @Autowired
    public GoodExportWebSocketHandler(SimpMessagingTemplate template) {
        this.template = template;
    }

    @MessageMapping("/message")
    public void message(TestBean testBean) {
        System.out.println("in");
    }
}

@Data
class TestBean {
    private String name;
}
