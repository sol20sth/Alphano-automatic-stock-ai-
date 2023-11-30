package com.ssafy.alphano.api.stockdate.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssafy.alphano.api.stockdate.service.StockDateService;
import com.ssafy.alphano.common.response.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/stock-date")
@RequiredArgsConstructor
public class StockDateController {

    private final StockDateService stockDateService;

    @PostMapping("")
    @Operation(summary = "AI 데이터 csv 추가", description = "AI csv 데이터 추가")
    public ApiResult createStockDate() {
        try {
            return stockDateService.saveStockDate();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
