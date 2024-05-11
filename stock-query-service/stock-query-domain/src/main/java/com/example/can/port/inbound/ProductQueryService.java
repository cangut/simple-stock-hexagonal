package com.example.can.port.inbound;

import com.example.can.query.ProductQuery;

public abstract class ProductQueryService<R> implements QueryDispatcher<R, ProductQuery> {
     public R dispatch(ProductQuery query) {
        return switch (query) {
            case ProductQuery.ByProductId q -> this.handle(q);
            case ProductQuery.StockById q -> this.handle(q);
            case ProductQuery.All q -> this.handle(q);
            default -> throw new IllegalStateException("Unexpected value: " + query);
        };
    }

    protected abstract R handle(ProductQuery.ByProductId byProductId);
    protected abstract R handle(ProductQuery.StockById stockById);
    protected abstract R handle(ProductQuery.All all);
}
