package com.ssafy.alphano.api.member.service.query;

import com.ssafy.alphano.api.member.service.response.MemberPrivateInfoResponse;
import com.ssafy.alphano.common.error.ErrorCode;
import com.ssafy.alphano.common.response.ApiData;
import com.ssafy.alphano.common.response.ApiError;
import com.ssafy.alphano.common.response.ApiResult;
import com.ssafy.alphano.domain.member.repository.query.MemberLikeStockQueryRepository;
import com.ssafy.alphano.domain.member.repository.query.MemberQueryRepository;
import com.ssafy.alphano.domain.member.repository.query.MemberRecentStockQueryRepository;
import com.ssafy.alphano.domain.member.repository.query.MemberStockQueryRepository;
import com.ssafy.alphano.util.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberQueryService {

    private final MemberLikeStockQueryRepository memberLikeStockQueryRepository;

    private final MemberRecentStockQueryRepository memberRecentStockQueryRepository;

    private final MemberStockQueryRepository memberStockQuery;

    private final MemberQueryRepository memberQueryRepository;

    private final TokenProvider tokenProvider;

    public ApiResult findMemberLikeStock(Long memberId) {

        return ApiData.of(memberLikeStockQueryRepository.findMemberLikeStock(memberId));
    }

    public ApiResult findMemberRecentStock(Long memberId) {
        return ApiData.of(memberRecentStockQueryRepository.findMemberRecentStock(memberId));
    }

    public ApiResult findMemberStock(Long memberId) {
        return ApiData.of(memberStockQuery.findMemberStock(memberId));
    }


    private boolean checkJwtToken(String authorization) {
        if (authorization == null || !authorization.startsWith("Bearer")) {
            return false;
        }
        String jwtToken = authorization.substring(7);

        return tokenProvider.validateToken(jwtToken);
    }

    public MemberPrivateInfoResponse findMemberPrivateInfo(Long memberId) {
        MemberPrivateInfoResponse memberPrivateInfo = memberQueryRepository.findMemberPrivateInfoByMemberId(memberId);
        if(memberPrivateInfo == null){
            ApiError.of(ErrorCode.ENTITY_NOT_FOUND);
        }
        return memberPrivateInfo;
    }
}
