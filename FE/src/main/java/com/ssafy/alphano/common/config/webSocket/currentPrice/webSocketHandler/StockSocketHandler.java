package com.ssafy.alphano.common.config.webSocket.currentPrice.webSocketHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.alphano.api.member.middle.service.MemberOrderStockService;
import com.ssafy.alphano.api.redis.service.RedisService;
import com.ssafy.alphano.domain.member.Enum.BuySell;
import com.ssafy.alphano.util.BeanUtils;
import org.springframework.http.*;
import org.springframework.util.backoff.FixedBackOff;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.util.*;


public class StockSocketHandler extends TextWebSocketHandler {

    private WebSocketSession session;
    private final RestTemplate restTemplate;

    private static final long HEARTBEAT_INTERVAL = 30000; // 30초마다 Heartbeat 메시지 전송

    private Timer heartbeatTimer;

    private final RedisService redisService;

    private final MemberOrderStockService memberOrderStockService;

    String iv;

    String key;

    boolean isConnect = false;

    Map<String, Object> request = new HashMap<>();
    ObjectMapper objectMapper = new ObjectMapper();
    Map<String, Object> header = new HashMap<>();
    Map<String, Object> body = new HashMap<>();
    Map<String, Object> input = new HashMap<>();

    String approvalKey;

    boolean check;

    public StockSocketHandler(String approvalKey, boolean check) {
        this.redisService = (RedisService) BeanUtils.getBean("redisService");
        this.restTemplate = (RestTemplate) BeanUtils.getBean("restTemplate");
        this.memberOrderStockService = (MemberOrderStockService) BeanUtils.getBean("memberOrderStockService");
        this.approvalKey = approvalKey;
        this.check = check;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        this.session = session;
        super.afterConnectionEstablished(session);
        startHeartbeatTimer();
        if (check) {
            priceSocketConnect(approvalKey);
        } else {
            noticeSocketConnect(approvalKey);
        }

//        // 요청 메시지 생성 및 전송
    }

