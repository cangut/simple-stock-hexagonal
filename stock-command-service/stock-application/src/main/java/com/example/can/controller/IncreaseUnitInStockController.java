package com.example.can.controller;

import com.example.can.dto.UnitInStockDto;
import com.example.can.mapper.ProductMapper;
import com.example.can.ports.inbound.ProductApplicationService;
import com.example.can.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class IncreaseUnitInStockController {

    private final ProductMapper mapper;
    private final ProductApplicationService<ProductResponse> applicationService;

    @PutMapping("/{productId}/increase-unit-in-stock")
    public ResponseEntity<ProductResponse> increaseUnitInStock(@PathVariable("productId") UUID productId,
                                                               @RequestBody UnitInStockDto dto) {
        var increaseUnitInStock = mapper.mapToIncreaseUnitInStockCommand(dto, productId);
        var response = applicationService.dispatch(increaseUnitInStock);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
