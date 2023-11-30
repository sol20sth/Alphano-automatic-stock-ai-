package com.ssafy.alphano.api.member.middle.service.response;

import com.ssafy.alphano.domain.member.Enum.BuySell;
import com.ssafy.alphano.domain.member.Enum.IsOrdered;
import com.ssafy.alphano.domain.member.entity.middle.MemberOrderStock;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nonapi.io.github.classgraph.utils.LogNode;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class MemberOrderStockServiceResponse {

    String memberOrderStockNickname;

    Long memberOrderStockId;

    String stockName;

    BuySell buySell;

    IsOrdered isOrdered;

    LocalDateTime startTime;

    LocalDateTime endTime;

    int orderCount;

    LocalDateTime createdTime;

    LocalDateTime updatedTime;

    @Builder
    public MemberOrderStockServiceResponse(String memberOrderStockNickname, Long memberOrderStockId, String stockName, BuySell buySell, IsOrdered isOrdered,
                                           LocalDateTime startTime, LocalDateTime endTime,
                                           int orderCount, LocalDateTime createdTime, LocalDateTime updatedTime) {
        this.memberOrderStockNickname = memberOrderStockNickname;
        this.memberOrderStockId = memberOrderStockId;
        this.stockName = stockName;
        this.buySell = buySell;
        this.isOrdered = isOrdered;
        this.startTime = startTime;
        this.endTime = endTime;
        this.orderCount = orderCount;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
    }

    public static MemberOrderStockServiceResponse of(MemberOrderStock memberOrderStock) {
        return MemberOrderStockServiceResponse.builder()
                .memberOrderStockNickname(memberOrderStock.getMemberOrderStockNickname())
                .memberOrderStockId(memberOrderStock.getId())
                .stockName(memberOrderStock.getStock().getStockName())
                .buySell(memberOrderStock.getBuySell())
                .isOrdered(memberOrderStock.getIsOrdered())
                .startTime(memberOrderStock.getStartTime())
                .endTime(memberOrderStock.getEndTime())
                .orderCount(memberOrderStock.getOrderCount())
                .build();
    }
}
