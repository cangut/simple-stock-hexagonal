package com.example.can.event;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public interface ProductEvent extends DomainEvent {
    @Builder
    record Created(UUID productId,
                   String brand,
                   String categoryId,
                   String productTitle,
                   String productState,
                   BigDecimal basePrice,
                   Instant createdAt,
                   StockEvent.Created stock) implements ProductEvent {
    }

    @Builder
    record UnitInStockIncreased(UUID productId,
                                Integer amount,
                                Instant createdAt) implements ProductEvent {
    }

    @Builder
    record UnitInStockDecreased(UUID productId,
                                Integer amount,
                                Instant createdAt) implements ProductEvent {
    }

    @Builder
    record Closed(UUID productId,
                  String productState,
                  Instant createdAt) implements ProductEvent {
    }

    @Builder
    record StatusChanged(UUID productId,
                         String productState,
                         Instant createdAt) implements ProductEvent {
    }

}
