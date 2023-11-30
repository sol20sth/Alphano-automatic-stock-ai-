package com.ssafy.alphano.api.member.middle.controller;

import com.ssafy.alphano.api.member.middle.controller.request.MemberOptionCreateRequest;
import com.ssafy.alphano.api.member.middle.service.MemberOptionService;
import com.ssafy.alphano.api.member.middle.service.query.MemberOptionQueryService;
import com.ssafy.alphano.common.response.ApiResult;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class MemberOptionController {

    private final MemberOptionService memberOptionService;

    private final MemberOptionQueryService memberOptionQueryService;

//    @PostMapping("/option")
//    public ApiResult createMemberOption(HttpServletRequest servletRequest, @RequestBody MemberOptionCreateRequest request) {
//        String authorization = servletRequest.getHeader("Authorization");
//
//        return memberOptionService.createMemberOption(authorization, request.toServiceRequest());
//    }

//    @GetMapping("/option/{memberOptionId}")
//    public ApiResult getMemberOption(@RequestParam Long memberOptionId) {
//        return memberOptionQueryService.findMemberOption(memberOptionId);
//    }
}
