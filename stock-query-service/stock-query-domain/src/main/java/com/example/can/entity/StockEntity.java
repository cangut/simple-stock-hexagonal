package com.example.can.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "stocks")
@Entity
public class StockEntity {
    @Id
    private UUID id;

    @OneToOne
    @JoinColumn(name = "PRODUCT_ID")
    private ProductEntity product;

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
