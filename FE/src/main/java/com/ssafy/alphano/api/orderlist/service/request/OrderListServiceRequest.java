package com.ssafy.alphano.api.orderlist.service.request;

import com.ssafy.alphano.domain.orderlist.Enum.OrderStatus;
import com.ssafy.alphano.domain.orderlist.entity.OrderList;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class OrderListServiceRequest {
    Long memberOrderStockId;

    int count;

    int price;

    OrderStatus orderStatus;

    int totalPrice;

    LocalDateTime orderTime;

    LocalDateTime orderCompleteTime;

    @Builder
    public OrderListServiceRequest(Long memberOrderStockId, int count, int price, OrderStatus orderStatus, int totalPrice, LocalDateTime orderTime, LocalDateTime orderCompleteTime) {
        this.memberOrderStockId = memberOrderStockId;
        this.count = count;
        this.price = price;
        this.orderStatus = orderStatus;
        this.totalPrice = totalPrice;
        this.orderTime = orderTime;
        this.orderCompleteTime = orderCompleteTime;
    }

    public OrderListServiceRequest toServiceRequest() {
        return OrderListServiceRequest.builder()
              .memberOrderStockId(memberOrderStockId)
              .count(count)
              .price(price)
              .orderStatus(orderStatus)
              .totalPrice(totalPrice)
              .orderTime(orderTime)
              .orderCompleteTime(orderCompleteTime)
              .build();
    }
}
