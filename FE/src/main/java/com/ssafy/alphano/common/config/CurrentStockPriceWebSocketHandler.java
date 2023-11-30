package com.ssafy.alphano.common.config;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class CurrentStockPriceWebSocketHandler extends TextWebSocketHandler {

    String stockCode;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String queryString = session.getUri().getQuery();
        System.out.println("Received query string: " + queryString);

        session.sendMessage(new TextMessage("Hello, " + queryString));
    }
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        String payload = message.getPayload();
        System.out.println("Received message: " + payload);
        session.sendMessage(new TextMessage("Received your message: " + payload));

    }
}
