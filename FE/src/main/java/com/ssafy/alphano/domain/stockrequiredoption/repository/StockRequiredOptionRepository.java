package com.ssafy.alphano.domain.stockrequiredoption.repository;

import com.ssafy.alphano.domain.stockrequiredoption.entity.StockRequiredOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRequiredOptionRepository extends JpaRepository<StockRequiredOption, Long> {
    StockRequiredOption findByTargetOrderPrice(int targetOrderPrice);

    StockRequiredOption findByMemberOrderStockId(Long memberOrderStockId);
}
