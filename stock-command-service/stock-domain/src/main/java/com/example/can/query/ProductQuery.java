package com.example.can.query;

import com.example.can.valueobject.ProductId;
import lombok.Builder;

public interface ProductQuery {

    @Builder
    record ById(ProductId productId) implements ProductQuery {}
}
