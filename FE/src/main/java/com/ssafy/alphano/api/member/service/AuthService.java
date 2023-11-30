package com.ssafy.alphano.api.member.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.alphano.api.member.service.request.MemberCreateServiceRequest;
import com.ssafy.alphano.api.member.service.request.MemberLoginServiceRequest;
import com.ssafy.alphano.api.member.service.response.ApprovalKeyResponse;
import com.ssafy.alphano.common.error.ErrorCode;
import com.ssafy.alphano.common.error.exception.EntityNotFoundException;
import com.ssafy.alphano.domain.member.entity.Member;
import com.ssafy.alphano.domain.member.repository.MemberRepository;
import com.ssafy.alphano.domain.member.repository.query.MemberQueryRepository;
import com.ssafy.alphano.util.TokenProvider;
import com.ssafy.alphano.util.dto.TokenDto;
import com.ssafy.alphano.util.dto.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {


    private final MemberRepository memberRepository;

    private final MemberQueryRepository memberQueryRepository;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final TokenProvider tokenProvider;

    private final RefreshTokenRepository refreshTokenRepository;

    private final PasswordEncoder passwordEncoder;

    private final MemberService memberService;

    private Authentication authentication;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public boolean createMember(MemberCreateServiceRequest request) {

        request.encodePassword(passwordEncoder);

        if (memberRepository.existsByUsername(request.getUsername())) {
            return false;
        }
        memberRepository.save(request.toEntity());

        return true;
    }

    @Transactional
    public TokenDto login(MemberLoginServiceRequest request) {
        UsernamePasswordAuthenticationToken authenticationToken = request.toAuthentication();

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        Member member = memberRepository.findByUsername(request.getUsername()).orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));


        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        tokenDto.addMemberId(member.getId());

        memberService.updateMemberAccessToken(member.getId());


        // 5. 토큰 발급
        return tokenDto;

    }

    public boolean validateToken(String authorization) {
        if (authorization == null || !authorization.startsWith("Bearer")) {
            return false;
        }

        String jwtToken = authorization.substring(7);

        if (!tokenProvider.validateToken(jwtToken)) {
            return false;
        }

        return true;
    }

    public Long findMemberIdByToken(String authorization) {
        String jwtToken = authorization.substring(7);
        return Long.valueOf(tokenProvider.getUsernameFromJwt(jwtToken));
    }


    private boolean checkRequestApprovalKey(String username) {
        // 1. 해당 유저의  approval key 정보 확인
        ApprovalKeyResponse approvalKeyResponse = memberQueryRepository.findMemberApprovalKey(username);

        // 2. 해당 유저가 가지고 있는 approval key가 없을 경우
        if (approvalKeyResponse == null) {
            return true;
        }
        // 현재 시간 가져오기
        LocalDateTime currentTime = LocalDateTime.now();

        // approvalKeyResponse에 담겨있는 updatedTime 가져오기
        LocalDateTime updatedTime = approvalKeyResponse.getUpdatedTime();

        // 날짜가 다른지 확인
        boolean datesAreDifferent = !currentTime.toLocalDate().isEqual(updatedTime.toLocalDate());

        if (datesAreDifferent) {
            // 처리할 로직 추가
            System.out.println("날짜가 다릅니다.");
            return true;
        } else {
            // 처리할 로직 추가
            System.out.println("날짜가 같습니다.");
            return false;
        }
    }

    private void sendRequest(String appKey, String appsecretKey) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://openapi.koreainvestment.com:9443/oauth2/Approval";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("grant_type", "client_credentials");
        requestBody.put("appkey", appKey);
        requestBody.put("secretkey", appsecretKey);

        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);


        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.POST, // Change to the appropriate HTTP method (POST, GET, etc.)
                requestEntity,
                String.class  // Change to the expected response type
        );

        String responseBody = responseEntity.getBody();

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> responseMap = objectMapper.readValue(responseBody, Map.class);

        String approvalKey = (String) responseMap.get("approval_key");

        // Do something with the value
        redisTemplate.opsForValue().set("junghyun", approvalKey); // 이정현이라는 키로 approvalKey를 저장 (이후에는 이정현이라는 키로 approvalKey를 가져올 수 있음)
        System.out.println("Approval Key: " + approvalKey);
    }


}
