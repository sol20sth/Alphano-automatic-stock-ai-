package com.ssafy.alphano.domain.stock.repository.query;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.alphano.api.ai.service.response.StockAIReliabilityResponse;
import com.ssafy.alphano.api.stock.service.query.response.StockIndicatorResponse;
import com.ssafy.alphano.api.stock.service.response.StockAiServiceResponse;
import com.ssafy.alphano.api.stock.service.response.StockDailyResponse;
import com.ssafy.alphano.domain.stock.entity.QStockDaily;
import com.ssafy.alphano.domain.stock.entity.Stock;
import com.ssafy.alphano.domain.stock.entity.StockDaily;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static com.ssafy.alphano.domain.stock.entity.QStockDaily.stockDaily;

@Repository
@RequiredArgsConstructor
public class StockDailyQueryRepository {

    private final JPAQueryFactory queryFactory;

    /**
     * 주식 일봉 조회
     * 종목ID
     * 2023년 1월 1일 이후 데이터
     */

    public List<StockDailyResponse> findByStockDaily(String stockCode) {

        LocalDate fromDateTime = LocalDate.of(2023, 1, 1);

        return queryFactory
                .select(Projections.constructor(StockDailyResponse.class,
                        stockDaily.stockDate.stockDate.as("stockDate")
                ))
                .from(stockDaily)
                .where(
                        stockDaily.stock.stockCode.eq(stockCode)
                )
                .fetch();
    }

    /**
     * 전체 주식 AI 신뢰도 조회
     */
    public List<StockAiServiceResponse> findAllStockDaily() {

        return queryFactory
                .select(Projections.constructor(StockAiServiceResponse.class,
                        stockDaily.stock.stockName.as("stockName"),
                        stockDaily.stock.stockCode.as("stockCode"),
                        stockDaily.aiMaxReliability.as("aiMaxReliability"),
                        stockDaily.aiMinReliability.as("aiMinReliability"),
                        stockDaily.aiEndReliability.as("aiEndReliability"),
                        stockDaily.aiReliability.as("aiReliability"),
                        stockDaily.aiWeeklyReliability.as("aiWeeklyReliability")
                ))
                .from(stockDaily)
                .orderBy(stockDaily.stockDate.id.desc())
                .limit(50)
                .fetch();
    }

    public List<StockDailyResponse> findStockDailyByStockCode(String stockCode) {
        return queryFactory.select(Projections.constructor(StockDailyResponse.class,
                        stockDaily.startPrice,
                        stockDaily.endPrice,
                        stockDaily.minPrice,
                        stockDaily.maxPrice,
                        stockDaily.todayVolume,
                        stockDaily.aiMaxPrice,
                        stockDaily.aiMinPrice,
                        stockDaily.aiEndPrice,
                        stockDaily.stockDate.id
                ))
                .from(stockDaily)
                .where(stockDaily.stock.stockCode.eq(stockCode))
                .orderBy(stockDaily.stockDate.id.asc())
                .fetch();
    }

    public List<StockDailyResponse> findOptionByStockCode(String stockCode, int days) {
        return queryFactory.select(Projections.constructor(StockDailyResponse.class,
                        stockDaily.startPrice.as("startPrice"),
                        stockDaily.endPrice.as("endPrice"),
                        stockDaily.minPrice.as("minPrice"),
                        stockDaily.maxPrice.as("maxPrice"),
                        stockDaily.stockDate.id.as("stockDate")
                ))
                .from(stockDaily)
                .where(stockDaily.stock.stockCode.eq(stockCode))
                .orderBy(stockDaily.stockDate.id.desc())
                .limit(days)
                .fetch();
    }


    @Modifying
    public void updateOption(Stock stock, Double rsi, Double macd, String stockDate) {
        queryFactory.update(stockDaily)
                .set(stockDaily.rsi, rsi)
                .set(stockDaily.macd, macd)
                .where(stockDaily.stock.eq(stock), stockDaily.stockDate.id.eq(stockDate))
                .execute();
    }

    public List<StockIndicatorResponse> findAllStockIndicators() {
        return queryFactory.select(Projections.constructor(StockIndicatorResponse.class,
                        stockDaily.stock.stockCode.as("stockCode"),
                        stockDaily.stock.stockName.as("stockName"),
                        stockDaily.stockDate.id.as("stockDate"),
                        stockDaily.rsi.as("RSI"),
                        stockDaily.macd.as("MACD")
                ))
                .from(stockDaily)
                .orderBy(stockDaily.stockDate.id.desc())
                .limit(50)
                .fetch();
    }

    public StockIndicatorResponse findTodayStockIndicatorsByStockCode(String StockCode) {
        return queryFactory.select(Projections.constructor(StockIndicatorResponse.class,
                        stockDaily.stock.stockCode.as("stockCode"),
                        stockDaily.stock.stockName.as("stockName"),
                        stockDaily.stockDate.id.as("stockDate"),
                        stockDaily.rsi.as("RSI"),
                        stockDaily.macd.as("MACD")
                ))
                .from(stockDaily)
                .where(stockDaily.stock.stockCode.eq(StockCode))
                .orderBy(stockDaily.stockDate.id.desc())
                .fetchFirst();
    }

