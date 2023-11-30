package com.ssafy.alphano.api.member.service.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberLikeStockServiceRequest {

    String memberId;

    String stockName;

    String stockCode;

    @Builder
    public MemberLikeStockServiceRequest(String memberId, String stockName, String stockCode) {
        this.memberId = memberId;
        this.stockName = stockName;
        this.stockCode = stockCode;
    }
}
