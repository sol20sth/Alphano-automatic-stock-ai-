package com.ssafy.alphano.api.stock.service.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StockDailyServiceRequest {
    String stockName;

    @Builder
    public StockDailyServiceRequest(String stockName) {
        this.stockName = stockName;
    }
}
