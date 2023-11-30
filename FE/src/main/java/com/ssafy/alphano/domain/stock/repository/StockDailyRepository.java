package com.ssafy.alphano.domain.stock.repository;

import com.ssafy.alphano.domain.stock.entity.StockDaily;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockDailyRepository extends JpaRepository<StockDaily, Long> {

    public Optional<StockDaily> findByStockIdAndStockDateId(Long stockId, String stockDateId);

//    public Optional<StockDaily> findByStockIdAndStockDateId(Long stockId, String stockDateId);

}
