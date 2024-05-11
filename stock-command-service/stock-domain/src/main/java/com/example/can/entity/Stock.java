package com.example.can.entity;

import com.example.can.event.StockEvent;
import com.example.can.valueobject.*;
import lombok.Getter;

import java.util.UUID;

@Getter
public class Stock extends BaseEntity<StockId> {
    private ProductId product;
    private String stockName;
    private StockState stockState;
    private StockCode stockCode;
    private Integer maximumSaleQuantity;
    private Integer minimumSaleQuantity;
    private Integer unitInStock;
    private Integer vatRate;
    private Money price;
    private Money discountedPrice;
    private Money discount;




    public Stock (StockEvent.Created created) {
        this.product = new ProductId(created.productId());
        this.setId(new StockId(UUID.randomUUID()));
        this.stockName = created.stockName();
        this.stockState = StockState.valueOf(created.stockState());
        this.stockCode = new StockCode(created.stockCode());
        this.maximumSaleQuantity = created.maximumSaleQuantity();
        this.minimumSaleQuantity = created.minimumSaleQuantity();
        this.unitInStock = created.unitInStock();
        this.vatRate = created.vatRate();
        this.price = new Money(created.price());
        this.discountedPrice = new Money(created.discountedPrice());
        this.discount = new Money(created.discount());
    }


    public void increaseUnitInStock(Integer amount) {
        this.unitInStock += amount;
    }

    public void decreaseUnitInStock(Integer amount) {
        this.unitInStock -= amount;
    }

    public void close() {
        this.stockState = StockState.INACTIVE;
    }

    public void changeStatus(StockState newState){
        this.stockState = newState;
    }
}
