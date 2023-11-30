package com.ssafy.alphano.domain.orderlist.entity;

import com.ssafy.alphano.domain.member.entity.middle.MemberOrderStock;
import com.ssafy.alphano.domain.orderlist.Enum.OrderStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
public class OrderList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_list_id")
    Long id;

    @OneToOne(fetch = LAZY)
    private MemberOrderStock memberOrderStock;

    @Column(nullable = false)
    int count;

    @Column(nullable = false)
    int price;

    @Column
    int totalPrice;

    @Column(nullable = false)
    private OrderStatus orderStatus; // FINISH - 체결완료, ORDERING - 체결중, CANCELED - 미체결

    @Column(nullable = false)
    private LocalDateTime orderTime;

    @Column
    private LocalDateTime orderCompleteTime;

    @Builder
    public OrderList(Long id, MemberOrderStock memberOrderStock, int count, int price, int totalPrice, OrderStatus orderStatus, LocalDateTime orderTime, LocalDateTime orderCompleteTime) {
        this.id = id;
        this.memberOrderStock = memberOrderStock;
        this.count = count;
        this.price = price;
        this.totalPrice = totalPrice;
        this.orderStatus = orderStatus;
        this.orderTime = orderTime;
        this.orderCompleteTime = orderCompleteTime;
    }
}
