package com.ssafy.alphano.api.stock.controller.request;

import com.ssafy.alphano.api.member.service.request.MemberLikeStockServiceRequest;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberLikeStockRequest {

    String stockName;

    @Builder
    public MemberLikeStockRequest(String memberId, String stockCode, String stockName) {
        this.stockName = stockName;
    }

    public MemberLikeStockServiceRequest toServiceRequest() {
        return MemberLikeStockServiceRequest.builder()
                .stockName(stockName)
                .build();
    }
}
