package com.ssafy.alphano.api.redis.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.alphano.api.redis.service.request.RedisMemberRequest;
import com.ssafy.alphano.domain.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class RedisService {


    private final RedisTemplate<String, Object> redisTemplate;

    private final RedisTemplate<String, String> stringRedisTemplate;

    private final StockRepository stockRepository;

    public void addValueToList(String key, List<RedisMemberRequest> value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void addValueForTest(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public Object getList(String key) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Map<String, Object>> myList = redisTemplate.opsForValue().get(key) == null ? new ArrayList<>() : objectMapper.readValue(objectMapper.writeValueAsString(redisTemplate.opsForValue().get(key)), new TypeReference<List<Map<String, Object>>>() {
        });
        return myList;
    }

    public void createCurrentStockPrice(String stockCode, String stockPrice) {
        stringRedisTemplate.opsForValue().set(stockCode, stockPrice);
    }

    public Map<String, String> getCurrentAllStockPrice() {
        Pattern pattern = Pattern.compile("\\+\\d{6}");
        Map<String, String> stockPrice = new HashMap<>();

        Set<String> keys = stringRedisTemplate.keys("*");
        for (String key : keys) {
            if (pattern.matcher(key).matches()) {
                stockPrice.put(key, stringRedisTemplate.opsForValue().get(key));
            }
        }

        return stockPrice;
    }

    public Map<String, String> getAllRedisValues() {
        Map<String, String> redisValues = new HashMap<>();

        Set<String> keys = stringRedisTemplate.keys("*");
        for (String key : keys) {
            redisValues.put(key, stringRedisTemplate.opsForValue().get(key));
        }

        return redisValues;
    }
}
