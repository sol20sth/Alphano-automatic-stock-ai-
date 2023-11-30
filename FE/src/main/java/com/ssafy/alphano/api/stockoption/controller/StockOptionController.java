package com.ssafy.alphano.api.stockoption.controller;

import com.ssafy.alphano.api.stockoption.service.StockOptionService;
import com.ssafy.alphano.api.stockoption.service.query.StockOptionQueryService;
import com.ssafy.alphano.api.stockoption.service.request.StockOptionServiceRequest;
import com.ssafy.alphano.api.stockoption.service.response.StockOptionNameServiceResponse;
import com.ssafy.alphano.common.response.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stock")
@RequiredArgsConstructor
public class StockOptionController {

    private final StockOptionService stockOptionService;

    private final StockOptionQueryService stockOptionQueryService;

    @PostMapping("/option")
    @Operation(summary = "관리자가 선택 옵션명 추가", description = "관리자 선택 옵션명 추가")
    public ApiResult createStockOption(HttpServletRequest servletRequest, @RequestBody StockOptionServiceRequest request) {
        String authorization = servletRequest.getHeader("Authorization");

        return stockOptionService.createStockOption(authorization, request.toServiceRequest());
    }

    @GetMapping("/option")
    @Operation(summary = "선택 옵션명 전체 조회", description = "선택 옵션명 전체 조회")
    public ApiResult getStockOption() {

        return stockOptionQueryService.findAllStockOption();
    }


}
