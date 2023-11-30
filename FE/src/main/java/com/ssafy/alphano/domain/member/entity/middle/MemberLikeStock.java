package com.ssafy.alphano.domain.member.entity.middle;

import com.ssafy.alphano.domain.common.BaseEntity;
import com.ssafy.alphano.domain.member.entity.Member;
import com.ssafy.alphano.domain.stock.entity.Stock;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@NoArgsConstructor
@Getter
public class MemberLikeStock extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_like_stock_id", columnDefinition = "int")
    Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "stock_id", nullable = false)
    private Stock stock;

    @Builder
    public MemberLikeStock(Long id, Member member, Stock stock) {
        this.id = id;
        this.member = member;
        this.stock = stock;
    }
}
