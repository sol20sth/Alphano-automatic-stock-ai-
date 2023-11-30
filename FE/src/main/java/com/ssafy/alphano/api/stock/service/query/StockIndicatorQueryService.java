package com.ssafy.alphano.api.stock.service.query;

import com.ssafy.alphano.api.stock.service.query.response.StockIndicatorResponse;
import com.ssafy.alphano.domain.stock.repository.query.StockDailyQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StockIndicatorQueryService {

    private final StockDailyQueryRepository stockDailyQueryRepository;

    public List<StockIndicatorResponse> findAllStockIndicators() {
        return stockDailyQueryRepository.findAllStockIndicators();
    }


}
