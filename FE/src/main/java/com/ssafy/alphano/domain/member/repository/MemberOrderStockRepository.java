package com.ssafy.alphano.domain.member.repository;

import com.ssafy.alphano.domain.member.Enum.BuySell;
import com.ssafy.alphano.domain.member.entity.middle.MemberOrderStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberOrderStockRepository extends JpaRepository<MemberOrderStock, Long> {
    MemberOrderStock findByMemberIdAndStockId(Long memberId, Long stockId);

    MemberOrderStock findByMemberIdAndStockIdAndBuySell(Long memberId, Long stockId, BuySell buySell);

    MemberOrderStock findByMemberIdAndMemberOrderStockNickname(Long memberId, String memberOrderStockNickname);

    boolean existsByOrderNumber(String orderNumber);
}
