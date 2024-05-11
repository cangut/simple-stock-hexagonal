package com.example.can.controller;

import com.example.can.port.inbound.ProductQueryService;
import com.example.can.query.ProductQuery;
import com.example.can.response.QueryResponse;
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
@RequestMapping("/product-query")
public class ProductController {

    private final ProductQueryService<QueryResponse> queryService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<QueryResponse> getProductById(@PathVariable UUID id) {
        var query = ProductQuery.ByProductId.builder().productId(id).build();
        var response = queryService.dispatch(query);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<QueryResponse> getProductById() {
        var query = ProductQuery.All.builder().build();
        var response = queryService.dispatch(query);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
