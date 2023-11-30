package com.ssafy.alphano.util.springbatch;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.alphano.api.stock.service.StockDailyService;
import com.ssafy.alphano.api.stock.service.query.StockQueryService;
import com.ssafy.alphano.api.stock.service.request.CreateStockDailyServiceRequest;
import com.ssafy.alphano.api.stock.service.response.StockServiceResponse;
import com.ssafy.alphano.api.stockdate.service.StockDateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@EnableScheduling
@Component
@RequiredArgsConstructor
public class StockDailyUpdateBatchScheduler {

    private final StockQueryService stockQueryService;

    private final RestTemplate restTemplate;

    private final StockDailyService stockDailyService;

    private final StockDateService stockDateService;



//    @Scheduled(cron = "*/10 * * * * *") // define schedule as needed
    public void get() throws Exception {
           sendGetRequest();
    }

    public String sendGetRequest() throws JsonProcessingException {
        String apiUrl = "http://data-dbg.krx.co.kr/svc/apis/sto/stk_bydd_trd";

        // 어제 날짜 계산
        LocalDate yesterday = LocalDate.now().minusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String basDd = yesterday.format(formatter);


        System.out.println("날짜 = " + basDd);
        if(!stockDateService.checkMktOpen(basDd)){
            System.out.println("개장일이 아닙니다");
            return null;
        }

        // 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("AUTH_KEY", "480B11A55C964C5EBADE4FED5DE2B894BF46FA17 ");
        headers.setContentType(MediaType.APPLICATION_JSON);

        // HTTP 요청 설정
        HttpEntity<String> requestEntity = new HttpEntity<>("{\"basDd\":\"" + basDd + "\"}", headers);

        // POST 요청 보내기
        ResponseEntity<String> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);

        String body = responseEntity.getBody();

        ObjectMapper objectMapper = new ObjectMapper();


        Map<String, List<Map<String, String>>> stockInfoMap = objectMapper.readValue(body, Map.class);
        List<Map<String, String>> stockInfoList = stockInfoMap.get("OutBlock_1");
        List<StockServiceResponse> allStock = stockQueryService.findAllStock();
        for(Map<String, String> stock : stockInfoList){
            for(StockServiceResponse stockNameServiceResponse : allStock){
                if(stock.get("ISU_NM").equals(stockNameServiceResponse.getStockName())){

                    CreateStockDailyServiceRequest stockDailyData = createStockDailyData(stock);
                    stockDailyService.saveStockDaily(stockDailyData, basDd);

                }
            }
        }
        return body;
    }

    private CreateStockDailyServiceRequest createStockDailyData(Map<String, String> stock) {

        String stockName = stock.get("ISU_NM"); // 종목명
        String createDate = stock.get("BAS_DD"); // 날짜
        int startPrice = Integer.valueOf(stock.get("TDD_OPNPRC")); // 시가
        int endPrice = Integer.valueOf(stock.get("TDD_CLSPRC")); // 종가
        int maxPrice = Integer.valueOf(stock.get("TDD_HGPRC")); // 고가
        int minPrice = Integer.valueOf(stock.get("TDD_LWPRC")); // 저가

        return CreateStockDailyServiceRequest.builder()
                .stockName(stockName)
                .createDate(createDate)
                .startPrice(startPrice)
                .endPrice(endPrice)
                .maxPrice(maxPrice)
                .minPrice(minPrice)
                .build();
    }


}
