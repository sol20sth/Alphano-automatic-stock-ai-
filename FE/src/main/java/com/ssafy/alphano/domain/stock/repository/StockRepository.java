package com.ssafy.alphano.domain.stock.repository;

import com.ssafy.alphano.domain.stock.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    public Optional<Stock> findByStockCode(String stockCode);

    public Optional<Stock> findByStockName(String stockName);

    public Optional<Stock> findByMktType(String mktType);
}
