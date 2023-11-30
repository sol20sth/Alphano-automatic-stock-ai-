package com.ssafy.alphano.api.redis.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssafy.alphano.api.redis.service.RedisService;
import com.ssafy.alphano.api.redis.service.request.RedisMemberRequest;
import com.ssafy.alphano.common.response.ApiData;
import com.ssafy.alphano.common.response.ApiResult;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/redis")
@Slf4j(topic = "RedisController")
@RequiredArgsConstructor
public class RedisController {

    private final RedisService redisService;

    @PostMapping("/set/{key}/{value}")
    @ApiResponse(description = "Redis에 key-value 저장")
    public String set(@PathVariable String key, @RequestBody List<RedisMemberRequest> value) {

        redisService.addValueToList(key, value);
        return "Value set in Redis";
    }

    @GetMapping("/get/{key}")
    public ApiResult get(@PathVariable String key) throws JsonProcessingException {
        Object result = redisService.getList(key);
        return ApiData.of(result);
    }

    @GetMapping("/get/all")
    public ApiResult getAllRedisValue() {
        return ApiData.of(redisService.getAllRedisValues());
    }

    @GetMapping("/currentPrice/all")
    public ApiData getCurrentAllStockPrice(){
        return ApiData.of(redisService.getCurrentAllStockPrice());
    }
//
//    @GetMapping("/currentPrice/{stockCode}")
//    public ApiData getCurrentStockPrice(@PathVariable String stockCode){
//        return ApiData.of(redisService.getCurrentStockPrice(stockCode));
//    }

}
