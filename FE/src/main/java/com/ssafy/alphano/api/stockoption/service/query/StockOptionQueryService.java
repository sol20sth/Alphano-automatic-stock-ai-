package com.ssafy.alphano.api.stockoption.service.query;

import com.ssafy.alphano.api.stockoption.service.response.StockOptionNameServiceResponse;
import com.ssafy.alphano.common.response.ApiData;
import com.ssafy.alphano.common.response.ApiResult;
import com.ssafy.alphano.domain.stockoption.repository.StockOptionRepository;
import com.ssafy.alphano.domain.stockoption.repository.query.StockOptionQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StockOptionQueryService {

    private final StockOptionRepository stockOptionRepository;

    private final StockOptionQueryRepository stockOptionQueryRepository;

    public ApiResult findStockOption(Long id) {
        return ApiData.of(stockOptionRepository.findById(id));
    }

    public ApiResult findAllStockOption() {
        return ApiData.of(stockOptionQueryRepository.findAllStockOption());
    }
}
