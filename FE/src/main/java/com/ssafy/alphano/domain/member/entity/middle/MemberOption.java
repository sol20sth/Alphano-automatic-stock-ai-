package com.ssafy.alphano.domain.member.entity.middle;

import com.ssafy.alphano.api.member.middle.service.dto.UpdateMemberOptionDto;
import com.ssafy.alphano.domain.member.Enum.OptionInequality;
import com.ssafy.alphano.domain.stockoption.entity.StockOption;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class MemberOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_option_id", columnDefinition = "int")
    Long id;

    @Column
    float optionValue;

    @Column
    @Enumerated(EnumType.STRING)
    private OptionInequality optionInequality; // GREATER, LESS

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "stock_option_id")
    private StockOption stockOption;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_order_stock_id", nullable = false)
    private MemberOrderStock memberOrderStock;

    @Builder
    public MemberOption(Long id, float optionValue, OptionInequality optionInequality, StockOption stockOption, MemberOrderStock memberOrderStock) {
        this.id = id;
        this.optionValue = optionValue;
        this.optionInequality = optionInequality;
        this.stockOption = stockOption;
        this.memberOrderStock = memberOrderStock;
    }

    public void updateMemberOption(UpdateMemberOptionDto memberOption) {
        this.optionValue = memberOption.getOptionValue();
        this.optionInequality = memberOption.getOptionInequality();
    }
}
