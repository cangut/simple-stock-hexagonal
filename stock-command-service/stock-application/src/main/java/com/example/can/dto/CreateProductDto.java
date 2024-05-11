package com.example.can.dto;

import lombok.Data;

import java.math.BigDecimal;


@Data
public class CreateProductDto {
    private String categoryId;
    private String brand;
    private BigDecimal basePrice;
    private String productTitle;
    private CreateStockDto stock;
}
