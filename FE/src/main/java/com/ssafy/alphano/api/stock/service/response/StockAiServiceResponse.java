package com.ssafy.alphano.api.stock.service.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StockAiServiceResponse {

    String stockName;

    String stockCode;

    float aiMaxReliability;

    float aiMinReliability;

    float aiEndReliability;

    float aiReliability;

    float aiWeeklyReliability;

    @Builder
    public StockAiServiceResponse(String stockName, String stockCode, float aiMaxReliability, float aiMinReliability, float aiEndReliability, float aiReliability, float aiWeeklyReliability) {
        this.stockName = stockName;
        this.stockCode = stockCode;
        this.aiMaxReliability = aiMaxReliability;
        this.aiMinReliability = aiMinReliability;
        this.aiEndReliability = aiEndReliability;
        this.aiReliability = aiReliability;
        this.aiWeeklyReliability = aiWeeklyReliability;
    }

}
