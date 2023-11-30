package com.ssafy.alphano.api.member.service.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class MemberStockResponse {

    String stockName;

    int purchasePrice;

    int purchaseCount;

    LocalDateTime purchaseTime;

    @Builder
    public MemberStockResponse(String stockName, int purchasePrice, int purchaseCount, LocalDateTime purchaseTime) {
        this.stockName = stockName;
        this.purchasePrice = purchasePrice;
        this.purchaseCount = purchaseCount;
        this.purchaseTime = purchaseTime;
    }
}
