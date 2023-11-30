package com.ssafy.alphano.domain.common;

import com.ssafy.alphano.domain.member.Enum.BuySell;
import com.ssafy.alphano.domain.member.Enum.IsOrdered;
import com.ssafy.alphano.domain.member.entity.Member;
import com.ssafy.alphano.domain.member.entity.middle.MemberOption;
import com.ssafy.alphano.domain.orderlist.entity.OrderList;
import com.ssafy.alphano.domain.stock.entity.Stock;
import com.ssafy.alphano.domain.stockrequiredoption.entity.StockRequiredOption;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    Long id;

    Long memberId;

    Long orderListId;

    Long stockRequiredOptionId;

    Long stockId;

    Long memberOptionId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BuySell buySell; // S(sell), B(buy)

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private IsOrdered isOrdered; // SUCCESS, CANCELED, WAIT, FAIL

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    @Column(nullable = false)
    int orderCount;

    LocalDateTime createdDate;

    LocalDateTime updatedDate;

    int count;

    @Column(nullable = false)
    Long member_order_stock_id;

    String orderNumber;

    @Column(nullable = false)
    String memberOrderStockNickname;

    @Builder
    public Log(Long id, Long memberId, Long orderListId, Long stockRequiredOptionId, Long stockId, Long memberOptionId, BuySell buySell, IsOrdered isOrdered, LocalDateTime startTime, LocalDateTime endTime, int orderCount, LocalDateTime createdDate, LocalDateTime updatedDate, int count, Long member_order_stock_id, String orderNumber, String memberOrderStockNickname) {
        this.id = id;
        this.memberId = memberId;
        this.orderListId = orderListId;
        this.stockRequiredOptionId = stockRequiredOptionId;
        this.stockId = stockId;
        this.memberOptionId = memberOptionId;
        this.buySell = buySell;
        this.isOrdered = isOrdered;
        this.startTime = startTime;
        this.endTime = endTime;
        this.orderCount = orderCount;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.count = count;
        this.member_order_stock_id = member_order_stock_id;
        this.orderNumber = orderNumber;
        this.memberOrderStockNickname = memberOrderStockNickname;
    }
}
