package com.ssafy.alphano.api.member.controller.request;

import com.ssafy.alphano.api.member.service.request.MemberLoginServiceRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberLoginRequest {

    String username;

    String password;

    @Builder
    public MemberLoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public MemberLoginServiceRequest toServiceRequest() {
        return MemberLoginServiceRequest.builder()
                .username(username)
                .password(password)
                .build();
    }
}
