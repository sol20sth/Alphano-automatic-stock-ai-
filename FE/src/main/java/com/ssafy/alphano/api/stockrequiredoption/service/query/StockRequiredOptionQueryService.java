package com.ssafy.alphano.api.stockrequiredoption.service.query;

import com.ssafy.alphano.common.response.ApiData;
import com.ssafy.alphano.common.response.ApiResult;
import com.ssafy.alphano.domain.stockrequiredoption.repository.StockRequiredOptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StockRequiredOptionQueryService {

    private final StockRequiredOptionRepository stockRequiredOptionRepository;

    public ApiResult findStockRequiredOption(Long id) {
        return ApiData.of(stockRequiredOptionRepository.findById(id));
    }
}
