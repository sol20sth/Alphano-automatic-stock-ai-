package com.ssafy.alphano.api.orderlist.service.response;

import com.ssafy.alphano.domain.member.entity.middle.MemberOrderStock;
import com.ssafy.alphano.domain.orderlist.Enum.OrderStatus;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class OrderListServiceResponse {

    MemberOrderStock memberOrderStock;

    int count;

    int price;

    OrderStatus orderStatus;

    int totalPrice;

    LocalDateTime orderTime;

    LocalDateTime orderCompleteTime;

    @Builder
    public OrderListServiceResponse(MemberOrderStock memberOrderStock, int count, int price, OrderStatus orderStatus, int totalPrice, LocalDateTime orderTime, LocalDateTime orderCompleteTime) {
        this.memberOrderStock = memberOrderStock;
        this.count = count;
        this.price = price;
        this.orderStatus = orderStatus;
        this.totalPrice = totalPrice;
        this.orderTime = orderTime;
        this.orderCompleteTime = orderCompleteTime;
    }
}
