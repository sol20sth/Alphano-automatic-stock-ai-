package com.ssafy.alphano.domain.member.repository;

import com.ssafy.alphano.domain.member.entity.middle.MemberRecentStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRecentStockRepository extends JpaRepository<MemberRecentStock, Long> {
    MemberRecentStock findByMemberIdAndStockId(Long memberId, Long stockId);
}
