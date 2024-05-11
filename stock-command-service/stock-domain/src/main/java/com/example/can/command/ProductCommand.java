package com.example.can.command;

import com.example.can.valueobject.*;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

public interface ProductCommand {

    @Builder
    record Create(ProductId productId,
                  Brand brand,
                  CategoryId categoryId,
                  Money basePrice,
                  String productTitle,
                  CreateStock stock) implements ProductCommand {}

    @Builder
    record CreateStock(@Getter ProductId productId,
                       @Getter StockId stockId,
                       @Getter String stockName,
                       @Getter StockCode stockCode,
                       @Getter Integer maximumSaleQuantity,
                       @Getter Integer minimumSaleQuantity,
                       @Getter Integer unitInStock,
                       @Getter Integer vatRate) implements ProductCommand {}


    @Builder
    record IncreaseUnitInStock(ProductId productId,
                               Integer amount) implements ProductCommand {}

    @Builder
    record DecreaseUnitInStock(ProductId productId,
                               Integer amount) implements ProductCommand {}

    @Builder
    record Close(ProductId productId) implements ProductCommand {}

    @Builder
    record Replay() implements ProductCommand {}

    @Builder
    record ChangeStatus(ProductId productId,
                               ProductState state,
                               Instant createdAt) implements ProductCommand {}
}
