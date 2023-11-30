package com.ssafy.alphano.api.stock.service.query.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@Data
public class StockIndicatorResponse {

    String stockCode;
    String stockName;
    String stockDate;
    Map<String, Double> stockIndicator = new HashMap<String, Double>();

    @Builder
    public StockIndicatorResponse(String stockCode, String stockName, String stockDate, double RSI, double MACD) {
        this.stockCode = stockCode;
        this.stockName = stockName;
        this.stockDate = stockDate;
        this.stockIndicator.put("RSI", RSI);
        this.stockIndicator.put("MACD", MACD);

    }
}
