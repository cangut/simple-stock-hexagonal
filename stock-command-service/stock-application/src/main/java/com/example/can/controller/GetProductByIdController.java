package com.example.can.controller;

import com.example.can.ports.inbound.ProductApplicationService;
import com.example.can.query.ProductQuery;
import com.example.can.response.ProductResponse;
import com.example.can.valueobject.ProductId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("product")
public class GetProductByIdController {

    private final ProductApplicationService<ProductResponse> applicationService;

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> getById(@PathVariable UUID productId) {
        var product = applicationService.dispatch(new ProductQuery.ById(new ProductId(productId)));
        return new ResponseEntity<>(product, HttpStatus.OK);
    }
}
