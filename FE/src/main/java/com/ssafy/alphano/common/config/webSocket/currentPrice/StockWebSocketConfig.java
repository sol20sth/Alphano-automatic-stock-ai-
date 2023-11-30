package com.ssafy.alphano.common.config.webSocket.currentPrice;

import com.ssafy.alphano.api.member.service.query.MemberQueryService;
import com.ssafy.alphano.common.config.webSocket.currentPrice.webSocketHandler.StockSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.WebSocketConnectionManager;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSocket
public class StockWebSocketConfig implements WebSocketConfigurer {

    Map<String, StockSocketHandler> stockSocketHandlerMap = new HashMap<>();

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        WebSocketClient webSocketClient = new StandardWebSocketClient();

        String price = "6b1e9ed1-b500-4b39-8ca8-3b3ade70ee45";

        StockSocketHandler stockSocketHandler = new StockSocketHandler(price, true);
        WebSocketConnectionManager connectionManager = new WebSocketConnectionManager(webSocketClient, stockSocketHandler, "ws://ops.koreainvestment.com:31000");
        connectionManager.setAutoStartup(true);
        connectionManager.start();

    }
    public Map<String, StockSocketHandler> getStockSocketHandlerMap() {
        return this.stockSocketHandlerMap;
    }


}
