package com.ssafy.alphano.api.kisapi.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class AskingPriceServiceRequest {

    String stockCode;

    @Builder
    public AskingPriceServiceRequest(String stockCode) {
        this.stockCode = stockCode;
    }
}
