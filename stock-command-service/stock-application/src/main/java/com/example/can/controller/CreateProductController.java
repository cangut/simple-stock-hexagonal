package com.example.can.controller;

import com.example.can.aggregate.Product;
import com.example.can.dto.CreateProductDto;
import com.example.can.mapper.ProductMapper;
import com.example.can.ports.inbound.ProductApplicationService;
import com.example.can.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/product")
public class CreateProductController {

    private final ProductMapper mapper;
    private final ProductApplicationService<ProductResponse> productApplicationService;

    @PostMapping
    public ResponseEntity<ProductResponse> create(@RequestBody CreateProductDto dto) {
        var createCommand = mapper.mapToCreateCommand(dto);
        ProductResponse productResponse = productApplicationService.dispatch(createCommand);
        return new ResponseEntity<>(productResponse, HttpStatus.CREATED);
    }
}
