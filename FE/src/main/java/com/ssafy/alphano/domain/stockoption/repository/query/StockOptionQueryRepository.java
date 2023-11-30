package com.ssafy.alphano.domain.stockoption.repository.query;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.alphano.api.stockoption.service.response.StockOptionNameServiceResponse;
import com.ssafy.alphano.api.stockoption.service.response.StockOptionServiceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.ssafy.alphano.domain.stockoption.entity.QStockOption.stockOption;

@Repository
@RequiredArgsConstructor
public class StockOptionQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<StockOptionServiceResponse> findStockOption(String optionName) {
        return queryFactory
                .select(Projections.constructor(StockOptionServiceResponse.class,
                        stockOption.optionName.as("optionName")
                ))
                .from(stockOption)
                .where(stockOption.optionName.eq(optionName))
                .fetch();
    }

    public List<StockOptionNameServiceResponse> findAllStockOption() {
        return queryFactory
                .select(Projections.constructor(StockOptionNameServiceResponse.class,
                        stockOption.optionName.as("optionName")))
                .from(stockOption)
                .fetch();
    }
}
