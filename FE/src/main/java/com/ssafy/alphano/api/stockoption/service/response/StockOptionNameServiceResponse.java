package com.ssafy.alphano.api.stockoption.service.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StockOptionNameServiceResponse {
    String optionName;

    public StockOptionNameServiceResponse(String optionName){
        this.optionName = optionName;
    }
}
