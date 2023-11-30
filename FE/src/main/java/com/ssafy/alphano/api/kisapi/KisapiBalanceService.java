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
public class KisapiBalanceService {

    private final RestTemplate restTemplate;

    public ApiResult findBalance(String accessToken, String appkey, String appsecret, String cano, String acntPrdtCd) {
        ObjectMapper objectMapper = new ObjectMapper();
        String apiUrl = "https://openapivts.koreainvestment.com:29443/uapi/domestic-stock/v1/trading/inquire-balance";
        System.out.println("cano" + cano);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("CANO", cano)
                .queryParam("ACNT_PRDT_CD", acntPrdtCd)
                .queryParam("AFHR_FLPR_YN", "N")
                .queryParam("OFL_YN", "")
                .queryParam("INQR_DVSN", "01")
                .queryParam("UNPR_DVSN", "01")
                .queryParam("FUND_STTL_ICLD_YN", "N")
                .queryParam("FNCG_AMT_AUTO_RDPT_YN", "N")
                .queryParam("PRCS_DVSN", "01")
                .queryParam("CTX_AREA_FK100", "")
                .queryParam("CTX_AREA_NK100", "");

        String queryUrl = builder.toUriString();

        // 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("authorization", "Bearer " + accessToken);
        headers.set("appkey", appkey);
        headers.set("appsecret", appsecret);
        headers.set("tr_id", "VTTC8434R"); // P: 개인, B: 법인
        headers.setContentType(MediaType.APPLICATION_JSON);

        // HTTP 요청 설정
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(queryUrl, HttpMethod.GET, requestEntity, String.class);

        return ApiData.of(responseEntity);
    }
}
