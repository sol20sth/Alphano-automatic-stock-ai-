package com.ssafy.alphano.api.member.service.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberRecentStockServiceRequest {
    String stockName;

    @Builder
    public MemberRecentStockServiceRequest(String stockName) {
        this.stockName = stockName;
    }
}
