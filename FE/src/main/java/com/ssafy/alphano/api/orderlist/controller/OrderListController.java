package com.ssafy.alphano.api.orderlist.controller;

import com.ssafy.alphano.domain.orderlist.Enum.OrderStatus;
import com.ssafy.alphano.domain.orderlist.entity.OrderList;
import com.ssafy.alphano.domain.orderlist.repository.OrderListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class OrderListController {
    private final OrderListRepository orderRepository;

    @GetMapping("/order")
    public void getItem() {
        orderRepository.save(OrderList.builder().id(1L).count(10).price(1000).orderStatus(OrderStatus.FINISH).orderTime(LocalDateTime.now()).build());
    }
}
