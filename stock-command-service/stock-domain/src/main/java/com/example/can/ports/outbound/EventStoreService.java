package com.example.can.ports.outbound;

import com.example.can.aggregate.Product;

public interface EventStoreService {
    void saveEvents(Product product);
    Product getProductById(String aggregateId);
    void recover();
}
