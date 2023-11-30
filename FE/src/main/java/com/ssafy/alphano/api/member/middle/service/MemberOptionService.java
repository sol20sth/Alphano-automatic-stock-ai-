package com.ssafy.alphano.api.member.middle.service;

import com.ssafy.alphano.api.member.middle.service.dto.CreateMemberOptionDto;
import com.ssafy.alphano.api.member.middle.service.request.MemberOptionServiceRequest;
import com.ssafy.alphano.api.member.middle.service.response.MemberOptionServiceResponse;
import com.ssafy.alphano.common.error.ErrorCode;
import com.ssafy.alphano.common.error.exception.EntityNotFoundException;
import com.ssafy.alphano.common.response.ApiData;
import com.ssafy.alphano.common.response.ApiError;
import com.ssafy.alphano.common.response.ApiResult;
import com.ssafy.alphano.domain.member.entity.middle.MemberOption;
import com.ssafy.alphano.domain.member.entity.middle.MemberOrderStock;
import com.ssafy.alphano.domain.member.repository.MemberOptionRepository;
import com.ssafy.alphano.domain.member.repository.MemberOrderStockRepository;
import com.ssafy.alphano.domain.member.repository.MemberRepository;
import com.ssafy.alphano.domain.member.repository.query.MemberOptionQueryRepository;
import com.ssafy.alphano.domain.stockoption.entity.StockOption;
import com.ssafy.alphano.domain.stockoption.repository.StockOptionRepository;
import com.ssafy.alphano.util.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberOptionService {

    private final TokenProvider tokenProvider;

    private final MemberRepository memberRepository;

    private final MemberOptionRepository memberOptionRepository;

    private final MemberOptionQueryRepository memberOptionQueryRepository;

    private final MemberOrderStockRepository memberOrderStockRepository;

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

    public ApiResult createMemberOption(String authorization, MemberOptionServiceRequest request) {
        if(!checkJwtToken(authorization)){
            return ApiError.of(ErrorCode.INVALID_TOKEN);
        }

        List<CreateMemberOptionDto> memberOptions = request.getMemberOptions();

        for (int i = 0; i < memberOptions.size(); i++) {
            String optionName = memberOptions.get(i).getOptionName();
            boolean existsOptionName = stockOptionRepository.existsByOptionName(optionName);
            if (existsOptionName == false) {
                return ApiData.of("해당 옵션이 없습니다.");
            }
        }

        for (CreateMemberOptionDto memberOptionItem : memberOptions) {
            addMemberOption(memberOptionItem);
        }

        return ApiData.of("success");
    }

    private void addMemberOption(CreateMemberOptionDto memberOptionItem) {
        MemberOption memberOption = MemberOption.builder()
              .optionValue(memberOptionItem.getOptionValue())
              .optionInequality(memberOptionItem.getOptionInequality())
              .build();

        memberOptionRepository.save(memberOption);
    }

}
