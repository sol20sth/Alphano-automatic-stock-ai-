package com.ssafy.alphano.api.stock.controller;

import com.ssafy.alphano.api.stock.service.StockDailyService;
import com.ssafy.alphano.api.stock.service.query.StockDailyQueryService;
import com.ssafy.alphano.api.stock.service.query.StockQueryService;
import com.ssafy.alphano.api.stock.service.response.StockServiceResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stock")
@RequiredArgsConstructor
public class StockController {

    private final StockQueryService stockQueryService;

    private final StockDailyService stockDailyService;

    private final StockDailyQueryService stockDailyQueryService;
    @GetMapping("/info")
    @Operation(summary = "주식 정보 조회", description = "모든 주식 정보 조회")
    public List<StockServiceResponse> getStockInfo() {
        return stockQueryService.findAllStock();
    }
//    @GetMapping("/{stockCode}")
//    public ApiResult getStock(HttpServletRequest servletRequest, @RequestParam String stockCode) {
//        return stockQueryService.findStock(stockCode);
//    }
//
//    @PostMapping("/push-csv")
//    public ApiResult pushCsv() {
//        return stockDailyService.pushData();
//    }





}