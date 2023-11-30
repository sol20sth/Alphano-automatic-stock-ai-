package com.ssafy.alphano.api.member.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssafy.alphano.api.member.controller.request.MemberCreateRequest;
import com.ssafy.alphano.api.member.controller.request.MemberLoginRequest;
import com.ssafy.alphano.api.member.service.AuthService;
import com.ssafy.alphano.api.member.service.MemberService;
import com.ssafy.alphano.common.error.ErrorCode;
import com.ssafy.alphano.common.response.ApiData;
import com.ssafy.alphano.common.response.ApiError;
import com.ssafy.alphano.common.response.ApiResult;
import com.ssafy.alphano.util.dto.TokenDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    private final MemberService memberService;


    @PostMapping("/sign-up")
    @Operation(summary = "회원가입", description = "회원가입을 한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "ID중복 or 비밀번호 조건 불만족"),
            @ApiResponse(responseCode = "200", description = "회원가입 성공", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiData.class, example =  "{\n" +
                    "    \"status\": 200,\n" +
                    "    \"code\": \"OK\",\n" +
                    "    \"data\": \"회원가입이 성공하였습니다\"\n" +
                    "}"))})})
    public ApiResult signUp(@RequestBody MemberCreateRequest request) throws JsonProcessingException {
        if(!authService.createMember(request.toServiceRequest())){
            return ApiError.of(ErrorCode.DUPLICATE_USER_NAME);
        }

        return ApiData.of("회원가입이 성공하였습니다");

    }

    // 로그인
    @PostMapping("/login")
    @Operation(summary = "로그인", description = "아이디와 비밀번호로 로그인.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "ID중복 or 비밀번호 조건 불만족"),
            @ApiResponse(responseCode = "200", description = "로그인 성공", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiData.class))})})
    public ApiResult login(@RequestBody MemberLoginRequest request) throws JsonProcessingException {


        TokenDto tokenDto = authService.login(request.toServiceRequest());

        memberService.updateMemberAccessToken(tokenDto.getMemberId());


        return ApiData.of(tokenDto);
    }
}
