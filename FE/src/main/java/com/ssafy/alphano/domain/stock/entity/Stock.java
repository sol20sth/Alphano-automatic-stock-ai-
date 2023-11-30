package com.ssafy.alphano.domain.stock.entity;

import com.ssafy.alphano.domain.member.entity.middle.MemberLikeStock;
import com.ssafy.alphano.domain.member.entity.middle.MemberOrderStock;
import com.ssafy.alphano.domain.member.entity.middle.MemberRecentStock;
import com.ssafy.alphano.domain.member.entity.middle.MemberStock;
import com.ssafy.alphano.domain.stock.Enum.MktType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_id")
    Long id;

    @OneToMany(mappedBy = "stock", cascade = CascadeType.ALL, fetch = LAZY)
    private List<StockDaily>  stockDailies = new ArrayList<>();

    @OneToMany(mappedBy = "stock", cascade = CascadeType.ALL, fetch = LAZY)
    private List<StockWeekly>  stockWeeklies = new ArrayList<>();

    @OneToMany(mappedBy = "stock", cascade = CascadeType.ALL, fetch = LAZY)
    private List<MemberStock> memberStocks = new ArrayList<>();

    @OneToMany(mappedBy = "stock", cascade = CascadeType.ALL, fetch = LAZY)
    private List<MemberLikeStock> memberLikeStocks = new ArrayList<>();

    @OneToMany(mappedBy = "stock", cascade = CascadeType.ALL, fetch = LAZY)
    private List<MemberRecentStock> memberRecentStocks = new ArrayList<>();

    @OneToMany(mappedBy = "stock", cascade = CascadeType.ALL, fetch = LAZY)
    private List<MemberOrderStock> memberOrderStocks = new ArrayList<>();

    @Column(unique = true, nullable = false)
    String stockCode;

    @Column(nullable = false)
    String stockName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MktType mktType; // KOSPI, KOSDAQ

    @Column(columnDefinition = "int default 0", nullable = false)
    int hit;

    @Builder
    public Stock(Long id, String stockCode, String stockName, MktType mktType, int hit) {
        this.id = id;
        this.stockCode = stockCode;
        this.stockName = stockName;
        this.mktType = mktType;
        this.hit = hit;
    }
}
