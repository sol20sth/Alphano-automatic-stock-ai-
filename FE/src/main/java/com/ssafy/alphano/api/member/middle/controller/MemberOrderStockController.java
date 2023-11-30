package com.ssafy.alphano.api.member.middle.controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssafy.alphano.api.member.middle.service.MemberOrderStockService;
import com.ssafy.alphano.api.member.middle.service.query.MemberOrderStockQueryService;
import com.ssafy.alphano.api.member.service.AuthService;
import com.ssafy.alphano.api.stock.controller.request.MemberOrderStockRequest;
import com.ssafy.alphano.common.response.ApiData;
import com.ssafy.alphano.common.response.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class MemberOrderStockController {

    private final MemberOrderStockService memberOrderStockService;

    private final MemberOrderStockQueryService memberOrderStockQueryService;

    private final AuthService authService;

    @PostMapping("/order/stock/create")
    @Operation(summary = "회원 주문 옵션 등록", description = "회원 주문 옵션 등록 - 종목이름, 필수옵션, 선택옵션")
    public ApiResult createMemberOrderStock(HttpServletRequest servletRequest, @RequestBody MemberOrderStockRequest request) throws JsonProcessingException {
        String authorization = servletRequest.getHeader("Authorization");

        Long memberId = authService.findMemberIdByToken(authorization);

        return ApiData.of(memberOrderStockService.createMemberOrderStock(memberId, request.toServiceRequest()));
    }

    @PostMapping("/order/stock/update")
    @Operation(summary = "회원 주문 옵션 수정", description = "회원 주문 옵션 수정 - 종목이름, 필수옵션, 선택옵션")
    public  ApiResult updateMemberOrderStock(HttpServletRequest servletRequest, @RequestBody MemberOrderStockRequest request) throws JsonProcessingException {
        String authorization = servletRequest.getHeader("Authorization");

        Long memberId = authService.findMemberIdByToken(authorization);

        return ApiData.of(memberOrderStockService.updateMemberOrderStock(memberId, request.toServiceRequest()));
    }

    @DeleteMapping("/order/stock/delete")
    @ResponseStatus(HttpStatus.FOUND)
    @Operation(summary = "회원 주문 옵션 삭제", description = "회원 주문 옵션 삭제 - 종목이름")
    public ApiResult removeMemberOrderStock(HttpServletRequest servletRequest, @RequestParam String memberOrderStockNickname) {

        String authorization = servletRequest.getHeader("Authorization");

        Long memberId = authService.findMemberIdByToken(authorization);

        return memberOrderStockService.removeMemberOrderStock(memberId, memberOrderStockNickname);
    }

    @GetMapping("/order/stock")
    @Operation(summary = "회원 주문 옵션 전체 조회", description = "회원 주문 옵션 전체 조회")
    public ApiResult getMemberOrderStock(HttpServletRequest servletRequest) {

        String authorization = servletRequest.getHeader("Authorization");

        Long memberId = authService.findMemberIdByToken(authorization);

        return memberOrderStockQueryService.findMemberOrderStock(memberId);
    }


    @GetMapping("order/stock/nickname")
    @Operation(summary = "별칭으로 회원 주문 옵션 조회", description = "별칭으로 회원 주문 옵션 조회 - 별칭")
    public ApiResult getMemberOrderStockNickname(HttpServletRequest servletRequest, @RequestParam String memberOrderStockNickname) {
        String authorization = servletRequest.getHeader("Authorization");

        Long memberId = authService.findMemberIdByToken(authorization);

        return memberOrderStockQueryService.findMemberOrderStockNickname(memberId, memberOrderStockNickname);
    }

    @GetMapping("order/stock/member-all")
    @Operation(summary = "전체 회원 주문 조회", description = "전체 회원 주문 조회 by 종목코드")
    public ApiResult getAllMemberOrderStock(String stockCode) {
        return ApiData.of(memberOrderStockQueryService.findAllmemberOrderStock(stockCode));
    }

}
