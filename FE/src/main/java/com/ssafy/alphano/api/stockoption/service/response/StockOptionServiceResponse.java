package com.ssafy.alphano.api.stockoption.service.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StockOptionServiceResponse {
    Long id;
    String optionName;

    @Builder
    public StockOptionServiceResponse(Long id, String optionName) {
        this.id = id;
        this.optionName = optionName;
    }
}
