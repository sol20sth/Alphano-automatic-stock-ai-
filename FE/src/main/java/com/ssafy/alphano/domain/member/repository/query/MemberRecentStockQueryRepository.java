package com.ssafy.alphano.domain.member.repository.query;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.alphano.api.member.service.response.MemberRecentStockResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.ssafy.alphano.domain.member.entity.middle.QMemberRecentStock.memberRecentStock;

@Repository
@RequiredArgsConstructor
public class MemberRecentStockQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<MemberRecentStockResponse> findMemberRecentStock(Long memberId) {
        return queryFactory
              .select(Projections.constructor(MemberRecentStockResponse.class,
                        memberRecentStock.stock.stockName.as("stockName"),
                      memberRecentStock.stock.stockCode.as("stockCode")
              ))
              .from(memberRecentStock)
              .where(memberRecentStock.member.id.eq(memberId))
              .orderBy(memberRecentStock.updatedTime.desc())
              .limit(5)
              .fetch();
    }

}
