package com.ssafy.alphano.api.ai.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssafy.alphano.api.ai.service.AIService;
import com.ssafy.alphano.common.response.ApiData;
import com.ssafy.alphano.common.response.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ai")
@RequiredArgsConstructor
public class AIController {


    private final AIService aiService;

    @PostMapping("/stock")
    public ApiResult sendStockCodeToAI(String stockCode) throws JsonProcessingException {
        return ApiData.of(aiService.sendStockCodeToAI(stockCode));
    }

    @PutMapping("/stock-reliability")
    public void updateAiReliability() {
        aiService.updateAIReliability();
    }
}
