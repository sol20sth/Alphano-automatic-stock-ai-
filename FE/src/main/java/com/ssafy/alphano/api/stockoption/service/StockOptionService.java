package com.ssafy.alphano.api.stockoption.service;

import com.ssafy.alphano.api.stockoption.service.request.StockOptionServiceRequest;
import com.ssafy.alphano.common.error.ErrorCode;
import com.ssafy.alphano.common.error.exception.EntityNotFoundException;
import com.ssafy.alphano.common.response.ApiData;
import com.ssafy.alphano.common.response.ApiError;
import com.ssafy.alphano.common.response.ApiResult;
import com.ssafy.alphano.domain.member.entity.Member;
import com.ssafy.alphano.domain.member.entity.middle.MemberRecentStock;
import com.ssafy.alphano.domain.member.repository.MemberRepository;
import com.ssafy.alphano.domain.stock.entity.Stock;
import com.ssafy.alphano.domain.stockoption.entity.StockOption;
import com.ssafy.alphano.domain.stockoption.repository.StockOptionRepository;
import com.ssafy.alphano.util.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockOptionService {

    private final TokenProvider tokenProvider;

    private final MemberRepository memberRepository;

    private final StockOptionRepository stockOptionRepository;

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

    public ApiResult createStockOption(String authorization, StockOptionServiceRequest request) {
        if(!checkJwtToken(authorization)){
            return ApiError.of(ErrorCode.INVALID_TOKEN);
        }

        String jwtToken = authorization.substring(7);

        Long memberId = Long.valueOf(tokenProvider.getUsernameFromJwt(jwtToken));

        Member member = memberRepository.findById(memberId).orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));

        if(stockOptionRepository.existsByOptionName(request.getOptionName())) {
            deleteStockOption(request);
            return ApiData.of("삭제가 완료되었습니다.");
        }
        addStockOption(request);
        return ApiData.of("success");
    }

    private void addStockOption(StockOptionServiceRequest request) {
        StockOption stockOption = StockOption.builder()
                .optionName(request.getOptionName())
                .build();
        stockOptionRepository.save(stockOption);
    }

    private void deleteStockOption(StockOptionServiceRequest request) {
        stockOptionRepository.delete(stockOptionRepository.findByOptionName(request.getOptionName()).get());
    }
}