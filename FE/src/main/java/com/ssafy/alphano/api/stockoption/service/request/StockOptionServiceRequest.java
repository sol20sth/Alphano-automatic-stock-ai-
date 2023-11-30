package com.ssafy.alphano.api.stockoption.service.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StockOptionServiceRequest {
    String optionName;

    @Builder
    public StockOptionServiceRequest(String optionName) {
        this.optionName = optionName;
    }

    public StockOptionServiceRequest toServiceRequest() {
        return StockOptionServiceRequest.builder()
              .optionName(optionName)
              .build();
    }
}
