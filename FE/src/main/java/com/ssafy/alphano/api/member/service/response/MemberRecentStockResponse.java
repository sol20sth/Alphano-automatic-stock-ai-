package com.ssafy.alphano.api.member.service.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberRecentStockResponse {

    String stockName;

    String stockCode;

    @Builder
    public MemberRecentStockResponse(String stockName, String stockCode) {
        this.stockName = stockName;
        this.stockCode = stockCode;
    }
    
}
