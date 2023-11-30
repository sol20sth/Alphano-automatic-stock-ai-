package com.ssafy.alphano.api.stock.service.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StockServiceResponse {

    Long id;

    String stockName;

    String stockCode;

    public StockServiceResponse(String stockName, String stockCode) {
        this.stockName = stockName;
        this.stockCode = stockCode;
    }

    public StockServiceResponse(Long id, String stockName, String stockCode) {
        this.id = id;
        this.stockName = stockName;
        this.stockCode = stockCode;
    }
}
