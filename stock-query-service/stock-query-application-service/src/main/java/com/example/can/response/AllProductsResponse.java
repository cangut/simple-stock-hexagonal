package com.example.can.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AllProductsResponse extends QueryResponse {
    private List<ProductResponse> products;
}
