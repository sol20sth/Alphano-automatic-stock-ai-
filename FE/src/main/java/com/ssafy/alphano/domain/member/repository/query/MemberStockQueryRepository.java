package com.ssafy.alphano.domain.member.repository.query;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.alphano.api.member.service.response.MemberStockResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.ssafy.alphano.domain.member.entity.middle.QMemberStock.memberStock;

@Repository
@RequiredArgsConstructor
public class MemberStockQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<MemberStockResponse> findMemberStock(Long memberId) {
        return queryFactory
                .select(Projections.constructor(MemberStockResponse.class,
                        memberStock.stock.stockName.as("stockName"),
                        memberStock.purchasePrice.as("purchasePrice"),
                        memberStock.purchaseCount.as("purchaseCount"),
                        memberStock.purchaseTime.as("purchaseTime")
                ))
                .from(memberStock)
                .where(memberStock.member.id.eq(memberId))
                .fetch();
    }
}
