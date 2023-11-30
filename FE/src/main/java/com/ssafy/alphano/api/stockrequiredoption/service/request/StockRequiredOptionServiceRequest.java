package com.ssafy.alphano.api.stockrequiredoption.service.request;

import com.ssafy.alphano.api.stockoption.service.request.StockOptionServiceRequest;
import com.ssafy.alphano.domain.stockrequiredoption.Enum.TargetPriceCondition;
import com.ssafy.alphano.domain.stockrequiredoption.Enum.TargetPriceOptionInequality;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StockRequiredOptionServiceRequest {

    int targetPrice;

    TargetPriceCondition targetPriceCondition;

    TargetPriceOptionInequality targetPriceOptionInequality;

    int targetOrderPrice;

    @Builder
    public StockRequiredOptionServiceRequest(Long id, int targetPrice, TargetPriceCondition targetPriceCondition, TargetPriceOptionInequality targetPriceOptionInequality, int targetOrderPrice) {
        this.targetPrice = targetPrice;
        this.targetPriceCondition = targetPriceCondition;
        this.targetPriceOptionInequality = targetPriceOptionInequality;
        this.targetOrderPrice = targetOrderPrice;
    }

    public StockRequiredOptionServiceRequest toServiceRequest() {
        return StockRequiredOptionServiceRequest.builder()
                .targetPrice(targetPrice)
                .targetPriceCondition(targetPriceCondition)
                .targetPriceOptionInequality(targetPriceOptionInequality)
                .targetOrderPrice(targetOrderPrice)
                .build();
    }

}
