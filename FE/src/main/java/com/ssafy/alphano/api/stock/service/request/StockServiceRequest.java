package com.ssafy.alphano.api.stock.service.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StockServiceRequest {
    String stockName;

    @Builder
    public StockServiceRequest(String stockName) {
        this.stockName = stockName;
    }
}
