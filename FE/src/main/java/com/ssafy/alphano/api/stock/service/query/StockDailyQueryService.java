package com.ssafy.alphano.api.stock.service.query;

import com.ssafy.alphano.api.stock.service.response.StockDailyResponse;
import com.ssafy.alphano.common.response.ApiData;
import com.ssafy.alphano.common.response.ApiResult;
import com.ssafy.alphano.domain.stock.repository.query.StockDailyQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StockDailyQueryService {

    private final StockDailyQueryRepository stockDailyQueryRepository;

    public List<StockDailyResponse> findStockDailyByStockCode(String stockCode) {
        return stockDailyQueryRepository.findStockDailyByStockCode(stockCode);
    }

    public ApiResult findAllStockDaily() {
        return ApiData.of(stockDailyQueryRepository.findAllStockDaily());
    }

    public List<StockDailyResponse> findStockDailyForOption(String StockCode, int days){
        return stockDailyQueryRepository.findOptionByStockCode(StockCode, days);
    }
}
