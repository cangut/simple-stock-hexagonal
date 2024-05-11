package com.example.can.event;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

public interface StockEvent {
    @Builder
    record Created(UUID productId,
                   UUID stockId,
                   String stockName,
                   String stockCode,
                   String stockState,
                   Integer maximumSaleQuantity,
                   Integer minimumSaleQuantity,
                   Integer unitInStock,
                   Integer vatRate,
                   BigDecimal price,
                   BigDecimal discountedPrice,
                   BigDecimal discount) {}
}
