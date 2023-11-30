package com.ssafy.alphano.api.orderlist.service;

import com.ssafy.alphano.api.orderlist.service.request.OrderListServiceRequest;
import com.ssafy.alphano.common.error.ErrorCode;
import com.ssafy.alphano.common.error.exception.EntityNotFoundException;
import com.ssafy.alphano.common.response.ApiData;
import com.ssafy.alphano.common.response.ApiResult;
import com.ssafy.alphano.domain.member.repository.MemberOrderStockRepository;
import com.ssafy.alphano.domain.orderlist.Enum.OrderStatus;
import com.ssafy.alphano.domain.orderlist.entity.OrderList;
import com.ssafy.alphano.domain.orderlist.repository.OrderListRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrderListService {

    private final OrderListRepository orderListRepository;

    private final MemberOrderStockRepository memberOrderStockRepository;

    public ApiResult createOrderList(OrderListServiceRequest request) {

        Long memberOrderStockId = request.getMemberOrderStockId();

        int count = request.getCount();

        int price = request.getPrice();

        OrderStatus orderStatus = request.getOrderStatus();

        int totalPrice = request.getTotalPrice();

        LocalDateTime orderTime = request.getOrderTime();

        LocalDateTime orderCompleteTime = request.getOrderCompleteTime();

        if(orderListRepository.findById(memberOrderStockId) != null) {
            return ApiData.of("등록된 주문이 있습니다.");
        }

        addOrderList(memberOrderStockId, count, price, orderStatus, totalPrice, orderTime, orderCompleteTime);
        return ApiData.of("success");
    }

    private void addOrderList(Long memberOrderStockId, int count, int price, OrderStatus orderStatus, int totalPrice, LocalDateTime orderTime, LocalDateTime orderCompleteTime) {
        OrderList orderList = OrderList.builder()
                .memberOrderStock(memberOrderStockRepository.findById(memberOrderStockId).orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND)))
                .count(count)
                .price(price)
                .orderStatus(orderStatus)
                .totalPrice(totalPrice)
                .orderTime(orderTime)
                .orderCompleteTime(orderCompleteTime)
                .build();

        orderListRepository.save(orderList);
    }


}
