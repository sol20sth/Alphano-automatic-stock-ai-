package com.ssafy.alphano.domain.stockoption.repository;

import com.ssafy.alphano.domain.stockoption.entity.StockOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockOptionRepository extends JpaRepository<StockOption, Long> {
    public Optional<StockOption> findByOptionName(String optionName);

    public boolean existsByOptionName(String optionName);
}
