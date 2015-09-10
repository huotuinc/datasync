package com.huobanplus.goodsync.datacenter.common;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

/**
 * Created by liual on 2015-09-10.
 */
@Component
public class MessageHandler extends TextWebSocketHandler {

    private WebSocketSession session;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        this.session = session;
        session.sendMessage(new TextMessage("connected"));
    }

    public void sendMessage(String msg) throws IOException {
        System.out.println("send:" + msg);
        session.sendMessage(new TextMessage(msg));
    }
}
