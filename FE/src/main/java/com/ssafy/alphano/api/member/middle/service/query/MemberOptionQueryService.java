package com.ssafy.alphano.api.member.middle.service.query;

import com.ssafy.alphano.common.response.ApiData;
import com.ssafy.alphano.common.response.ApiResult;
import com.ssafy.alphano.domain.member.repository.query.MemberOptionQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberOptionQueryService {

    private final MemberOptionQueryRepository memberOptionQueryRepository;

    public ApiResult findMemberOption(Long memberOrderStockId) {
        return ApiData.of(memberOptionQueryRepository.findMemberOption(memberOrderStockId));
    }
}
