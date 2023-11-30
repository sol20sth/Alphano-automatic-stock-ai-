package com.ssafy.alphano.domain.stock.repository.query;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.alphano.api.stock.service.response.StockServiceResponse;
import com.ssafy.alphano.api.stock.service.response.StockResponse;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import java.util.List;
import static com.ssafy.alphano.domain.stock.entity.QStock.stock;

@Repository
public class StockQueryRepository {

    private final JPAQueryFactory queryFactory;

    public StockQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    /**
     * 종목 정보 조회
     * 종목 코드로
     */
    public StockResponse findByStockCode(String stockCode) {
        return queryFactory
                .select(Projections.constructor(StockResponse.class,
                        stock.id,
                        stock.stockCode,
                        stock.stockName,
                        stock.mktType,
                        stock.hit
                ))
                .from(stock)
                .where(stock.stockCode.eq(stockCode))
                .fetchOne();
    }

    public StockResponse findByStockName(String stockName) {
        return queryFactory
                .select(Projections.constructor(StockResponse.class,
                        stock.id,
                        stock.stockCode,
                        stock.stockName,
                        stock.mktType,
                        stock.hit
                ))
                .from(stock)
                .where(stock.stockName.eq(stockName))
                .fetchOne();
    }

    public List<StockResponse> findStockByKeyword(String searchKeyword){
        return queryFactory
                .select(Projections.constructor(StockResponse.class,
                        stock.id,
                        stock.stockCode,
                        stock.stockName,
                        stock.mktType,
                        stock.hit
                ))
                .from(stock)
                .where(stock.stockName.startsWith(searchKeyword))
                .fetch();
    }

    public List<StockServiceResponse> findAllStock() {

        return queryFactory.select(Projections.constructor(StockServiceResponse.class,
                stock.id.as("id"),
                stock.stockName.as("stockName"),
                stock.stockCode.as("stockCode")
        ))
                .from(stock)
                .fetch();
    }

    public List<StockResponse> findAllStockCode() {
        return queryFactory
                .select(Projections.constructor(StockResponse.class,
                        stock.id,
                        stock.stockCode,
                        stock.stockName,
                        stock.mktType,
                        stock.hit
                ))
                .from(stock)
                .fetch();
    }


}
