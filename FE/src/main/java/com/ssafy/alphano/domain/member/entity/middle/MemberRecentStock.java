package com.ssafy.alphano.domain.member.entity.middle;

import com.ssafy.alphano.domain.common.BaseEntity;
import com.ssafy.alphano.domain.member.entity.Member;
import com.ssafy.alphano.domain.stock.entity.Stock;
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
public class MemberRecentStock extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_recent_stock_id")
    Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "stock_id", nullable = false)
    private Stock stock;

    @Builder
    public MemberRecentStock(Long id, Member member, Stock stock) {
        this.id = id;
        this.member = member;
        this.stock = stock;
    }
}
