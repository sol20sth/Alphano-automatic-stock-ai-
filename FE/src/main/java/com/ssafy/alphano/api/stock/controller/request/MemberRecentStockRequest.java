package com.ssafy.alphano.api.stock.controller.request;

import com.ssafy.alphano.api.member.service.request.MemberRecentStockServiceRequest;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberRecentStockRequest {

    String stockName;

    @Builder
    public MemberRecentStockRequest(String stockName) {
        this.stockName = stockName;
    }

    public MemberRecentStockServiceRequest toServiceRequest() {
        return MemberRecentStockServiceRequest.builder()
              .stockName(stockName)
              .build();
    }
}
