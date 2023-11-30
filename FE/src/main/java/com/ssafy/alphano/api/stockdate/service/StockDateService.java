package com.ssafy.alphano.api.stockdate.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.alphano.common.response.ApiData;
import com.ssafy.alphano.common.response.ApiResult;
import com.ssafy.alphano.domain.stock.entity.StockDate;
import com.ssafy.alphano.domain.stock.repository.StockDateRepository;
import com.ssafy.alphano.domain.stock.repository.query.StockDateQueryRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.List;
import java.util.Map;
import java.util.Properties;

@Service
@RequiredArgsConstructor
public class StockDateService {

    private final StockDateRepository stockDateRepository;

    private final StockDateQueryRepository stockDateQueryRepository;

    public boolean checkMktOpen(String requestDate) {
        return stockDateQueryRepository.checkMktOpen(requestDate);
    }

    public ApiResult saveStockDate() throws JsonProcessingException {

        String baseURL = "https://openapi.koreainvestment.com:9443/uapi/domestic-stock/v1/quotations/chk-holiday";

        Properties prop = new Properties();
        String appKey = "";
        String appSecretKey = "";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("appkey", appKey);
        headers.set("appsecret", appSecretKey);

        headers.set("authorization", "");
        headers.set("tr_id", "CTCA0903R");
        headers.set("custtype", "P");

        int currentYear = LocalDate.now().getYear();

        LocalDate newYearDay = LocalDate.of(currentYear, 1, 1);

        LocalDate endYearDay = LocalDate.of(currentYear, 12, 31);

        String formmatedNewDate = newYearDay.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        String formmatedEndDate = endYearDay.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        Integer BaseDate = Integer.parseInt(formmatedNewDate);

        Integer EndDate = Integer.parseInt(formmatedEndDate);

        System.out.println("지금 날짜요" + BaseDate);

        String CTX_AREA_NK = "";
        String CTX_AREA_FK = "";

        while (BaseDate < EndDate) {
            URI uri = UriComponentsBuilder.fromHttpUrl(baseURL)
                    .queryParam("BASS_DT", BaseDate)
                    .queryParam("CTX_AREA_NK", CTX_AREA_NK)
                    .queryParam("CTX_AREA_FK", CTX_AREA_FK)
                    .build()
                    .toUri();

            HttpEntity<String> entity = new HttpEntity<>(headers);

            RestTemplate restTemplate = new RestTemplate();

            ResponseEntity<String> responseEntity = restTemplate.exchange(uri, HttpMethod.POST, entity, String.class);

            String responseBody = responseEntity.getBody();

            ObjectMapper objectMapper = new ObjectMapper();

            Map<String, Object> parsedResponse = objectMapper.readValue(responseBody, new TypeReference<Map<String, Object>>() {
            });

            List<Map<String, Object>> outputLists = (List<Map<String, Object>>) parsedResponse.get("output");

            for (Map<String, Object> entry : outputLists) {
                String bassDtStr = (String) entry.get("bass_dt");
                String openYnStr = (String) entry.get("opnd_yn");

                boolean isOpen = "Y".equals(openYnStr);

                StockDate stockDate = new StockDate();
                stockDate.setId(bassDtStr);
                stockDate.setMktOpen(isOpen);

                stockDateRepository.save(stockDate);

            }

            String baseDateString = BaseDate.toString();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            LocalDate localDate = LocalDate.parse(baseDateString, formatter);

            LocalDate newDate = localDate.plusDays(24);
            String formattedNewDate = newDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            BaseDate = Integer.parseInt(formattedNewDate);

        }

        return ApiData.of("success");
    }


    private String getMemberAccessToken (String appKey, String appsecretKey) throws JsonProcessingException {

        String url = "https://openapivts.koreainvestment.com:29443/oauth2/tokenP";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String jsonBody = "{" +
                "\"grant_type\": \"client_credentials\"," +
                "\"appkey\": \"" + appKey + "\"," +
                "\"appsecret\": \"" + appsecretKey + "\"" +
                "}";

        HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        String responseBody = responseEntity.getBody();

        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, String> parsedResponse = (Map<String, String>) objectMapper.readValue(responseBody, Map.class);

        return parsedResponse.get("access_token");

    }
}
