package com.ssafy.alphano.api.member.middle.service.request;

import com.ssafy.alphano.api.member.middle.service.dto.CreateMemberOptionDto;
import com.ssafy.alphano.domain.member.Enum.BuySell;
import com.ssafy.alphano.domain.member.Enum.IsOrdered;
import com.ssafy.alphano.domain.stockrequiredoption.Enum.TargetPriceCondition;
import com.ssafy.alphano.domain.stockrequiredoption.Enum.TargetPriceOptionInequality;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class MemberOrderStockServiceRequest {

    String memberOrderStockNickname;

    String stockName;

    BuySell buySell; // 매도매수

    IsOrdered isOrdered;

    LocalDateTime endTime;

    int orderCount;

    int targetPrice;

    TargetPriceCondition targetPriceCondition;

    TargetPriceOptionInequality targetPriceOptionInequality;

    int targetOrderPrice;

    List<CreateMemberOptionDto> MemberOptions; // 선택 옵션

    @Builder
    public MemberOrderStockServiceRequest(String memberOrderStockNickname, String stockName,
                                          BuySell buySell, IsOrdered isOrdered,
                                          LocalDateTime endTime, int orderCount, int targetPrice,
                                          TargetPriceCondition targetPriceCondition,
                                          TargetPriceOptionInequality targetPriceOptionInequality, int targetOrderPrice,
                                          List<CreateMemberOptionDto> MemberOptions) {
        this.memberOrderStockNickname = memberOrderStockNickname;
        this.stockName = stockName;
        this.buySell = buySell;
        this.isOrdered = isOrdered;
        this.endTime = endTime;
        this.orderCount = orderCount;
        this.targetPrice = targetPrice;
        this.targetPriceCondition = targetPriceCondition;
        this.targetPriceOptionInequality = targetPriceOptionInequality;
        this.targetOrderPrice = targetOrderPrice;
        this.MemberOptions = MemberOptions;
    }
}
