package com.ssafy.alphano.domain.member.repository.query;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.alphano.api.member.service.response.MemberLikeStockResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;
import static com.ssafy.alphano.domain.member.entity.middle.QMemberLikeStock.memberLikeStock;

@Repository
@RequiredArgsConstructor
public class MemberLikeStockQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<MemberLikeStockResponse> findMemberLikeStock(Long memberId) {
        return queryFactory
                .select(Projections.constructor(MemberLikeStockResponse.class,
                        memberLikeStock.stock.stockName.as("stockName"),
                        memberLikeStock.stock.stockCode.as("stockCode")
                ))
                .from(memberLikeStock)
                .where(memberLikeStock.member.id.eq(memberId))
                .fetch();
    }
}
