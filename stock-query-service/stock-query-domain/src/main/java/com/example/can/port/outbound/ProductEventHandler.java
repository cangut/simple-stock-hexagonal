package com.example.can.port.outbound;

import com.example.can.event.ProductEvent;

public interface ProductEventHandler {

    void handle(ProductEvent.Created created);
    void handle(ProductEvent.UnitInStockIncreased unitInStockIncreased);
    void handle(ProductEvent.UnitInStockDecreased unitInStockDecreased);
    void handle(ProductEvent.Closed closed);
}
