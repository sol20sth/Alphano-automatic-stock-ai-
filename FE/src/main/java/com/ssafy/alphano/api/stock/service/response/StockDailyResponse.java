package com.ssafy.alphano.api.stock.service.response;

import com.ssafy.alphano.domain.stock.entity.Stock;
import com.ssafy.alphano.domain.stock.entity.StockDaily;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

import static com.ssafy.alphano.domain.stock.entity.QStockDaily.stockDaily;

@Data
@NoArgsConstructor
public class StockDailyResponse {

    Long id;

    int maxPrice;

    int minPrice;

    int startPrice;

    int endPrice;

    int todayVolume;

    float aiReliability;

    int aiMaxPrice;

    int aiMinPrice;

    int aiEndPrice;

    LocalDateTime aiInputTime;

    LocalDateTime aiCompleteTime;

    boolean aiIsSuccess;

    Stock stock;

    String stockDate;

    @Builder
    public StockDailyResponse(Long id, int maxPrice, int minPrice, int startPrice, int endPrice, int todayVolume, float aiReliability, int aiMaxPrice, int aiMinPrice, int aiEndPrice, LocalDateTime aiInputTime, LocalDateTime aiCompleteTime, boolean aiIsSuccess, Stock stock, String stockDate) {
        this.id = id;
        this.maxPrice = maxPrice;
        this.minPrice = minPrice;
        this.startPrice = startPrice;
        this.endPrice = endPrice;
        this.todayVolume = todayVolume;
        this.aiReliability = aiReliability;
        this.aiMaxPrice = aiMaxPrice;
        this.aiMinPrice = aiMinPrice;
        this.aiEndPrice = aiEndPrice;
        this.aiInputTime = aiInputTime;
        this.aiCompleteTime = aiCompleteTime;
        this.aiIsSuccess = aiIsSuccess;
        this.stock = stock;
        this.stockDate = stockDate;
    }


    public StockDailyResponse(int startPrice, int endPrice, int minPrice, int maxPrice, String stockDate) {
        this.startPrice = startPrice;
        this.endPrice = endPrice;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.stockDate = stockDate;
    }

    public StockDailyResponse(int startPrice, int endPrice, int minPrice, int maxPrice, int todayVolume, int aiMaxPrice, int aiMinPrice, int aiEndPrice, String stockDate) {
        this.startPrice = startPrice;
        this.endPrice = endPrice;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.todayVolume = todayVolume;
        this.aiMaxPrice = aiMaxPrice;
        this.aiMinPrice = aiMinPrice;
        this.aiEndPrice = aiEndPrice;
        this.stockDate = stockDate;

    }


    public static StockDailyResponse of(StockDaily stockDaily) {
        return StockDailyResponse.builder()
                .id(stockDaily.getId())
                .maxPrice(stockDaily.getMaxPrice())
                .minPrice(stockDaily.getMinPrice())
                .startPrice(stockDaily.getStartPrice())
                .endPrice(stockDaily.getEndPrice())
                .todayVolume(stockDaily.getTodayVolume())
                .aiReliability(stockDaily.getAiReliability())
                .aiMaxPrice(stockDaily.getAiMaxPrice())
                .aiMinPrice(stockDaily.getAiMinPrice())
                .aiEndPrice(stockDaily.getAiEndPrice())
                .aiInputTime(stockDaily.getAiInputTime())
                .aiCompleteTime(stockDaily.getAiCompleteTime())
                .aiIsSuccess(stockDaily.isAiIsSuccess())
                .stock(stockDaily.getStock())
                .stockDate(stockDaily.getStockDate().toString())
                .build();
    }
}
