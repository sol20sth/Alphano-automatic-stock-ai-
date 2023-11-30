package com.ssafy.alphano.domain.orderlist.repository;

import com.ssafy.alphano.domain.orderlist.Enum.OrderStatus;
import com.ssafy.alphano.domain.orderlist.entity.OrderList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface OrderListRepository extends JpaRepository<OrderList, Long> {

    // 주문상태 조회
    public Optional<OrderList> findByOrderStatus(OrderStatus orderStatus);

    // 주문일시로 조회
    public Optional<OrderList> findByOrderTime(LocalDateTime orderTime);

    // 주문체결일시로 조회
    public Optional<OrderList> findByOrderCompleteTime(LocalDateTime orderCompleteTime);
}
