package com.ssafy.alphano.domain.stock.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
public class StockWeekly {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_daily_id")
    Long id;

    @Column(nullable = false)
    int nowPrice;

    @Column(nullable = false)
    int maxPrice;

    @Column(nullable = false)
    int minPrice;

    @Column(nullable = false)
    int startPrice;

    @Column(nullable = false)
    int endPrice;

    @Column
    float aiReliability;

    @Column
    int aiMaxPrice;

    @Column
    int aiMinPrice;

    @Column
    int aiEndPrice;

    @Column
    private LocalDateTime aiInputTime;

    @Column
    private LocalDateTime aiCompleteTime;

    @Column
    private boolean aiIsSuccess;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "stock_id", nullable = false, updatable = false)
    private Stock stock;

    @OneToOne(fetch = LAZY)
    @PrimaryKeyJoinColumn
    private StockDate stockDate;

    @Builder

    public StockWeekly(Long id, int nowPrice, int maxPrice, int minPrice,
                       int startPrice, int endPrice, float aiReliability,
                       int aiMaxPrice, int aiMinPrice, int aiEndPrice,
                       LocalDateTime aiInputTime, LocalDateTime aiCompleteTime,
                       boolean aiIsSuccess) {
        this.id = id;
        this.nowPrice = nowPrice;
        this.maxPrice = maxPrice;
        this.minPrice = minPrice;
        this.startPrice = startPrice;
        this.endPrice = endPrice;
        this.aiReliability = aiReliability;
        this.aiMaxPrice = aiMaxPrice;
        this.aiMinPrice = aiMinPrice;
        this.aiEndPrice = aiEndPrice;
        this.aiInputTime = aiInputTime;
        this.aiCompleteTime = aiCompleteTime;
        this.aiIsSuccess = aiIsSuccess;
    }
}
