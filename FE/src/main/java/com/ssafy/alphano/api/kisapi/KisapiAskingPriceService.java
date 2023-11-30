package com.ssafy.alphano.api.kisapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.alphano.api.kisapi.request.AskingPriceServiceRequest;
import com.ssafy.alphano.common.response.ApiData;
import com.ssafy.alphano.common.response.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class KisapiAskingPriceService {

    private final RestTemplate restTemplate;

    public ApiResult findAskingPrice(String accessToken, String appkey, String appsecret, AskingPriceServiceRequest request) {
        ObjectMapper objectMapper = new ObjectMapper();
        String apiUrl = "https://openapivts.koreainvestment.com:29443/uapi/domestic-stock/v1/quotations/inquire-asking-price-exp-ccn";
        String stockCode = request.getStockCode();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("FID_COND_MRKT_DIV_CODE", "J")
                .queryParam("FID_INPUT_ISCD", stockCode);

        String queryUrl = builder.toUriString();

        // 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("authorization", "Bearer " + accessToken);
        headers.set("appkey", appkey);
        headers.set("appsecret", appsecret);
        headers.set("tr_id", "FHKST01010200 "); // P: 개인, B: 법인
        headers.setContentType(MediaType.APPLICATION_JSON);

        // HTTP 요청 설정
        HttpEntity<String> requestEntity = new HttpEntity<>("{\n" +
                "    \"input\" : {\n" +
                "        \"tr_id\" : \"H0STASP0\",\n" +
                "        \"tr_key\" : \"" + stockCode + "\"\n" +
                "    }\n" +
                "}", headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(queryUrl, HttpMethod.GET, requestEntity, String.class);

        return ApiData.of(responseEntity);
    }
}
