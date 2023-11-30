package com.ssafy.alphano.domain.stock.repository;

import com.ssafy.alphano.domain.stock.entity.StockDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface StockDateRepository extends JpaRepository<StockDate, Long> {

    Optional<StockDate> findById(String stockDateId);
}
