package com.ssafy.alphano.domain.stock.entity;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class StockDate {
    @Id
    @Column(name = "stock_date_id", unique = true)
    String id;

    @Column(name = "is_mkt_open", nullable = false)
    private boolean isMktOpen;

    @Builder
    public StockDate(String id, boolean isMktOpen) {
        this.id = id;
        this.isMktOpen = isMktOpen;
    }
}
