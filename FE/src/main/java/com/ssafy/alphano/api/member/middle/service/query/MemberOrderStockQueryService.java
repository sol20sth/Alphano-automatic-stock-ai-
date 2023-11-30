package com.ssafy.alphano.api.member.middle.service.query;

import com.ssafy.alphano.api.member.middle.controller.dto.MemberOrderStockResponseDto;
import com.ssafy.alphano.common.response.ApiData;
import com.ssafy.alphano.common.response.ApiResult;
import com.ssafy.alphano.domain.member.repository.query.MemberOrderStockQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberOrderStockQueryService {

    private final MemberOrderStockQueryRepository memberOrderStockQueryRepository;

    public ApiResult findMemberOrderStock(Long memberId) {
        return ApiData.of(memberOrderStockQueryRepository.findMemberOrderStock(memberId));
    }

    public List<MemberOrderStockResponseDto> findAllmemberOrderStock(String stockCode) {
        return memberOrderStockQueryRepository.findAllMemberOrderStock(stockCode);
    }

    public ApiResult findMemberOrderStockNickname(Long memberId, String memberOrderStockNickname) {
        return ApiData.of(memberOrderStockQueryRepository.findMemberOrderStockNickname(memberId, memberOrderStockNickname));
    }
}
