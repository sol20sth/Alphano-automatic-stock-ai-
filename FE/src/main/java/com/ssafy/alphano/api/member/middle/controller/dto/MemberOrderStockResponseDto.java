package com.ssafy.alphano.api.member.middle.controller.dto;

import com.ssafy.alphano.api.member.middle.service.dto.CreateMemberOptionDto;
import com.ssafy.alphano.domain.member.Enum.BuySell;
import com.ssafy.alphano.domain.member.Enum.IsOrdered;
import com.ssafy.alphano.domain.member.entity.middle.MemberOption;
import com.ssafy.alphano.domain.stockrequiredoption.Enum.TargetPriceCondition;
import com.ssafy.alphano.domain.stockrequiredoption.Enum.TargetPriceOptionInequality;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class MemberOrderStockResponseDto {

    Long memberOrderStockId;

    String memberOrderStockNickname;

    String stockCode;

    String stockName;

    BuySell buySell; // 매도매수

    IsOrdered isOrdered;

    LocalDateTime startTime;

    LocalDateTime endTime;

    int orderCount;

    int count;

    int targetPrice;

    TargetPriceCondition targetPriceCondition;

    TargetPriceOptionInequality targetPriceOptionInequality;

    int targetOrderPrice;

    List<CreateMemberOptionDto> MemberOptions; // 선택 옵션

    @Builder
    public MemberOrderStockResponseDto(Long memberOrderStockId,String memberOrderStockNickname, String stockCode, String stockName,
                                          BuySell buySell, IsOrdered isOrdered, LocalDateTime startTime,
                                          LocalDateTime endTime, int orderCount, int count, int targetPrice,
                                          TargetPriceCondition targetPriceCondition,
                                          TargetPriceOptionInequality targetPriceOptionInequality, int targetOrderPrice,
                                          List<CreateMemberOptionDto> MemberOptions) {
        this.memberOrderStockId = memberOrderStockId;
        this.memberOrderStockNickname = memberOrderStockNickname;
        this.stockCode = stockCode;
        this.stockName = stockName;
        this.buySell = buySell;
        this.isOrdered = isOrdered;
        this.startTime = startTime;
        this.endTime = endTime;
        this.orderCount = orderCount;
        this.count = count;
        this.targetPrice = targetPrice;
        this.targetPriceCondition = targetPriceCondition;
        this.targetPriceOptionInequality = targetPriceOptionInequality;
        this.targetOrderPrice = targetOrderPrice;
        this.MemberOptions = MemberOptions;
    }

    public MemberOrderStockResponseDto(Long memberOrderStockId, String memberOrderStockNickname, String stockName,
                                       BuySell buySell, IsOrdered isOrdered, LocalDateTime startTime,
                                       LocalDateTime endTime, int orderCount, int targetPrice,
                                       TargetPriceCondition targetPriceCondition,
                                       TargetPriceOptionInequality targetPriceOptionInequality, int targetOrderPrice,
                                       List<CreateMemberOptionDto> MemberOptions, int count) {
        this.memberOrderStockId = memberOrderStockId;
        this.memberOrderStockNickname = memberOrderStockNickname;
        this.stockName = stockName;
        this.buySell = buySell;
        this.isOrdered = isOrdered;
        this.startTime = startTime;
        this.endTime = endTime;
        this.orderCount = orderCount;
        this.targetPrice = targetPrice;
        this.targetPriceCondition = targetPriceCondition;
        this.targetPriceOptionInequality = targetPriceOptionInequality;
        this.targetOrderPrice = targetOrderPrice;
        this.MemberOptions = MemberOptions;
        this.count = count;
    }
}


