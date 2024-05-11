package com.example.can.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class StockResponse extends QueryResponse {

    private UUID id;

    private UUID productId;

    private String stockState;

    private String stockCode;

    private Integer maximumSaleQuantity;

    private Integer minimumSaleQuantity;

    private Integer unitInStock;

    private Integer vatRate;

    private BigDecimal price;

    private BigDecimal discountedPrice;

    private BigDecimal discount;
}
