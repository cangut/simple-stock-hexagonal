package com.example.can.adapter.outbound;

import com.example.can.entity.ProductEntity;
import com.example.can.entity.StockEntity;
import com.example.can.event.ProductEvent;
import com.example.can.port.outbound.ProductEventHandler;
import com.example.can.port.outbound.ProductJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductEventHandlerImpl implements ProductEventHandler {
    private final ProductJpaRepository repository;

    @Transactional
    public void handle(ProductEvent.Created created) {
        var stockEntity = StockEntity.builder()
                .id(created.stock().stockId())
                .stockState(created.stock().stockState())
                .stockCode(created.stock().stockCode())
                .maximumSaleQuantity(created.stock().maximumSaleQuantity())
                .minimumSaleQuantity(created.stock().minimumSaleQuantity())
                .unitInStock(created.stock().unitInStock())
                .vatRate(created.stock().vatRate())
                .price(created.stock().price())
                .discountedPrice(created.stock().discountedPrice())
                .discount(created.stock().discount())
                .build();

        var productEntity = ProductEntity.builder()
                .id(created.productId())
                .categoryId(created.categoryId())
                .productState(created.productState())
                .productTitle(created.productTitle())
                .basePrice(created.basePrice())
                .stockId(created.stock().stockId())
                .stock(stockEntity)
                .build();

        stockEntity.setProduct(productEntity);

        repository.save(productEntity);
    }

    @Transactional
    public void handle(ProductEvent.UnitInStockIncreased unitInStockIncreased) {
        var product = repository.findById(unitInStockIncreased.productId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        StockEntity stock = product.getStock();
        var unitInStock = stock.getUnitInStock();
        stock.setUnitInStock(unitInStock + unitInStockIncreased.amount());

        repository.save(product);
    }

    @Transactional
    public void handle(ProductEvent.UnitInStockDecreased unitInStockDecreased) {
        var product = repository.findById(unitInStockDecreased.productId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        StockEntity stock = product.getStock();
        var unitInStock = stock.getUnitInStock();
        stock.setUnitInStock(unitInStock - unitInStockDecreased.amount());

        repository.save(product);
    }

    @Transactional
    public void handle(ProductEvent.Closed closed) {
        var product = repository.findById(closed.productId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setProductState(closed.productState());
        product.getStock().setStockState(closed.productState());

        repository.save(product);
    }
}
