package com.ssafy.alphano.api.member.middle.controller.request;

import com.ssafy.alphano.api.member.middle.service.dto.UpdateMemberOptionDto;
import com.ssafy.alphano.domain.stockrequiredoption.Enum.TargetPriceCondition;
import com.ssafy.alphano.domain.stockrequiredoption.Enum.TargetPriceOptionInequality;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class MemberOrderStockUpdateRequest {

    String memberOrderStockNickname;

    LocalDateTime startTime;

    LocalDateTime endTime;

    int orderCount;

    int targetPrice;

    TargetPriceCondition targetPriceCondition;

    TargetPriceOptionInequality targetPriceOptionInequality;

    int targetOrderPrice;

    List<UpdateMemberOptionDto> memberOptions;

    @Builder
    public MemberOrderStockUpdateRequest(
            String memberOrderStockNickname,
            LocalDateTime startTime, LocalDateTime endTime,
            int orderCount, int targetPrice,
            TargetPriceCondition targetPriceCondition,
            TargetPriceOptionInequality targetPriceOptionInequality,
            int targetOrderPrice, List<UpdateMemberOptionDto> memberOptions
    ) {
        this.memberOrderStockNickname = memberOrderStockNickname;
        this.startTime = startTime;
        this.endTime = endTime;
        this.orderCount = orderCount;
        this.targetPrice = targetPrice;
        this.targetPriceCondition = targetPriceCondition;
        this.targetPriceOptionInequality = targetPriceOptionInequality;
        this.targetOrderPrice = targetOrderPrice;
        this.memberOptions = memberOptions;
    }

    public MemberOrderStockUpdateRequest toServiceRequest() {
        return MemberOrderStockUpdateRequest.builder()
              .memberOrderStockNickname(memberOrderStockNickname)
              .startTime(startTime)
              .endTime(endTime)
              .orderCount(orderCount)
              .targetPrice(targetPrice)
              .targetPriceCondition(targetPriceCondition)
              .targetPriceOptionInequality(targetPriceOptionInequality)
              .targetOrderPrice(targetOrderPrice)
              .memberOptions(memberOptions)
              .build();
    }
}
