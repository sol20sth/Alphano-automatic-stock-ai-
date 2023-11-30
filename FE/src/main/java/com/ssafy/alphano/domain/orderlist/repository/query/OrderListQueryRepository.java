package com.ssafy.alphano.domain.orderlist.repository.query;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.alphano.api.orderlist.service.response.OrderListServiceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderListQueryRepository {

    private final JPAQueryFactory queryFactory;

//    public List<OrderListServiceResponse> findOrderList(Long id) {
//        return queryFactory
//                .select(Projections.constructor(OrderListServiceResponse.class,
//                        orderList.memberOrderStock.id.as("memberOrderStockId")
//                ))
//                .from(orderList)
//                .where(orderList.memberOrderStock.id.eq(id))
//                .fetch();
//    }
}
