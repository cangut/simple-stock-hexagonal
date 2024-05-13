package com.example.can.aggregate;

import com.example.can.command.ProductCommand;
import com.example.can.entity.Stock;
import com.example.can.event.ProductEvent;
import com.example.can.event.StockEvent;
import com.example.can.exception.ProductDomainException;
import com.example.can.valueobject.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
/*
-single entity or cluster of entities or value object.
-loaded and saved as a whole and as a single unit -> atomicity and consistency
-optimistic concurrency control
 */

@Builder
@AllArgsConstructor
@Getter
public class Product extends AggregateRoot<ProductId> {
    private Brand brand;
    private CategoryId category;
    private ProductState productState;
    private String productTitle;
    private Money basePrice;
    private Stock stock;

    public Product(ProductCommand.Create createCommand) {
        Money salePrice = Money.calculateVatRateIncludedPrice(createCommand.stock().getVatRate(), createCommand.basePrice());

        var productCreated = ProductEvent.Created.builder()
                .productId(createCommand.productId().getValue())
                .brand(createCommand.brand().name())
                .categoryId(createCommand.categoryId().getValue())
                .productTitle(createCommand.productTitle())
                .productState(ProductState.IN_APPROVAL.name())
                .basePrice(createCommand.basePrice().amount())
                .stock(StockEvent.Created.builder()
                        .productId(createCommand.productId().getValue())
                        .stockId(createCommand.stock().stockId().getValue())
                        .stockCode(createCommand.stock().getStockCode().value())
                        .stockState(ProductState.IN_APPROVAL.name())
                        .stockName(createCommand.stock().getStockName())
                        .unitInStock(createCommand.stock().getUnitInStock())
                        .maximumSaleQuantity(createCommand.stock().maximumSaleQuantity())
                        .minimumSaleQuantity(createCommand.stock().minimumSaleQuantity())
                        .unitInStock(createCommand.stock().unitInStock())
                        .vatRate(createCommand.stock().getVatRate())
                        .price(salePrice.amount())
                        .discountedPrice(Money.ZERO.amount())
                        .discount(Money.ZERO.amount())
                        .build())
                .createdAt(Instant.now())
                .build();

        raiseEvent(productCreated);
    }

    private void apply(ProductEvent.Created createdEvent) {
        this.setId(new ProductId(createdEvent.productId()));
        this.brand = Brand.valueOf(createdEvent.brand());
        this.category = new CategoryId(createdEvent.categoryId());
        this.productTitle = createdEvent.productTitle();
        this.productState = ProductState.valueOf(createdEvent.productState());
        this.basePrice = new Money(createdEvent.basePrice());
        this.stock = new Stock(createdEvent.stock());
    }

    public void increaseUnitInStock(ProductCommand.IncreaseUnitInStock increaseStock) {
        if (!this.productState.equals(ProductState.ACTIVE) && !this.productState.equals(ProductState.IN_APPROVAL)) {
            throw new ProductDomainException.ProductStateIsNotActive("Product state must be active to do this operation");
        }
        if (increaseStock.amount() <= 0) {
            throw new ProductDomainException.AmountMustBeGreaterThanZero("Amount must be greater than zero");
        }

        var unitInStockIncreased = ProductEvent.UnitInStockIncreased.builder()
                .productId(increaseStock.productId().getValue())
                .amount(increaseStock.amount())
                .createdAt(Instant.now())
                .build();

        raiseEvent(unitInStockIncreased);
    }

    private void apply(ProductEvent.UnitInStockIncreased unitInStockIncreased) {
        this.stock.increaseUnitInStock(unitInStockIncreased.amount());
    }

    public void decreaseUnitInStock(ProductCommand.DecreaseUnitInStock decreaseUnitInStock) {
        if (!this.productState.equals(ProductState.ACTIVE) && !this.productState.equals(ProductState.IN_APPROVAL)) {
            throw new ProductDomainException.ProductStateIsNotActive("Product state must be active to do this operation");
        }
        if (decreaseUnitInStock.amount() <= 0) {
            throw new ProductDomainException.AmountMustBeGreaterThanZero("Amount must be greater than zero");
        }

        var unitInStockIncreased = ProductEvent.UnitInStockDecreased.builder()
                .productId(decreaseUnitInStock.productId().getValue())
                .amount(decreaseUnitInStock.amount())
                .createdAt(Instant.now())
                .build();

        raiseEvent(unitInStockIncreased);
    }

    private void apply(ProductEvent.UnitInStockDecreased unitInStockDecreased) {
        this.stock.decreaseUnitInStock(unitInStockDecreased.amount());
    }

    public void close(ProductCommand.Close close) {
        var closed = ProductEvent.Closed.builder()
                .productId(close.productId().getValue())
                .productState(ProductState.INACTIVE.name())
                .build();

        raiseEvent(closed);
    }

    private void apply(ProductEvent.Closed closed) {
        this.productState = ProductState.valueOf(closed.productState());
        if (this.stock != null) {
            this.stock.close();
        }
    }

    public void changeStatus(ProductCommand.ChangeStatus changeStatus) {
        var statusChanged = ProductEvent.StatusChanged.builder()
                .productId(changeStatus.productId().getValue())
                .productState(changeStatus.state().name())
                .createdAt(changeStatus.createdAt())
                .build();

        raiseEvent(statusChanged);
    }

    private void apply(ProductEvent.StatusChanged changed) {
        this.productState = ProductState.valueOf(changed.productState());
        if (this.stock != null) {
            this.stock.changeStatus(StockState.valueOf(changed.productState()));
        }
    }
}
