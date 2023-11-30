package com.ssafy.alphano.api.stock.controller.request;

import com.ssafy.alphano.api.member.service.request.MemberRecentStockServiceRequest;
import com.ssafy.alphano.api.member.service.request.MemberStockServiceRequest;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class MemberStockRequest {

    String stockCode;

    String stockName;

    int purchasePrice;

    int purchaseCount;

    LocalDateTime purchaseTime;

    @Builder
    public MemberStockRequest(String stockCode, String stockName, int purchasePrice, int purchaseCount, LocalDateTime purchaseTime) {

        this.stockCode = stockCode;
        this.stockName = stockName;
        this.purchasePrice = purchasePrice;
        this.purchaseCount = purchaseCount;
        this.purchaseTime = LocalDateTime.now();
    }

    public MemberStockServiceRequest toServiceRequest() {
        return MemberStockServiceRequest.builder()
              .stockCode(stockCode)
              .stockName(stockName)
              .purchasePrice(purchasePrice)
              .purchaseCount(purchaseCount)
              .purchaseTime(purchaseTime)
              .build();
    }
}
