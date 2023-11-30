package com.ssafy.alphano.domain.member.entity.middle;

import com.ssafy.alphano.api.member.middle.controller.request.MemberOrderStockUpdateRequest;
import com.ssafy.alphano.api.member.middle.service.request.MemberOrderStockServiceRequest;
import com.ssafy.alphano.domain.common.BaseEntity;
import com.ssafy.alphano.domain.member.Enum.BuySell;
import com.ssafy.alphano.domain.member.Enum.IsOrdered;
import com.ssafy.alphano.domain.member.entity.Member;
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
public class MemberOrderStock extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_order_stock_id")
    Long id;

    @Column(nullable = false)
    String memberOrderStockNickname;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL, mappedBy = "memberOrderStock")
    private OrderList orderList;

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL, mappedBy = "memberOrderStock")
    private StockRequiredOption stockRequiredOption;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "stock_id", nullable = false)
    private Stock stock;

    @OneToMany(mappedBy = "memberOrderStock", cascade = CascadeType.ALL, fetch = LAZY)
    private List<MemberOption> memberOptions = new ArrayList<>();

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BuySell buySell; // S(sell), B(buy)

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private IsOrdered isOrdered; // Success, Cancel, Wait, Fail

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    @Column(nullable = true)
    int orderCount;

    @Column(nullable = true)
    int count;

    @Column(nullable = true)
    int price;

    int totalPrice;

    @Column(nullable = true)
    String orderNumber;

    public void addMemberOptionItem(MemberOption memberOption) {
        memberOptions.add(memberOption);
        memberOption.setMemberOrderStock(this);
    }

    @Builder
    public MemberOrderStock(Long id, String memberOrderStockNickname,Member member, OrderList orderList, StockRequiredOption stockRequiredOption,
                            Stock stock, List<MemberOption> memberOptions, BuySell buySell, IsOrdered isOrdered,
                            LocalDateTime startTime, LocalDateTime endTime, int orderCount, int count, String orderNumber) {
        this.id = id;
        this.memberOrderStockNickname = memberOrderStockNickname;
        this.member = member;
        this.orderList = orderList;
        this.stockRequiredOption = stockRequiredOption;
        this.stock = stock;
        if(memberOptions != null) {
            for (MemberOption memberOption : memberOptions) {
                this.addMemberOptionItem(memberOption);
            }
        }

        this.buySell = buySell;
        this.isOrdered = isOrdered;
        this.startTime = startTime;
        this.endTime = endTime;
        this.orderCount = orderCount;
        this.count = count;
        this.orderNumber = orderNumber;
    }

    //== 비즈니스 로직==//
    public void updateMemberOrderStock(MemberOrderStockUpdateRequest request) {
        this.memberOrderStockNickname = request.getMemberOrderStockNickname();
        this.startTime = request.getStartTime();
        this.endTime = request.getEndTime();
        this.orderCount = request.getOrderCount();
    }
}