    @Modifying
    public void updateAiData(Long stockId, int maxPrice, int minPrice, int finalPrice, String stockDate) {
        queryFactory.update(stockDaily)
                .set(stockDaily.aiMaxPrice, maxPrice)
                .set(stockDaily.aiMinPrice, minPrice)
                .set(stockDaily.aiEndPrice, finalPrice)
                .where(stockDaily.stock.id.eq(stockId), stockDaily.stockDate.id.eq(stockDate))
                .execute();
    }

    public StockAIReliabilityResponse findDayBeforeYesterdayAIReliability(String stockDate, Long stockId) {

        return queryFactory.select(Projections.constructor(StockAIReliabilityResponse.class,
                        stockDaily.stock.id.as("stockId"),
                        stockDaily.aiEndReliability.as("aiEndReliability"),
                        stockDaily.aiMaxReliability.as("aiMaxReliability"),
                        stockDaily.aiMinReliability.as("aiMinReliability"),
                        stockDaily.aiReliability.as("aiReliability")
                ))
                .from(stockDaily)
                .where(stockDaily.stockDate.id.eq(stockDate), stockDaily.stock.id.eq(stockId))
                .fetchOne();
    }


    public int findStockDailyCountByStockId(Long stockId) {
        return (int) queryFactory.selectFrom(stockDaily)
                .where(stockDaily.stock.id.eq(stockId))
                .fetch().size();
    }

    public StockAIReliabilityResponse findYesterdayAIReliability(String stockDate, Long stockId) {

        StockAIReliabilityResponse stockAIReliabilityResponse = queryFactory.select(Projections.constructor(StockAIReliabilityResponse.class,
                        stockDaily.stock.id.as("stockId"),
                        stockDaily.endPrice.as("endPrice"),
                        stockDaily.aiEndPrice.as("aiEndPrice"),
                        stockDaily.maxPrice.as("maxPrice"),
                        stockDaily.aiMaxPrice.as("aiMaxPrice"),
                        stockDaily.minPrice.as("minPrice"),
                        stockDaily.aiMinPrice.as("aiMinPrice")
                ))
                .from(stockDaily)
                .where(stockDaily.stockDate.id.eq(stockDate), stockDaily.stock.id.eq(stockId))
                .fetchOne();

        float aiEndPriceReliability = (float) Math.abs(stockAIReliabilityResponse.getAiEndPrice() - stockAIReliabilityResponse.getEndPrice()) / stockAIReliabilityResponse.getEndPrice() * 100;
        float aiMaxPriceReliability = (float) Math.abs(stockAIReliabilityResponse.getAiMaxPrice() - stockAIReliabilityResponse.getMaxPrice()) / stockAIReliabilityResponse.getMaxPrice() * 100;
        float aiMinPriceReliability = (float) Math.abs(stockAIReliabilityResponse.getAiMinPrice() - stockAIReliabilityResponse.getMinPrice()) / stockAIReliabilityResponse.getMinPrice() * 100;

        stockAIReliabilityResponse.updateReliability(aiEndPriceReliability, aiMaxPriceReliability, aiMinPriceReliability);

        return stockAIReliabilityResponse;
    }

    @Modifying
    public void updateAiReliability(Long id, float aiEndReliability, float aiMinReliability, float aiMaxReliability, float aiReliability, String stockDate) {
        queryFactory.update(stockDaily)
                .set(stockDaily.aiEndReliability, aiEndReliability)
                .set(stockDaily.aiMinReliability, aiMinReliability)
                .set(stockDaily.aiMaxReliability, aiMaxReliability)
                .set(stockDaily.aiReliability, aiReliability)
                .where(stockDaily.stock.id.eq(id), stockDaily.stockDate.id.eq(stockDate))
                .execute();
    }

    @Modifying
    public void updateAiWeeklyReliability(Long id, float aiEndReliability, float aiMinReliability, float aiMaxReliability, float aiReliability, String stockDate) {

        List<StockDaily> stockDailyList = queryFactory.selectFrom(stockDaily)
                .where(stockDaily.stock.id.eq(id), stockDaily.stockDate.id.eq(stockDate))
                .limit(7)
                .orderBy(QStockDaily.stockDaily.stockDate.id.desc())
                .fetch();

        float weeklyAiReliability = (float) (stockDailyList.subList(1, 6).stream().mapToDouble(StockDaily::getAiEndPrice).sum() + stockDailyList.get(0).getAiReliability()) / 7;


        queryFactory.update(QStockDaily.stockDaily)
                .set(stockDaily.aiEndReliability, aiEndReliability)
                .set(stockDaily.aiMinReliability, aiMinReliability)
                .set(stockDaily.aiMaxReliability, aiMaxReliability)
                .set(stockDaily.aiReliability, aiReliability)
                .set(stockDaily.aiWeeklyReliability, weeklyAiReliability)
                .where(stockDaily.stock.id.eq(id), stockDaily.stockDate.id.eq(stockDate))
                .execute();

    }


}
