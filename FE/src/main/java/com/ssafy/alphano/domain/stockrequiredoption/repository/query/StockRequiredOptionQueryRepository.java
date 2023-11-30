package com.ssafy.alphano.domain.stockrequiredoption.repository.query;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.alphano.api.stockrequiredoption.service.response.StockRequiredOptionServiceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.ssafy.alphano.domain.stockrequiredoption.entity.QStockRequiredOption.stockRequiredOption;

@Repository
@RequiredArgsConstructor
public class StockRequiredOptionQueryRepository {

    private final JPAQueryFactory queryfactory;

    public List<StockRequiredOptionServiceResponse> findStockRequiredOption(Long memberOrderStockId) {
        return queryfactory
                .select(Projections.constructor(StockRequiredOptionServiceResponse.class,
                        stockRequiredOption.memberOrderStock.id.as("memberOrderStockId")
                ))
                .from(stockRequiredOption)
                .where(stockRequiredOption.memberOrderStock.id.eq(memberOrderStockId))
                .fetch();
    }
}
