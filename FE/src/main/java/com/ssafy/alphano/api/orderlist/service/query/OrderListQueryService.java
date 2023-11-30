package com.ssafy.alphano.api.orderlist.service.query;

import com.ssafy.alphano.common.response.ApiData;
import com.ssafy.alphano.common.response.ApiResult;
import com.ssafy.alphano.domain.orderlist.repository.query.OrderListQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderListQueryService {

    private final OrderListQueryRepository orderListQueryRepository;

//    public ApiResult findOrderList(Long memberOrderStockId) {
//        return ApiData.of(orderListQueryRepository.findOrderList(memberOrderStockId));
//    }
}
