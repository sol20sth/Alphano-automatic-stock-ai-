package com.ssafy.alphano.api.stock.controller;

import com.ssafy.alphano.api.stock.service.query.StockDailyQueryService;
import com.ssafy.alphano.common.response.ApiData;
import com.ssafy.alphano.common.response.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/stock-daily")
@RequiredArgsConstructor
public class StockDailyController {

    private final StockDailyQueryService stockDailyQueryService;

    @GetMapping("/ai")
    @Operation(summary = "AI 신뢰도 조회", description = "AI 최고가, 최저가, 종가 신뢰도 조회")
    public ApiResult getStockDailyAi() {
        return stockDailyQueryService.findAllStockDaily();
    }

    @GetMapping("/{stockCode}")
    @Operation(summary = "종목 코드로 일봉 조회", description = "종목 코드로 일자별 시가, 종가, 고가, 저가 조회 ")
    public ApiResult getStock(@PathVariable String stockCode) {
        return ApiData.of(stockDailyQueryService.findStockDailyByStockCode(stockCode));
    }
}
