package com.example.can.mapper;

import com.example.can.command.ProductCommand;
import com.example.can.dto.CreateProductDto;
import com.example.can.dto.UnitInStockDto;
import com.example.can.valueobject.*;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ProductMapper {

    public ProductCommand.Create mapToCreateCommand(CreateProductDto dto) {
        UUID uuid = UUID.randomUUID();
        ProductId productId = new ProductId(uuid);
        ProductCommand.CreateStock stock = ProductCommand.CreateStock.builder()
                .productId(productId)
                .stockId(new StockId(UUID.randomUUID()))
                .stockName(dto.getStock().getStockName())
                .stockCode(new StockCode(dto.getStock().getStockCode()))
                .maximumSaleQuantity(dto.getStock().getMaximumSaleQuantity())
                .minimumSaleQuantity(dto.getStock().getMinimumSaleQuantity())
                .unitInStock(dto.getStock().getUnitInStock())
                .vatRate(dto.getStock().getVatRate())
                .build();


        return ProductCommand.Create.builder()
                .productId(productId)
                .productTitle(dto.getProductTitle())
                .categoryId(new CategoryId(dto.getCategoryId()))
                .brand(Brand.valueOf(dto.getBrand()))
                .basePrice(new Money(dto.getBasePrice()))
                .stock(stock)
                .build();
    }

    public ProductCommand.IncreaseUnitInStock mapToIncreaseUnitInStockCommand(UnitInStockDto dto, UUID productId) {
        return ProductCommand.IncreaseUnitInStock.builder()
                .productId(new ProductId(productId))
                .amount(dto.getAmount())
                .build();
    }

    public ProductCommand.DecreaseUnitInStock mapToDecreaseUnitInStockCommand(UnitInStockDto dto, UUID productId) {
        return ProductCommand.DecreaseUnitInStock.builder()
                .productId(new ProductId(productId))
                .amount(dto.getAmount())
                .build();
    }
}
