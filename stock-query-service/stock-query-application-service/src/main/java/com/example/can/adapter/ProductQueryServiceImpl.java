package com.example.can.adapter;

import com.example.can.entity.ProductEntity;
import com.example.can.entity.StockEntity;
import com.example.can.port.inbound.ProductQueryService;
import com.example.can.query.ProductQuery;
import com.example.can.port.outbound.ProductJpaRepository;
import com.example.can.port.outbound.StockJpaRepository;
import com.example.can.response.AllProductsResponse;
import com.example.can.response.ProductResponse;
import com.example.can.response.QueryResponse;
import com.example.can.response.StockResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductQueryServiceImpl extends ProductQueryService<QueryResponse> {

    private final ProductJpaRepository productJpaRepository;
    private final StockJpaRepository stockJpaRepository;

    @Override
    @Transactional(readOnly = true)
    protected QueryResponse handle(ProductQuery.ByProductId byProductId) {
        ProductEntity productEntity = productJpaRepository.findById(byProductId.productId())
                .orElseThrow(() -> new RuntimeException("Entity does not found."));

        return ProductResponse.builder()
                .id(productEntity.getId())
                .productTitle(productEntity.getProductTitle())
                .productState(productEntity.getProductState())
                .categoryId(productEntity.getCategoryId())
                .stockId(productEntity.getStockId())
                .basePrice(productEntity.getBasePrice())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    protected QueryResponse handle(ProductQuery.StockById stockById) {
        StockEntity stockEntity = stockJpaRepository.findById(stockById.stockId())
                .orElseThrow(() -> new RuntimeException("Entity does not found."));

        return StockResponse.builder()
                .id(stockEntity.getId())
                .productId(stockEntity.getProduct().getId())
                .price(stockEntity.getPrice())
                .stockState(stockEntity.getStockState())
                .unitInStock(stockEntity.getUnitInStock())
                .vatRate(stockEntity.getVatRate())
                .stockCode(stockEntity.getStockCode())
                .maximumSaleQuantity(stockEntity.getMaximumSaleQuantity())
                .minimumSaleQuantity(stockEntity.getMinimumSaleQuantity())
                .discountedPrice(stockEntity.getDiscountedPrice())
                .discount(stockEntity.getDiscount())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    protected QueryResponse handle(ProductQuery.All all) {
        List<ProductEntity> allProducts = productJpaRepository.findAll();
        if(allProducts.isEmpty()) {
            throw new RuntimeException("Product records does not found.");
        }
        List<ProductResponse> productResponses = new ArrayList<>();
        for(ProductEntity productEntity : allProducts) {
            var productResponse = ProductResponse.builder()
                    .id(productEntity.getId())
                    .productTitle(productEntity.getProductTitle())
                    .productState(productEntity.getProductState())
                    .categoryId(productEntity.getCategoryId())
                    .stockId(productEntity.getStockId())
                    .basePrice(productEntity.getBasePrice())
                    .build();
            productResponses.add(productResponse);
        }
        return new AllProductsResponse(productResponses);
    }
}
