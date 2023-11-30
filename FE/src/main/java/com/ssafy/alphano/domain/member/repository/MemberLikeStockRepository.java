package com.ssafy.alphano.domain.member.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.alphano.domain.member.entity.middle.MemberLikeStock;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberLikeStockRepository extends JpaRepository<MemberLikeStock, Long> {
    MemberLikeStock findByMemberIdAndStockId(Long memberId, Long stockId);
}
