package com.ssafy.alphano.api.stock.service.query;

import com.ssafy.alphano.api.stock.service.response.StockServiceResponse;
import com.ssafy.alphano.common.response.ApiData;
import com.ssafy.alphano.common.response.ApiResult;

import com.ssafy.alphano.api.stock.service.response.StockResponse;
import com.ssafy.alphano.domain.member.repository.query.MemberLikeStockQueryRepository;
import com.ssafy.alphano.domain.member.repository.query.MemberRecentStockQueryRepository;
import com.ssafy.alphano.domain.member.repository.query.MemberStockQueryRepository;

import com.ssafy.alphano.domain.stock.repository.query.StockQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StockQueryService {
    private final MemberLikeStockQueryRepository memberLikeStockQueryRepository;

    private final MemberStockQueryRepository memberStockQueryRepository;

    private final MemberRecentStockQueryRepository memberRecentStockQueryRepository;

    private final StockQueryRepository stockQueryRepository;

    public ApiResult findStock(String stockCode) {

        return ApiData.of(stockQueryRepository.findByStockCode(stockCode));
    }

    public List<StockResponse> findBySearchKeyword(String searchKeyword) {
        return stockQueryRepository.findStockByKeyword(searchKeyword);
    }

    public List<StockServiceResponse> findAllStock() {
        return stockQueryRepository.findAllStock();
    }

    public List<StockResponse> findByStockCode() {
        return stockQueryRepository.findAllStockCode();
    }


}
