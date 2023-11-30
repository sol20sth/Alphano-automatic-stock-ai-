package com.ssafy.alphano.api.stockrequiredoption.controller;

import com.ssafy.alphano.api.stockrequiredoption.service.StockRequiredOptionService;
import com.ssafy.alphano.api.stockrequiredoption.service.query.StockRequiredOptionQueryService;
import com.ssafy.alphano.api.stockrequiredoption.service.request.StockRequiredOptionServiceRequest;
import com.ssafy.alphano.common.response.ApiResult;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/stock/option")
@RequiredArgsConstructor
public class StockRequiredOptionController {

    private final StockRequiredOptionService stockRequiredOptionService;

    private final StockRequiredOptionQueryService stockRequiredOptionQueryService;

//    @PostMapping("/required")
//    public ApiResult createStockRequiredOption(HttpServletRequest servletRequest, @RequestBody StockRequiredOptionServiceRequest request) {
//        String authorization = servletRequest.getHeader("Authorization");
//
//        return stockRequiredOptionService.createStockRequiredOption(authorization, request.toServiceRequest());
//    }

//    @GetMapping("/required/{memberOrderStockId}")
//    public ApiResult getStockRequiredOption(@RequestParam Long memberOrderStockId) {
//        return stockRequiredOptionQueryService.findStockRequiredOption(memberOrderStockId);
//    }
}
