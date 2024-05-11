package com.example.can.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateStockDto {
    private String stockName;
    private String stockCode;
    private Integer maximumSaleQuantity;
    private Integer minimumSaleQuantity;
    private Integer unitInStock;
    private Integer vatRate;
}
