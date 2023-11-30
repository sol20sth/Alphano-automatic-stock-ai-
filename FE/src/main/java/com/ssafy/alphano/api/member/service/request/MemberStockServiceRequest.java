package com.ssafy.alphano.api.member.service.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class MemberStockServiceRequest {

    String stockName;
    String stockCode;
    int purchasePrice;
    int purchaseCount;
    LocalDateTime purchaseTime;

    @Builder
    public MemberStockServiceRequest(String stockName, String stockCode, int purchasePrice, int purchaseCount, LocalDateTime purchaseTime) {
        this.stockName = stockName;
        this.stockCode = stockCode;
        this.purchasePrice = purchasePrice;
        this.purchaseCount = purchaseCount;
        this.purchaseTime = purchaseTime;
    }
}
