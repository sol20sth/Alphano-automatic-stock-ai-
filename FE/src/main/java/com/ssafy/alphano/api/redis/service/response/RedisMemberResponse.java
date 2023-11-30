package com.ssafy.alphano.api.redis.service.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RedisMemberResponse {

    Long memberId;

    int price;

    String appKey;

    String secretKey;

    String accessToken;

    @Builder
    public RedisMemberResponse(Long memberId, int price, String appKey, String secretKey, String accessToken) {
        this.memberId = memberId;
        this.price = price;
        this.appKey = appKey;
        this.secretKey = secretKey;
        this.accessToken = accessToken;
    }
}
