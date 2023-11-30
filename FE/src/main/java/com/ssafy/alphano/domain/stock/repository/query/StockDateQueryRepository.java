package com.ssafy.alphano.domain.stock.repository.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

import static com.ssafy.alphano.domain.stock.entity.QStockDate.stockDate;


@Repository
@RequiredArgsConstructor
public class StockDateQueryRepository {

    private final JPAQueryFactory queryFactory;

    public boolean checkMktOpen(String requestDate) {
        return queryFactory
                .selectOne()
                .from(stockDate)
                .where(
                        stockDate.id.eq(requestDate),
                        stockDate.isMktOpen.eq(true)
                )
                .fetchFirst() != null;
    }

}
