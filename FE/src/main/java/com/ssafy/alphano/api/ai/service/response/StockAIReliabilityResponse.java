package com.ssafy.alphano.api.ai.service.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StockAIReliabilityResponse {

    Long stockId;

    int endPrice;

    int aiEndPrice;

    int maxPrice;

    int aiMaxPrice;

    int minPrice;

    int aiMinPrice;

    float aiEndReliability;

    float aiMaxReliability;

    float aiMinReliability;

    float aiReliability;

    @Builder
    public StockAIReliabilityResponse(Long stockId, int endPrice, int aiEndPrice, int maxPrice, int aiMaxPrice, int minPrice, int aiMinPrice, float aiEndReliability, float aiMaxReliability, float aiMinReliability, float aiReliability) {
        this.stockId = stockId;
        this.endPrice = endPrice;
        this.aiEndPrice = aiEndPrice;
        this.maxPrice = maxPrice;
        this.aiMaxPrice = aiMaxPrice;
        this.minPrice = minPrice;
        this.aiMinPrice = aiMinPrice;
        this.aiEndReliability = aiEndReliability;
        this.aiMaxReliability = aiMaxReliability;
        this.aiMinReliability = aiMinReliability;
        this.aiReliability = aiReliability;
    }

    public StockAIReliabilityResponse(Long stockId, float aiEndReliability, float aiMaxReliability, float aiMinReliability, float aiReliability) {
        this.stockId = stockId;
        this.aiEndReliability = aiEndReliability;
        this.aiMaxReliability = aiMaxReliability;
        this.aiMinReliability = aiMinReliability;
        this.aiReliability = aiReliability;
    }

    public StockAIReliabilityResponse(Long stockId, int endPrice, int aiEndPrice, int maxPrice, int aiMaxPrice, int minPrice, int aiMinPrice) {
        this.stockId = stockId;
        this.endPrice = endPrice;
        this.aiEndPrice = aiEndPrice;
        this.maxPrice = maxPrice;
        this.aiMaxPrice = aiMaxPrice;
        this.minPrice = minPrice;
        this.aiMinPrice = aiMinPrice;
    }

    public void updateReliability(float aiEndPriceReliability, float aiMaxPriceReliability, float aiMinPriceReliability) {
        this.aiEndReliability = aiEndPriceReliability;
        this.aiMaxReliability = aiMaxPriceReliability;
        this.aiMinReliability = aiMinPriceReliability;
        this.aiReliability = (aiEndPriceReliability + aiMaxPriceReliability + aiMinPriceReliability) / 3;
    }
}
