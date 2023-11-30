package com.ssafy.alphano.api.stockrequiredoption.service;

import com.ssafy.alphano.api.stockrequiredoption.service.request.StockRequiredOptionServiceRequest;
import com.ssafy.alphano.common.error.ErrorCode;
import com.ssafy.alphano.common.error.exception.EntityNotFoundException;
import com.ssafy.alphano.common.response.ApiData;
import com.ssafy.alphano.common.response.ApiError;
import com.ssafy.alphano.common.response.ApiResult;
import com.ssafy.alphano.domain.member.entity.Member;
import com.ssafy.alphano.domain.member.repository.MemberOrderStockRepository;
import com.ssafy.alphano.domain.member.repository.MemberRepository;
import com.ssafy.alphano.domain.stockrequiredoption.entity.StockRequiredOption;
import com.ssafy.alphano.domain.stockrequiredoption.repository.StockRequiredOptionRepository;
import com.ssafy.alphano.util.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockRequiredOptionService {

    private final TokenProvider tokenProvider;

    private final MemberRepository memberRepository;

    private final MemberOrderStockRepository memberOrderStockRepository;

    private final StockRequiredOptionRepository stockRequiredOptionRepository;


    private boolean checkJwtToken(String authorization) {
        if (authorization == null || !authorization.startsWith("Bearer")) {
            return false;
        }

        String jwtToken = authorization.substring(7);

        if (!tokenProvider.validateToken(jwtToken)) {
            return false;
        }

        return true;
    }

    public ApiResult createStockRequiredOption(String authorization, StockRequiredOptionServiceRequest request) {
        if(!checkJwtToken(authorization)){
            return ApiError.of(ErrorCode.INVALID_TOKEN);
        }

        if(stockRequiredOptionRepository.findByTargetOrderPrice(request.getTargetOrderPrice()) != null) {
            return ApiData.of("수정할 수 있습니다.");
        }
        addStockRequiredOption(request);
        return ApiData.of("success");
    }

    private void addStockRequiredOption(StockRequiredOptionServiceRequest request) {
        StockRequiredOption stockRequiredOption = StockRequiredOption.builder()
                .targetPrice(request.getTargetPrice())
                .targetPriceCondition(request.getTargetPriceCondition())
                .targetPriceOptionInequality(request.getTargetPriceOptionInequality())
                .targetOrderPrice(request.getTargetOrderPrice())
                .build();
        stockRequiredOptionRepository.save(stockRequiredOption);
    }

}
