package com.ssafy.alphano.common.config.webSocket.orderNotice;

import com.ssafy.alphano.common.config.webSocket.currentPrice.webSocketHandler.StockSocketHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class NoticeWebSocketConfig  implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        WebSocketClient webSocketClient = new StandardWebSocketClient();
//        String price = memberQueryService.findMemberPrivateInfo(12L).getApprovalKey();
//        String checkOrder = memberQueryService.findMemberPrivateInfo(15L).getApprovalKey();
        String notice = "50304182-7b05-469a-b88a-132193b1a9ef";

        StockSocketHandler stockSocketHandler = new StockSocketHandler(notice, false);
        WebSocketConnectionManager connectionManager = new WebSocketConnectionManager(webSocketClient, stockSocketHandler, "ws://ops.koreainvestment.com:31000");
        connectionManager.setAutoStartup(true);
        connectionManager.start();

    }
}
