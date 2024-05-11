package com.example.can.adapter;

import com.example.can.aggregate.Product;
import com.example.can.command.ProductCommand;
import com.example.can.ports.inbound.ProductApplicationService;
import com.example.can.ports.outbound.EventStoreService;
import com.example.can.query.ProductQuery;
import com.example.can.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductApplicationServiceImpl extends ProductApplicationService<ProductResponse> {

    private final EventStoreService eventStore;

    @Override
    @Transactional
    protected ProductResponse handle(ProductCommand.Create create) {
        var product = new Product(create);
        eventStore.saveEvents(product);
        return new ProductResponse.Created(product.getId().getValue());
    }

    @Override
    @Transactional
    protected ProductResponse handle(ProductCommand.IncreaseUnitInStock increaseUnitInStock) {
        var product = eventStore.getProductById(increaseUnitInStock.productId().getValue().toString());
        product.increaseUnitInStock(increaseUnitInStock);
        eventStore.saveEvents(product);
        return new ProductResponse.UnitInStockIncreased(product.getId().getValue());
    }

    @Override
    @Transactional
    protected ProductResponse handle(ProductCommand.DecreaseUnitInStock decreaseUnitInStock) {
        var product = eventStore.getProductById(decreaseUnitInStock.productId().getValue().toString());
        product.decreaseUnitInStock(decreaseUnitInStock);
        eventStore.saveEvents(product);
        return new ProductResponse.UnitInStockDecreased(product.getId().getValue());
    }

    @Override
    @Transactional
    protected ProductResponse handle(ProductCommand.Close close) {
        var product = eventStore.getProductById(close.productId().getValue().toString());
        product.close(close);
        eventStore.saveEvents(product);
        return new ProductResponse.ProductClosed(product.getId().getValue());
    }

    @Override
    @Transactional(readOnly = true)
    protected ProductResponse handle(ProductQuery.ById query) {
        var product = eventStore.getProductById(query.productId().getValue().toString());
        return ProductResponse.Product.builder()
                .productId(product.getId().getValue())
                .productTitle(product.getProductTitle())
                .productState(product.getProductState().name())
                .brand(product.getBrand().name())
                .basePrice(product.getBasePrice().amount())
                .categoryId(product.getCategory().getValue())
                .stock(ProductResponse.Stock.builder()
                        .stockId(product.getStock().getId().getValue())
                        .productId(product.getId().getValue())
                        .price(product.getStock().getPrice().amount())
                        .stockName(product.getStock().getStockName())
                        .stockCode(product.getStock().getStockCode().value())
                        .stockState(product.getStock().getStockState().name())
                        .discount(product.getStock().getDiscount().amount())
                        .unitInStock(product.getStock().getUnitInStock())
                        .discountedPrice(product.getStock().getDiscountedPrice().amount())
                        .maximumSaleQuantity(product.getStock().getMaximumSaleQuantity())
                        .minimumSaleQuantity(product.getStock().getMinimumSaleQuantity())
                        .vatRate(product.getStock().getVatRate())
                        .build())
                .build();
    }

    @Override
    protected ProductResponse handle(ProductCommand.Replay replay) {
        eventStore.republishEvents();
        return null;
    }

    @Override
    protected ProductResponse handle(ProductCommand.ChangeStatus changeStatus) {
        var product = eventStore.getProductById(changeStatus.productId().getValue().toString());
        product.changeStatus(changeStatus);
        return new ProductResponse.ProductStatusChanged(product.getId().getValue());
    }
}
