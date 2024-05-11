package com.example.can.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class ProductResponse extends QueryResponse {
    private UUID id;
    private String categoryId;
    private String productState;
    private String productTitle;
    private BigDecimal basePrice;
    private UUID stockId;

}
