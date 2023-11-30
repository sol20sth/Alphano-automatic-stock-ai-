package com.ssafy.alphano.util.springbatch;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.alphano.api.stock.service.StockDailyService;
import com.ssafy.alphano.api.stock.service.query.StockDailyQueryService;
import com.ssafy.alphano.api.stock.service.query.StockQueryService;
import com.ssafy.alphano.api.stock.service.response.StockDailyResponse;
import com.ssafy.alphano.api.stock.service.response.StockServiceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@EnableScheduling
@Component
@RequiredArgsConstructor
public class UpdateStockIndicatorScheduler {

    private final StockDailyQueryService stockDailyQueryService;

    private final StockDailyService stockDailyService;

    private final StockQueryService stockQueryService;

    private final RestTemplate restTemplate;

    /**
     *
     *
     * @return
     * 어제 날짜의 rsi, macd 업데이트
     * @throws JsonProcessingException
     */

//    @Scheduled(cron = "*/20 * * * * *")
    public void updateOption() throws JsonProcessingException {
        List<StockServiceResponse> stockList = stockQueryService.findAllStock();
        for(StockServiceResponse stock : stockList){
            String rsi = getOption(stock.getStockCode(), "rsi");
            String macd = getOption(stock.getStockCode(), "macd");

            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Double> rsiMap = objectMapper.readValue(rsi, Map.class);
            Map<String, Double> macdMap = objectMapper.readValue(macd, Map.class);

            LocalDate yesterday = LocalDate.now().minusDays(1);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            String stockDate = yesterday.format(formatter);

            stockDailyService.updateOption(stock.getStockCode(), rsiMap.get("rsi"), macdMap.get("macd"), stockDate);

        }

    }

    private List<Integer> getPrice(String stockCode, int days) {
        List<StockDailyResponse> stockDailyForOption = stockDailyQueryService.findStockDailyForOption(stockCode, days);
        Collections.sort(stockDailyForOption, Comparator.comparing(StockDailyResponse::getStockDate));
        List<Integer> requestPrice = new ArrayList<>();
        for (StockDailyResponse stockDailyResponse : stockDailyForOption) {
            requestPrice.add(stockDailyResponse.getEndPrice());
        }

        return requestPrice;
    }



    private String getOption(String stockCode, String optionName) throws JsonProcessingException {
        List<Integer> priceList = new ArrayList<>();
        if(optionName.equals("rsi")){
            priceList = getPrice(stockCode, 14);
        }else if(optionName.equals("macd")){
            priceList = getPrice(stockCode, 30);
        }
        String apiUrl = "http://localhost:8000/api/v1/ai/get-"+optionName;

        // 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(priceList);


        // HTTP 요청 설정
        HttpEntity<String> requestEntity = new HttpEntity<>(json, headers);

        // POST 요청 보내기
        ResponseEntity<String> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);

        String body = responseEntity.getBody();

        return body;

    }
}
