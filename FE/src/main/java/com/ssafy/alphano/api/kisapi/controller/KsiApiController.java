package com.ssafy.alphano.api.kisapi.controller;

import com.ssafy.alphano.api.kisapi.KisapiAskingPriceService;
import com.ssafy.alphano.api.kisapi.KisapiBalanceService;
import com.ssafy.alphano.api.kisapi.request.AskingPriceServiceRequest;
import com.ssafy.alphano.api.member.service.AuthService;
import com.ssafy.alphano.api.member.service.query.MemberQueryService;
import com.ssafy.alphano.api.member.service.response.MemberPrivateInfoResponse;
import com.ssafy.alphano.common.response.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/rest-api")
@RequiredArgsConstructor
public class KsiApiController {

    private final AuthService authService;

    private final MemberQueryService memberQueryService;

    private final KisapiAskingPriceService kisapiAskingPriceService;

    private final KisapiBalanceService kisapiBalanceService;

    @PostMapping("/asking-price")
    @Operation(summary = "주식실시간 호가 조회" ,description = "주식실시간 호가 조회")
    public ApiResult getAskingPrice(HttpServletRequest servletRequest, @RequestBody AskingPriceServiceRequest request) {
        String authorization = servletRequest.getHeader("Authorization");

        Long memberID = authService.findMemberIdByToken(authorization);

        MemberPrivateInfoResponse member = memberQueryService.findMemberPrivateInfo(memberID);

        String accessToken = member.getAuthorization();

        String appkey = member.getAppkey();

        String appsecret = member.getAppsecret();

        return kisapiAskingPriceService.findAskingPrice(accessToken, appkey, appsecret, request);

    }

    @GetMapping("/balance")
    @Operation(summary = "주식 잔고 조회" ,description = "주식 잔고 조회")
    public ApiResult getBalance(HttpServletRequest servletRequest) {
        String authorization = servletRequest.getHeader("Authorization");

        Long memberID = authService.findMemberIdByToken(authorization);

        MemberPrivateInfoResponse member = memberQueryService.findMemberPrivateInfo(memberID);

        String accessToken = member.getAuthorization();

        String appkey = member.getAppkey();

        String appsecret = member.getAppsecret();

        String cano = member.getCANO();

        String acntPrdtCd = member.getACNT_PRDT_CD();

        return kisapiBalanceService.findBalance(accessToken, appkey, appsecret, cano, acntPrdtCd);

    }

}
