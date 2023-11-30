package com.ssafy.alphano.domain.member.repository;

import com.ssafy.alphano.domain.member.entity.middle.MemberStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberStockRepository extends JpaRepository<MemberStock, Long> {

    MemberStock findByMemberIdAndStockId(Long memberId, Long stockId);
}