    private void startHeartbeatTimer() {
        heartbeatTimer = new Timer(true);
        heartbeatTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                sendHeartbeat();
            }
        }, HEARTBEAT_INTERVAL, HEARTBEAT_INTERVAL);
    }

    private void sendHeartbeat() {
        try {
            session.sendMessage(new TextMessage("Heartbeat")); // Heartbeat 메시지 전송
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void priceSocketConnect(String approvalKey) throws IOException {

        header.put("approval_key", approvalKey);
        header.put("custtype", "P");
        header.put("tr_type", "1");
        header.put("content-type", "utf-8");


        input.put("tr_id", "H0STCNT0");

        body.put("input", input);

        request.put("header", header);
        request.put("body", body);


        String[] trKeyList = new String[]{
                "207940",
                "207940",
                "207940",
                "207940",
                "207940",
                "207940",
                "047810",
                "005380",
                "005935",
                "005490",
                "005930",
                "000270",
                "051910",
                "035420",
                "012450",
                "068270",
                "105560",
                "017670",
                "015760",
                "028050",
                "034730",
                "009150",
                "018260",
                "329180",
                "010130",
                "047050",
                "326030",
                "316140",
                "024110",
                "005830",
                "012330",
                "011170",
                "028260",
                "055550",
                "066570",
                "035720",
                "032830",
                "088980",
                "003550",
                "086790",
                "000810",
                "033780"
        };
        for (String tr : trKeyList) {

            input.put("tr_key", tr);
            String jsonRequest = objectMapper.writeValueAsString(request);
            session.sendMessage(new TextMessage(jsonRequest));
        }

    }

    private void noticeSocketConnect(String approvalKey) throws IOException {

        System.out.println("noticeSocketConnect");
        header.put("approval_key", approvalKey);
        header.put("custtype", "P");
        header.put("tr_type", "1");
        header.put("content-type", "utf-8");

        input.put("tr_id", "H0STCNI9");
        input.put("tr_key", "duljji");

        body.put("input", input);

        request.put("header", header);
        request.put("body", body);

        String jsonRequest = objectMapper.writeValueAsString(request);
        session.sendMessage(new TextMessage(jsonRequest));

        input.put("tr_key", "macassis");
        jsonRequest = objectMapper.writeValueAsString(request);

        session.sendMessage(new TextMessage(jsonRequest));

    }

    public void testRequest(String trId, String trKey) throws IOException {

        header.put("approval_key", approvalKey);
        input.put("tr_id", trId);
        input.put("tr_key", trKey);

        String jsonRequest = objectMapper.writeValueAsString(request);

        session.sendMessage(new TextMessage(jsonRequest));

    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        if ("Heartbeat".equals(message.getPayload())) {
            return;
        }

        // 서버로부터 메시지를 받았을 때 실행되는 코드
        if (check) {
            String[] stockInfo = message.getPayload().split("\\^");
            System.out.println(Arrays.toString(stockInfo));
            if (stockInfo.length > 1) {
                String stockCode = stockInfo[0].split("[|]")[3];
                String stockPrice = stockInfo[2] + "/" + stockInfo[5] + "/" + stockInfo[4] + "/" + stockInfo[3] + "/" + stockInfo[7] + "/" + stockInfo[8] + "/" + stockInfo[9] + "/" + stockInfo[13];
                List<LinkedHashMap<String, Object>> list = (List<LinkedHashMap<String, Object>>) redisService.getList(stockCode);
                System.out.println(list);
                redisService.createCurrentStockPrice("+" + stockCode, stockPrice);


                if (list.size() > 0) {
                    System.out.println("redis 값 발견" + list);
                    List<LinkedHashMap<String, Object>> newList = new ArrayList<>();

                    for (LinkedHashMap<String, Object> redisMemberRequest : list) {
                        if (redisMemberRequest.get("targetPriceOptionInequality").equals("GREATER")) {
                            if ((int) redisMemberRequest.get("targetPrice") <= Integer.valueOf(stockInfo[2])) {
                                sendRequestToKIS(redisMemberRequest, stockCode);
                                continue;
                            }
                        } else {

                            if ((int) redisMemberRequest.get("targetPrice") >= Integer.valueOf(stockInfo[2])) {
                                sendRequestToKIS(redisMemberRequest, stockCode);

                                continue;
                            }
                        }
                        newList.add(redisMemberRequest);
                    }
                    if (list.size() != newList.size()) {
                        redisService.addValueForTest(stockCode, newList);
                    }
                }
            }
        } else {
            System.out.println("notice 메세지 받음" + key + " " + iv);
            System.out.println(message.getPayload());
            if (!isConnect) {
                isConnect = true;
                Map responseMessage = objectMapper.readValue(message.getPayload(), Map.class);
                System.out.println("responseMessage = " + responseMessage);
                Map body = (LinkedHashMap<String, Object>) responseMessage.get("body");
                System.out.println("body = " + body);
                Map output = (LinkedHashMap<String, Object>) body.get("output");
                System.out.println("output = " + output);
                if (output != null) {
                    key = (String) output.get("key");
                    iv = (String) output.get("iv");
                }
            } else {
                String[] responseMessage = message.getPayload().split("[|]");
                System.out.println(Arrays.toString(responseMessage));

                if (responseMessage.length >= 2) {

                    String notice = responseMessage[3];
                    String[] decryptMessage = aesCbcBase64Dec(key, iv, notice).split("\\^");

                    System.out.println("decryptMessage = " + Arrays.toString(decryptMessage));
                    createOrderListRequest(decryptMessage);

                }

            }


        }
    }

    private void createOrderListRequest(String[] decryptMessage) {
        String userName = decryptMessage[0];
        //String accountNumber = decryptMessage[1];
        String orderNumber = decryptMessage[2];
        String originOrderNum = decryptMessage[3];
        BuySell buySell;
        if (decryptMessage[4].equals("01")) {
            buySell = BuySell.S;
            System.out.println("매도");
        } else {
            buySell = BuySell.B;
            System.out.println("매수");
        }

        String stockOrderNumber = decryptMessage[6];
        String stockOrderTime = decryptMessage[7];
        String stockOrderPrice = decryptMessage[8];
        int orderCount = Integer.valueOf(decryptMessage[9]);
        int stockPrice = Integer.valueOf(decryptMessage[10]);
        String orderDate = decryptMessage[11];
        String stockOrderConditionQuantity = decryptMessage[12];
        String notice = decryptMessage[13];
        String completeCount = decryptMessage[19];

        // TODO : 주문번호로 값 받아서 업데이트 하기
        // TODO : 1. 주문 번호로 member_order_stock 받아오기
        // TODO : 2. member_order_stock의 주문 상태 업데이트 하기
        // TODO : 3. 주문 상태 업데이트 할 때, order_count와 count가 같으면 member_order_stock의 주문 상태를 완료로 업데이트 하기
        System.out.println(orderNumber + " " + orderCount + " " + stockPrice);
        if (stockPrice != 0) {
            memberOrderStockService.updateOrderCount(buySell, orderNumber, orderCount, stockPrice);
        }

    }

    private void sendRequestToKIS(LinkedHashMap<String, Object> redisMemberRequest, String stockCode) throws JsonProcessingException {
        String apiUrl = "https://openapivts.koreainvestment.com:29443/uapi/domestic-stock/v1/trading/order-cash?&&&";

        // 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + redisMemberRequest.get("authorization"));
        headers.set("appkey", (String) redisMemberRequest.get("appkey"));
        headers.set("appsecret", (String) redisMemberRequest.get("appsecret"));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("tr_id", (String) redisMemberRequest.get("buySell"));
        // HTTP 요청 설정
        HttpEntity<String> requestEntity = new HttpEntity<>("{\n" +
                "    \"CANO\" : \"" + redisMemberRequest.get("CANO") + "\",\n" +
                "    \"ACNT_PRDT_CD\" : \"" + redisMemberRequest.get("ACNT_PRDT_CD") + "\",\n" +
                "    \"PDNO\" : \"" + stockCode + "\",\n" +
                "    \"ORD_DVSN\" : \"" + redisMemberRequest.get("ORD_DVSN") + "\",\n" +
                "    \"ORD_QTY\" : \"" + redisMemberRequest.get("ORD_QTY") + "\",\n" +
                "    \"ORD_UNPR\" : \"" + redisMemberRequest.get("ORD_UNPR") + "\"\n" +
                "}", headers);

        ;
        // POST 요청 보내기
        ResponseEntity<String> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);
        System.out.println("주문 완료!" + responseEntity.getBody());
        Map responseMessage = objectMapper.readValue(responseEntity.getBody(), Map.class);
        LinkedHashMap<String, String> output = (LinkedHashMap<String, String>) responseMessage.get("output");
        if(output != null){
            String orderNumber = output.get("ODNO");
            String formattedOrderNumber = String.format("%010d", Long.parseLong(orderNumber));
            Integer memberId = (Integer) redisMemberRequest.get("memberId");
            memberOrderStockService.createMemberOrderNumber(memberId.longValue(), (String)redisMemberRequest.get("memberOrderStockNickname"), (String) redisMemberRequest.get("buySell"), formattedOrderNumber);
        }


    }

//                    requestBuyOrder();


//                if(Long.valueOf(sangbo.get(i)) < Long.valueOf(stockInfo[2])){
////                    requestSellOrder();
//                }


    private void requestSellOrder(LinkedHashMap<String, Object> redisMemberRequest, String stockCode) {
        ObjectMapper objectMapper = new ObjectMapper();
        String apiUrl = "https://openapivts.koreainvestment.com:29443/uapi/domestic-stock/v1/trading/order-cash?&&&";

        // 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + redisMemberRequest.get("authorization"));
        headers.set("appkey", (String) redisMemberRequest.get("appKey"));
        headers.set("appsecret", (String) redisMemberRequest.get("appsecret"));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("tr_id", "VTTC0801U");
        // HTTP 요청 설정
        HttpEntity<String> requestEntity = new HttpEntity<>("{\n" +
                "    \"CANO\" : \"" + redisMemberRequest.get("cano") + "\",\n" +
                "    \"ACNT_PRDT_CD\" : \"" + redisMemberRequest.get("acnt_PRDT_CD") + "\",\n" +
                "    \"PDNO\" : \"" + stockCode + "\",\n" +
                "    \"ORD_DVSN\" : \"" + redisMemberRequest.get("mktType") + "\",\n" +
                "    \"ORD_QTY\" : \"" + redisMemberRequest.get("ord_QTY") + "\",\n" +
                "    \"ORD_UNPR\" : \"" + redisMemberRequest.get("ord_UNPR") + "\"\n" +
                "}", headers);

        ;
        // POST 요청 보내기
        ResponseEntity<String> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);

    }

    private void requestBuyOrder() {
        ObjectMapper objectMapper = new ObjectMapper();
        String apiUrl = "https://openapivts.koreainvestment.com:29443/uapi/domestic-stock/v1/trading/order-cash?&&&";

        // 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0b2tlbiIsImF1ZCI6IjM3ZTQ0NTQ0LWI1OWEtNDZjNy1hNGExLWZjYWUzYjhlOGRkZCIsImlzcyI6InVub2d3IiwiZXhwIjoxNjk4OTc0NTk0LCJpYXQiOjE2OTg4ODgxOTQsImp0aSI6IlBTYnYxVWsyM0FsZGhCM0pwMTd1VUFZbkVEMll4Sm5NQnFZVSJ9.2-jCWdS-JdkA3v9H8O2W8_5narLvwRNMZ7VWqkJGVnx-fMjf8sKGj8-8bDyW29RTlBzAUyPwLreHb7WN6du7fQ");
        headers.set("appkey", "PSbv1Uk23AldhB3Jp17uUAYnED2YxJnMBqYU");
        headers.set("appsecret", "mWmiDY/fX7r0ppQoAVojeWPjlsWqDIagfIqwJEN8B68gn1xLokn70QMMLW19KLPnXqdOYPuaoV39mHwtn2oUIPF2MkwihiESq5D31hlbDg+rkcc7UJXY1x7O5qFOMJp5oMhfdKk4OrwvdTtyBjfFNGfgV0NVauG2U6j9JnmyilhOTIDoBCw=");
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("tr_id", "VTTC0802U");

        // HTTP 요청 설정
        HttpEntity<String> requestEntity = new HttpEntity<>("{\n" +
                "    \"CANO\" : \"50098544\",\n" +
                "    \"ACNT_PRDT_CD\" : \"01\",\n" +
                "    \"PDNO\" : \"005930\",\n" +
                "    \"ORD_DVSN\" : \"00\",\n" +
                "    \"ORD_QTY\" : \"1\",\n" +
                "    \"ORD_UNPR\" : \"69400\"\n" +
                "}", headers);

        // POST 요청 보내기
        ResponseEntity<String> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);
    }

    public WebSocketSession getSession() {

        return session;
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // 연결이 종료될 때 Heartbeat 타이머를 종료합니다.
        heartbeatTimer.cancel();

        // 연결이 끊겼을 때 다시 연결하는 로직을 추가합니다.
        reconnect();

        super.afterConnectionClosed(session, status);
    }

    private WebSocketConnectionManager connectionManager;
    private void reconnect() {
        if (connectionManager == null || !connectionManager.isConnected()) {
            WebSocketClient webSocketClient = new StandardWebSocketClient();
            WebSocketConnectionManager newConnectionManager = new WebSocketConnectionManager(webSocketClient, new StockSocketHandler(approvalKey, check), "ws://ops.koreainvestment.com:31000");
            newConnectionManager.setAutoStartup(true);
            newConnectionManager.start();

            connectionManager = newConnectionManager;
        }
    }

    public static String aesCbcBase64Dec(String key, String iv, String cipherText) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes("UTF-8"));
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);

            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(cipherText));
            return new String(decryptedBytes, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}