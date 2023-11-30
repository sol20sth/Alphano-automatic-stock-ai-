package com.ssafy.alphano.api.ai.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.alphano.api.ai.service.response.StockAIReliabilityResponse;
import com.ssafy.alphano.api.stock.service.response.StockServiceResponse;
import com.ssafy.alphano.domain.stock.repository.query.StockDailyQueryRepository;
import com.ssafy.alphano.domain.stock.repository.query.StockQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AIService {

    private final RestTemplate restTemplate;

    private final StockDailyQueryRepository stockDailyQueryRepository;

    private final StockQueryRepository stockQueryRepository;

    @Transactional
    public String sendStockCodeToAI(String stockCode) throws JsonProcessingException {


        List<StockServiceResponse> allStock = stockQueryRepository.findAllStock();
        for (StockServiceResponse stock : allStock) {
            Map<String, Object> aiData = connectAIServer(stock.getStockCode());

            Map<String, Object> AiData = (Map<String, Object>) aiData.get("data");

            Map<String, String> stockData = (Map<String, String>) (AiData.get(stock.getStockCode()));

            int maxPrice = Integer.valueOf(stockData.get("max_price"));

            int minPrice = Integer.valueOf(stockData.get("min_price"));

            int finalPrice = Integer.valueOf(stockData.get("final_price"));

            // 어제 날짜 구하기
            LocalDate yesterday = LocalDate.now().minusDays(1);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

            String stockDate = yesterday.format(formatter);

            // 어제 날짜의 AI 데이터 업데이트
            stockDailyQueryRepository.updateAiData(stock.getId(), maxPrice, minPrice, finalPrice, stockDate);

        }

        return "success";
    }

    private Map<String, Object> connectAIServer(String stockCode) throws JsonProcessingException {
        String url = "http://localhost:8000/api/v1/ai/stock-code";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String jsonBody = "[\n" +
                "\"" + stockCode + "\"" +
                "]";

        HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        String responseBody = responseEntity.getBody();

        ObjectMapper objectMapper = new ObjectMapper();

        return (Map<String, Object>) objectMapper.readValue(responseBody, Map.class);


    }

    @Transactional
    public void updateAIReliability() {

        // 1. order by stockDate desc 로 얻은 값 중 2번째 값 * (count - 1) 구하기
        // 그저께 날짜 데이터 구하기

        List<StockServiceResponse> allStock = stockQueryRepository.findAllStock();
        for (StockServiceResponse stockServiceResponse : allStock) {

            LocalDate dayBeforeYesterday = LocalDate.now().minusDays(2);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

            String stockDate = dayBeforeYesterday.format(formatter);

            StockAIReliabilityResponse aiReliability = stockDailyQueryRepository.findDayBeforeYesterdayAIReliability(stockDate, stockServiceResponse.getId());

            int stockDailyCount = stockDailyQueryRepository.findStockDailyCountByStockId(stockServiceResponse.getId());

            dayBeforeYesterday = LocalDate.now().minusDays(1);

            stockDate = dayBeforeYesterday.format(formatter);

            StockAIReliabilityResponse yesterdayAIReliability = stockDailyQueryRepository.findYesterdayAIReliability(stockDate, stockServiceResponse.getId());
            System.out.println(yesterdayAIReliability.getMinPrice());
            System.out.println(yesterdayAIReliability.getMaxPrice());
            System.out.println(yesterdayAIReliability.getEndPrice());
            System.out.println(yesterdayAIReliability.getAiEndPrice());
            System.out.println(yesterdayAIReliability.getAiMinPrice());
            System.out.println(yesterdayAIReliability.getAiMaxPrice());

            ;
            // 2. 어제 날짜의 신뢰도 구하기
            float aiEndReliability = ((aiReliability.getAiEndReliability() * (stockDailyCount - 1)) + yesterdayAIReliability.getAiEndReliability()) / stockDailyCount;
            float aiMinReliability = ((aiReliability.getAiMinReliability() * (stockDailyCount - 1)) + yesterdayAIReliability.getAiMinReliability()) / stockDailyCount;
            float aiMaxReliability = ((aiReliability.getAiMaxReliability() * (stockDailyCount - 1))+ yesterdayAIReliability.getAiMaxReliability()) / stockDailyCount;
            float aiFinalReliability = (aiEndReliability + aiMinReliability + aiMaxReliability) / 3;
            // 3. 어제 날짜의 신뢰도 업데이트

            stockDailyQueryRepository.updateAiReliability(stockServiceResponse.getId(), aiEndReliability, aiMinReliability, aiMaxReliability, aiFinalReliability, stockDate);
        }

    }

}
