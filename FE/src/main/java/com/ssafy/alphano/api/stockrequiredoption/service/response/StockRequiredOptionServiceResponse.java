package com.ssafy.alphano.api.stockrequiredoption.service.response;

import com.ssafy.alphano.domain.stockrequiredoption.Enum.TargetPriceCondition;
import com.ssafy.alphano.domain.stockrequiredoption.Enum.TargetPriceOptionInequality;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StockRequiredOptionServiceResponse {

    Long id;

    String stockName;

    int targetPrice;

    TargetPriceCondition targetPriceCondition;

    int targetOrderPrice;

    TargetPriceOptionInequality targetOptionInequality;

    @Builder
    public StockRequiredOptionServiceResponse(Long id, String stockName, int targetPrice, TargetPriceCondition targetPriceCondition, int targetOrderPrice, TargetPriceOptionInequality targetOptionInequality) {
        this.id = id;
        this.stockName = stockName;
        this.targetPrice = targetPrice;
        this.targetPriceCondition = targetPriceCondition;
        this.targetOrderPrice = targetOrderPrice;
        this.targetOptionInequality = targetOptionInequality;
    }
}
