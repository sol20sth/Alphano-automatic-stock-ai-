package com.ssafy.alphano.api.stock.service.response;

import com.ssafy.alphano.domain.stock.Enum.MktType;
import com.ssafy.alphano.domain.stock.entity.Stock;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StockResponse {

    Long id;
    String stockCode;
    String stockName;
    MktType mktType;
    int hit;

    @Builder
    public StockResponse(Long id, String stockCode, String stockName, MktType mktType, int hit) {
        this.id = id;
        this.stockCode = stockCode;
        this.stockName = stockName;
        this.mktType = mktType;
        this.hit = hit;
    }

    public static StockResponse of(Stock stock) {
        return StockResponse.builder()
              .id(stock.getId())
              .stockCode(stock.getStockCode())
              .stockName(stock.getStockName())
              .mktType(stock.getMktType())
              .hit(stock.getHit())
              .build();
    }

}
