package com.ssafy.alphano.api.stock.service.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateStockDailyServiceRequest {

    int startPrice;

    int endPrice;

    int maxPrice;

    int minPrice;

    String stockName;


    @Builder
    public CreateStockDailyServiceRequest(int startPrice, int endPrice, int maxPrice, int minPrice, String stockName, String createDate) {
        this.startPrice = startPrice;
        this.endPrice = endPrice;
        this.maxPrice = maxPrice;
        this.minPrice = minPrice;
        this.stockName = stockName;
    }
}
