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
@Table(name = "products")
@Entity
public class ProductEntity {
    @Id
    private UUID id;
    private String categoryId;
    private String productState;
    private String productTitle;
    private BigDecimal basePrice;
    private UUID stockId;
    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
    private StockEntity stock;

}
