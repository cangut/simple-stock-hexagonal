package com.example.can.response;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

public interface ProductResponse {

    @Builder
    record Created(UUID productId) implements ProductResponse {}

    @Builder
    record UnitInStockIncreased(UUID productId) implements ProductResponse {}

    @Builder
    record UnitInStockDecreased(UUID productId) implements ProductResponse {}

    @Builder
    record ProductClosed(UUID productId) implements ProductResponse {}

    @Builder
    record ProductStatusChanged(UUID productId) implements ProductResponse {}

    @Builder
    record Product(UUID productId,
                   String brand,
                   String categoryId,
                   String productState,
                   String productTitle,
                   BigDecimal basePrice,
                   Stock stock) implements ProductResponse {}

    @Builder
    record Stock(UUID productId,
                 UUID stockId,
                 String stockName,
                 String stockState,
                 String stockCode,
                 Integer maximumSaleQuantity,
                 Integer minimumSaleQuantity,
                 Integer unitInStock,
                 Integer vatRate,
                 BigDecimal price,
                 BigDecimal discountedPrice,
                 BigDecimal discount) implements ProductResponse {}
}
