package com.ssafy.alphano.api.member.service.response;

import lombok.Getter;

@Getter
public class MemberResponse {

    Long memberId;
    String username;

    String approvalKey;

    public MemberResponse(Long memberId, String username, String approvalKey) {
        this.memberId = memberId;
        this.username = username;
        this.approvalKey = approvalKey;
    }
}
