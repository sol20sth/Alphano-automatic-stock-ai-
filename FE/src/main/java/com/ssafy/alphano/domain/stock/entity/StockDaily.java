package com.ssafy.alphano.domain.stock.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class StockDaily {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_daily_id")
    Long id;

    @Column(nullable = false)
    int maxPrice;

    @Column(nullable = false)
    int minPrice;

    @Column(nullable = false)
    int startPrice;

    @Column(nullable = false)
    int endPrice;

    @Column
    Integer todayVolume;

    @Column
    Float aiReliability; // 전체 기간 신뢰도

    @Column
    Float aiMaxReliability;

    @Column
    Float aiMinReliability;

    @Column
    Float aiEndReliability;

    @Column
    Float aiWeeklyReliability; // 최근 1주일 간의 신뢰도

    @Column
    int aiMaxPrice;

    @Column
    int aiMinPrice;

    @Column
    int aiEndPrice;

    @Column
    LocalDateTime aiInputTime;

    @Column
    LocalDateTime aiCompleteTime;

    @Column
    boolean aiIsSuccess;

    @Column
    Double macd;

    @Column
    Double rsi;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "stock_id", nullable = false, updatable = false)
    Stock stock;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "stock_date_id", nullable = false, updatable = false)
    StockDate stockDate;

    @Builder
    public StockDaily(Long id, int maxPrice, int minPrice, int startPrice, int endPrice, Integer todayVolume, float aiReliability, float aiMaxReliability, float aiMinReliability, float aiEndReliability, float aiWeeklyReliability, int aiMaxPrice, int aiMinPrice, int aiEndPrice, LocalDateTime aiInputTime, LocalDateTime aiCompleteTime, boolean aiIsSuccess, double macd, double rsi, Stock stock, StockDate stockDate) {
        this.id = id;
        this.maxPrice = maxPrice;
        this.minPrice = minPrice;
        this.startPrice = startPrice;
        this.endPrice = endPrice;
        this.todayVolume = todayVolume;
        this.aiReliability = aiReliability;
        this.aiMaxReliability = aiMaxReliability;
        this.aiMinReliability = aiMinReliability;
        this.aiEndReliability = aiEndReliability;
        this.aiWeeklyReliability = aiWeeklyReliability;
        this.aiMaxPrice = aiMaxPrice;
        this.aiMinPrice = aiMinPrice;
        this.aiEndPrice = aiEndPrice;
        this.aiInputTime = aiInputTime;
        this.aiCompleteTime = aiCompleteTime;
        this.aiIsSuccess = aiIsSuccess;
        this.macd = macd;
        this.rsi = rsi;
        this.stock = stock;
        this.stockDate = stockDate;
    }
}
