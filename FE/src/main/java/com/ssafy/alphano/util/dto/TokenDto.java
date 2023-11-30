package com.ssafy.alphano.util.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TokenDto {

    Long memberId;

    String grantType;
    String accessToken;

    long accessTokenExpiresIn;
    String refreshToken;

    @Builder
    public TokenDto(Long memberId, String grantType, String accessToken, long accessTokenExpiresIn, String refreshToken) {
        this.memberId = memberId;
        this.grantType = grantType;
        this.accessToken = accessToken;
        this.accessTokenExpiresIn = accessTokenExpiresIn;
        this.refreshToken = refreshToken;
    }

    public void addMemberId(Long memberId) {
        this.memberId = memberId;
    }
}
