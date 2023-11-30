package com.ssafy.alphano.domain.member.repository.query;


import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;


import com.ssafy.alphano.api.member.service.response.ApprovalKeyResponse;

import com.ssafy.alphano.api.member.service.response.MemberPrivateInfoResponse;
import com.ssafy.alphano.api.member.service.response.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
;

import java.util.List;

import static com.ssafy.alphano.domain.member.entity.QApprovalKey.approvalKey1;
import static com.ssafy.alphano.domain.member.entity.QMember.member;


@Repository
@RequiredArgsConstructor
public class MemberQueryRepository {

    private final JPAQueryFactory queryFactory;

    public ApprovalKeyResponse findMemberApprovalKey(String username) {

        return queryFactory
                .select(Projections.constructor(
                        ApprovalKeyResponse.class,
                        approvalKey1.member.username.as("username"),
                        approvalKey1.approvalKey.as("approvalKey"),
                        approvalKey1.updatedTime.as("updatedTime")
                ))
                .from(approvalKey1)
                .where(approvalKey1.member.username.eq(username))
                .fetchOne();

    }

    public MemberPrivateInfoResponse findMemberPrivateInfoByMemberId(Long memberId) {


        return queryFactory
                .select(Projections.constructor(
                        MemberPrivateInfoResponse.class,
                        member.accessToken.as("authorization"),
                        member.approvalKey.as("approvalKey"),
                        member.appKey.as("appkey"),
                        member.appsecretKey.as("appsecret"),
                        member.account.as("CANO"),
                        member.accountCode.as("ACNT_PRDT_CD")
                ))
                .from(member)
                .where(member.id.eq(memberId))
                .fetchOne();
    }


    public List<MemberResponse> findAllMember() {
        return queryFactory.select(Projections.constructor(
                MemberResponse.class,
                member.id.as("id"),
                member.username.as("username"),
                member.approvalKey.as("approvalKey")
        )).from(member).fetch();
    }
}
