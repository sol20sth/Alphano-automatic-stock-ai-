package com.ssafy.alphano.domain.member.repository;

import com.ssafy.alphano.domain.member.entity.middle.MemberOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberOptionRepository extends JpaRepository<MemberOption, Long> {
    MemberOption findByStockOptionIdAndMemberOrderStockId(Long stockOptionId, Long memberOrderStockId);
}
