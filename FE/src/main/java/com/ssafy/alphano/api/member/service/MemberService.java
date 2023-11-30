package com.ssafy.alphano.api.member.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.alphano.api.member.service.request.MemberLikeStockServiceRequest;
import com.ssafy.alphano.api.member.service.request.MemberRecentStockServiceRequest;
import com.ssafy.alphano.api.member.service.request.MemberStockServiceRequest;
import com.ssafy.alphano.api.member.service.response.MemberResponse;
import com.ssafy.alphano.common.error.ErrorCode;
import com.ssafy.alphano.common.error.exception.EntityNotFoundException;
import com.ssafy.alphano.common.response.ApiData;
import com.ssafy.alphano.common.response.ApiError;
import com.ssafy.alphano.common.response.ApiResult;
import com.ssafy.alphano.domain.member.entity.Member;
import com.ssafy.alphano.domain.member.entity.middle.MemberLikeStock;
import com.ssafy.alphano.domain.member.entity.middle.MemberRecentStock;
import com.ssafy.alphano.domain.member.entity.middle.MemberStock;
import com.ssafy.alphano.domain.member.repository.MemberLikeStockRepository;
import com.ssafy.alphano.domain.member.repository.MemberRecentStockRepository;
import com.ssafy.alphano.domain.member.repository.MemberRepository;
import com.ssafy.alphano.domain.member.repository.MemberStockRepository;
import com.ssafy.alphano.domain.member.repository.query.MemberQueryRepository;
import com.ssafy.alphano.domain.stock.entity.Stock;
import com.ssafy.alphano.domain.stock.repository.StockRepository;
import com.ssafy.alphano.util.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final TokenProvider tokenProvider;

    private final StockRepository stockRepository;

    private final MemberRepository memberRepository;

    private final MemberLikeStockRepository memberLikeStockRepository;

    private final MemberRecentStockRepository memberRecentStockRepository;

    private final MemberStockRepository memberStockRepository;

    private final RestTemplate restTemplate;

    private final MemberQueryRepository memberQueryRepository;

    public List<MemberResponse> findAllMember() {
        return memberQueryRepository.findAllMember();
    }

    public ApiResult createLikeStock(String authorization, MemberLikeStockServiceRequest request) {

        if(!checkJwtToken(authorization)){
            return ApiError.of(ErrorCode.INVALID_TOKEN);
        }

        String jwtToken = authorization.substring(7);

        Long memberId = Long.valueOf(tokenProvider.getUsernameFromJwt(jwtToken));

        Member member = memberRepository.findById(memberId).orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));

        Stock stock = stockRepository.findByStockName(request.getStockName()).orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));

        if(memberLikeStockRepository.findByMemberIdAndStockId(member.getId(), stock.getId()) != null){
            disLikeStock(member, stock);
        }else{
            likeStock(member, stock);
        }
        return ApiData.of("success");
    }

    private void likeStock(Member member, Stock stock) {
        MemberLikeStock memberLikeStock = MemberLikeStock.builder()
                .member(member)
                .stock(stock)
                .build();

        memberLikeStockRepository.save(memberLikeStock);
    }

    private void disLikeStock(Member member, Stock stock) {
        memberLikeStockRepository.delete(memberLikeStockRepository.findByMemberIdAndStockId(member.getId(), stock.getId()));
    }

    private boolean checkJwtToken(String authorization) {
        if (authorization == null || !authorization.startsWith("Bearer")) {
            return false;
        }

        String jwtToken = authorization.substring(7);

        if (!tokenProvider.validateToken(jwtToken)) {
            return false;
        }

        return true;
    }

    public ApiResult createRecentStock(Long memberId, MemberRecentStockServiceRequest request) {

        Member member = memberRepository.findById(memberId).orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));

        Stock stock = stockRepository.findByStockName(request.getStockName()).orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));

        MemberRecentStock memberRecentStock = memberRecentStockRepository.findByMemberIdAndStockId(member.getId(), stock.getId());


        if(memberRecentStock != null) {
            deleteRecentStock(member, stock);
        }

        addRecentStock(member, stock);

        return ApiData.of("등록되었습니다.");
    }

    public ApiResult deleteRecentStock(Long memberId, MemberRecentStockServiceRequest request) {

        Member member = memberRepository.findById(memberId).orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));

        Stock stock = stockRepository.findByStockName(request.getStockName()).orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));

        if(memberRecentStockRepository.findByMemberIdAndStockId(member.getId(), stock.getId()) == null) {
            return ApiData.of("최근 종목 리스트에 없습니다.");
        }
        deleteRecentStock(member, stock);
        return ApiData.of("삭제되었습니다.");
    }

    private void addRecentStock(Member member, Stock stock) {
        MemberRecentStock memberRecentStock = MemberRecentStock.builder()
                .member(member)
                .stock(stock)
                .build();

        memberRecentStockRepository.save(memberRecentStock);
    }

    private void deleteRecentStock(Member member, Stock stock) {
        memberRecentStockRepository.delete(memberRecentStockRepository.findByMemberIdAndStockId(member.getId(), stock.getId()));
    }

    public ApiResult createMemberStock(String authorization, MemberStockServiceRequest request) {
        if(!checkJwtToken(authorization)){
            return ApiError.of(ErrorCode.INVALID_TOKEN);
        }

        String jwtToken = authorization.substring(7);

        Long memberId = Long.valueOf(tokenProvider.getUsernameFromJwt(jwtToken));

        Member member = memberRepository.findById(memberId).orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));

        Stock stock = stockRepository.findByStockName(request.getStockName()).orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));

        int purchasePrice = request.getPurchasePrice();
        
        int purchaseCount = request.getPurchaseCount();

        LocalDateTime purchaseTime = request.getPurchaseTime();

        if(memberStockRepository.findByMemberIdAndStockId(member.getId(), stock.getId()) != null){
            return ApiData.of("보유한 종목이 있습니다.");
        } else {
            memberStock(member, stock, purchasePrice, purchaseCount, purchaseTime);
        }
        return ApiData.of("success");
    }

    private void memberStock(Member member, Stock stock, int purchasePrice, int purchaseCount, LocalDateTime purchaseTime) {
        MemberStock memberStock = MemberStock.builder()
                .member(member)
                .stock(stock)
                .purchasePrice(purchasePrice)
                .purchaseCount(purchaseCount)
                .purchaseTime(purchaseTime)
                .build();

        memberStockRepository.save(memberStock);
    }

    public void updateAllAccessToken() throws JsonProcessingException, InterruptedException {
        List<Member> memberList = memberRepository.findAll();

        for(Member member : memberList){
            String accessToken = getMemberAccessToken(member.getAppKey(), member.getAppsecretKey());
            String approvalKey = requestMemberApprovalKey(member.getAppKey(), member.getAppsecretKey());
            System.out.println(approvalKey);

            member.updateAccessToken(accessToken);
            member.updateApprovalKey(approvalKey);

            memberRepository.save(member);

            Thread.sleep(1);
        }
    }

    private String getMemberAccessToken(String appKey, String appsecretKey) throws JsonProcessingException {

        String url = "https://openapivts.koreainvestment.com:29443/oauth2/tokenP";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String jsonBody = "{" +
                "\"grant_type\": \"client_credentials\"," +
                "\"appkey\": \"" + appKey +"\"," +
                "\"appsecret\": \""+appsecretKey+"\"" +
                "}";

        HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        String responseBody = responseEntity.getBody();

        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, String> parsedResponse = (Map<String, String>) objectMapper.readValue(responseBody, Map.class);

        return parsedResponse.get("access_token");

    }

    private String requestMemberApprovalKey(String appKey, String appsecretKey) throws JsonProcessingException {

        String url = "https://openapivts.koreainvestment.com:29443/oauth2/Approval";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String jsonBody = "{" +
                "\"grant_type\": \"client_credentials\"," +
                "\"appkey\": \"" + appKey +"\"," +
                "\"secretkey\": \""+appsecretKey+"\"" +
                "}";

        HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        String responseBody = responseEntity.getBody();

        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, String> parsedResponse = (Map<String, String>) objectMapper.readValue(responseBody, Map.class);

        return parsedResponse.get("approval_key");

    }

    public void updateMemberAccessToken(Long memberId) {

        Member member = memberRepository.findById(memberId).orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));

        try{
            String accessToken = getMemberAccessToken(member.getAppKey(), member.getAppsecretKey());

            member.updateAccessToken(accessToken);

            memberRepository.save(member);
        } catch (Exception e) {
            return;
        }


    }

}
