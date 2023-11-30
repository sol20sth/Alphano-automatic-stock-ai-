package com.ssafy.alphano.domain.member.repository.query;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.alphano.api.member.middle.service.response.MemberOptionServiceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.ssafy.alphano.domain.member.entity.middle.QMemberOption.memberOption;

@Repository
@RequiredArgsConstructor
public class MemberOptionQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<MemberOptionServiceResponse> findMemberOption(Long memberOrderStockId) {
        return queryFactory
              .select(Projections.constructor(MemberOptionServiceResponse.class,
                        memberOption.stockOption.optionName.as("optionName")
                ))
              .from(memberOption)
              .where(memberOption.memberOrderStock.id.eq(memberOrderStockId))
              .fetch();
    }

//    public MemberOptionServiceResponse findByOptionIdAndMemberOrderStockId(Long optionId, Long memberOrderStockId) {
//        return queryFactory
//                .select(Projections.constructor(MemberOptionServiceResponse.class,
//                        memberOption.stockOption.optionName.as("optionName")
//                ))
//                .from(memberOption)
//                .where(
//                        memberOption.stockOption.id.eq(optionId),
//                        memberOption.memberOrderStock.id.eq(memberOrderStockId)
//                )
//                .fetchOne();
//    }
}

