package com.ssafy.alphano.domain.common.repository;

import com.ssafy.alphano.domain.common.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends JpaRepository<Log, Long> {
    Log findByMemberIdAndStockId(Long memberId, Long stockId);
}
