package com.ssafy.alphano.util.springbatch;

import com.ssafy.alphano.api.member.middle.controller.dto.MemberOrderStockResponseDto;
import com.ssafy.alphano.api.member.middle.service.dto.CreateMemberOptionDto;
import com.ssafy.alphano.api.member.middle.service.query.MemberOrderStockQueryService;
import com.ssafy.alphano.api.member.service.query.MemberQueryService;
import com.ssafy.alphano.api.redis.service.RedisService;
import com.ssafy.alphano.api.stock.service.query.StockIndicatorQueryService;
import com.ssafy.alphano.api.stock.service.query.response.StockIndicatorResponse;
import com.ssafy.alphano.domain.member.Enum.OptionInequality;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@EnableScheduling
@Component
@RequiredArgsConstructor
public class UpdateOrderToRedisScheduler {
    private final MemberOrderStockQueryService memberOrderStockQueryService;

    private final MemberQueryService memberQueryService;

    private final StockIndicatorQueryService stockIndicatorQueryService;
    private final RedisService redisService;

//    @Scheduled(cron = "0 */1 * * * *")
    public void updateOrderToRedis() {

        List<StockIndicatorResponse> allStockIndicators = stockIndicatorQueryService.findAllStockIndicators();
        allStockIndicators.forEach(stockIndicatorResponse -> {
            String stockCode = stockIndicatorResponse.getStockCode();
            List<MemberOrderStockResponseDto> memberOrderStockList = memberOrderStockQueryService.findAllmemberOrderStock(stockCode);
            if (memberOrderStockList.size() > 0) {
                for (MemberOrderStockResponseDto memberOrderStock : memberOrderStockList) {
                    if (memberOrderStock.getMemberOptions() == null) {
                        System.out.println("조건 만족");
                        continue;
                    }

                    boolean flag = true;

                    for (CreateMemberOptionDto memberOption : memberOrderStock.getMemberOptions()) {

                        if (memberOption.getOptionInequality().equals(OptionInequality.LESS)) {
                            if(isGreater(stockIndicatorResponse, memberOption)){
                                flag = false;
                                break;
                            }
                        } else if (memberOption.getOptionInequality().equals(OptionInequality.GREATER)) {
                            if(isLess(stockIndicatorResponse, memberOption)){
                                flag = false;
                                break;
                            }
                        }

                    }
                    if(flag){
                        redisService.addValueForTest(memberOrderStock.getStockName(), "조건 만족");
                    }

                }
            }
        });
    }

    private boolean isLess(StockIndicatorResponse stockIndicatorResponse, CreateMemberOptionDto memberOption) {
        return memberOption.getOptionValue() < stockIndicatorResponse.getStockIndicator().get(memberOption.getOptionName());
    }

    private boolean isGreater(StockIndicatorResponse stockIndicatorResponse, CreateMemberOptionDto memberOption) {
        return memberOption.getOptionValue() > stockIndicatorResponse.getStockIndicator().get(memberOption.getOptionName());
    }

}