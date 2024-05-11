package com.example.can.query;

import lombok.Builder;

import java.util.UUID;

public interface ProductQuery {

    @Builder
    record ByProductId(UUID productId) implements ProductQuery {}
    @Builder
    record StockById(UUID stockId) implements ProductQuery {}
    @Builder
    record All() implements ProductQuery {}
}
