package com.ssafy.alphano.domain.stockrequiredoption.entity;

import com.ssafy.alphano.api.member.middle.controller.request.MemberOrderStockUpdateRequest;
import com.ssafy.alphano.domain.common.BaseEntity;
import com.ssafy.alphano.domain.member.entity.middle.MemberOrderStock;
import com.ssafy.alphano.domain.stockrequiredoption.Enum.TargetPriceCondition;
import com.ssafy.alphano.domain.stockrequiredoption.Enum.TargetPriceOptionInequality;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
public class StockRequiredOption extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_required_option_id")
    Long id;

    @Column(nullable = false)
    int targetPrice;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TargetPriceCondition targetPriceCondition; // T:지정가, M: 시장가

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TargetPriceOptionInequality targetPriceOptionInequality; // greater: 이상, less: 이하

    @Column(nullable = false)
    int targetOrderPrice;

    @OneToOne(fetch = LAZY)
    private MemberOrderStock memberOrderStock;

    @Builder
    public StockRequiredOption(Long id, int targetPrice, TargetPriceCondition targetPriceCondition, TargetPriceOptionInequality targetPriceOptionInequality, int targetOrderPrice, MemberOrderStock memberOrderStock) {
        this.id = id;
        this.targetPrice = targetPrice;
        this.targetPriceCondition = targetPriceCondition;
        this.targetPriceOptionInequality = targetPriceOptionInequality;
        this.targetOrderPrice = targetOrderPrice;
        this.memberOrderStock = memberOrderStock;
    }

    public void updateStockRequiredOption(MemberOrderStockUpdateRequest request) {
        this.targetPrice = request.getTargetPrice();
        this.targetPriceCondition = request.getTargetPriceCondition();
        this.targetPriceOptionInequality = request.getTargetPriceOptionInequality();
        this.targetOrderPrice = request.getTargetOrderPrice();
    }
}
