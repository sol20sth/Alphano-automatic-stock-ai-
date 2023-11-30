package com.ssafy.alphano.api.member.controller;


import com.ssafy.alphano.api.member.service.AuthService;
import com.ssafy.alphano.api.member.service.MemberService;
import com.ssafy.alphano.api.member.service.query.MemberQueryService;
import com.ssafy.alphano.api.stock.controller.request.MemberLikeStockRequest;
import com.ssafy.alphano.api.stock.controller.request.MemberRecentStockRequest;
import com.ssafy.alphano.common.error.ErrorCode;
import com.ssafy.alphano.common.response.ApiData;
import com.ssafy.alphano.common.response.ApiError;
import com.ssafy.alphano.common.response.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    private final MemberQueryService memberQueryService;

    private final AuthService authService;

    @PostMapping("/like/stock")
    @Operation(summary = "주식 관심종목 추가", description = "주식 관심종목 추가(토글)")

    public ApiResult createLikeStock(HttpServletRequest servletRequest, @RequestBody MemberLikeStockRequest request) {
        String authorization = servletRequest.getHeader("Authorization");

        return memberService.createLikeStock(authorization, request.toServiceRequest());
    }

    @GetMapping("/like/stock/")
    @Operation(summary = "주식 관심종목 조회", description = "현재 로그인한 사용자의 주식 관심종목 조회")
    public ApiResult getMemberLikeStock(HttpServletRequest servletRequest) {
        String authorization = servletRequest.getHeader("Authorization");
        if (!authService.validateToken(authorization)) {
            return ApiError.of(ErrorCode.INVALID_TOKEN);
        }

        return memberQueryService.findMemberLikeStock(authService.findMemberIdByToken(authorization));
    }

    @PostMapping("/recent/stock")
    @Operation(summary = "주식 최근본종목 추가", description = "주식 최근본종목 추가")
    public ApiResult createRecentStock(HttpServletRequest servletRequest, @RequestBody MemberRecentStockRequest request) {
        String authorization = servletRequest.getHeader("Authorization");
        if (!authService.validateToken(authorization)) {
            return ApiError.of(ErrorCode.INVALID_TOKEN);
        }

        Long memberId = authService.findMemberIdByToken(authorization);

        return memberService.createRecentStock(memberId, request.toServiceRequest());
    }

    @DeleteMapping("/recent/stock/delete")
    @Operation(summary = "주식 최근본종목 삭제", description = "주식 최근본종목 삭제")
    public ApiResult deleteRecentStock(HttpServletRequest servletRequest, @RequestBody MemberRecentStockRequest request) {
        String authorization = servletRequest.getHeader("Authorization");
        if (!authService.validateToken(authorization)) {
            return ApiError.of(ErrorCode.INVALID_TOKEN);
        }

        Long memberId = authService.findMemberIdByToken(authorization);

        return memberService.deleteRecentStock(memberId, request.toServiceRequest());
    }


    @GetMapping("/recent/stock")
    @Operation(summary = "주식 최근본종목 조회", description = "현재 로그인한 사용자의 주식 최근본종목 조회")
    public ApiResult getRecentStock(HttpServletRequest servletRequest) {
        String authorization = servletRequest.getHeader("Authorization");
        if (!authService.validateToken(authorization)) {
            return ApiError.of(ErrorCode.INVALID_TOKEN);
        }

        Long memberId = authService.findMemberIdByToken(authorization);

        return memberQueryService.findMemberRecentStock(memberId);
    }

//    @PostMapping("/test-request")
//    public void testRequest(@RequestParam String trId, @RequestParam String trKey, @RequestParam String approvalKey) throws IOException {
//
//        stockWebSocketConfig.getStockSocketHandlerMap().get("price").testRequest(trId, trKey);
//
//        stockWebSocketConfig.getStockSocketHandlerMap().get("checkOrder").testRequest(trId, trKey);
//
//
//
//    }


//    @PostMapping("/{memberId}/stock")
//    public ApiResult createMemberStock(HttpServletRequest servletRequest, @RequestBody MemberStockRequest request) {
//        String authorization = servletRequest.getHeader("Authorization");
//
//        return memberService.createMemberStock(authorization, request.toServiceRequest());
//    }
//
//    @GetMapping("/{memberId}/stock")
//    public ApiResult getMemberStock(@RequestParam Long memberId) {
//
//        return memberQueryService.findMemberStock(memberId);
//    }

    @GetMapping("/private-info")
    @Operation(summary = "회원 AccessToken, ApprovalKey 조회", description = "현재 로그인한 사용자의 개인정보 조회")
    public ApiResult getMemberPirvateInfo(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");

        if (!authService.validateToken(authorization)) {
            return ApiError.of(ErrorCode.INVALID_TOKEN);
        }
        Long memberId = authService.findMemberIdByToken(authorization);

        return ApiData.of(memberQueryService.findMemberPrivateInfo(memberId));
    }
}
