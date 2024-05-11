package com.example.can.ports.inbound;

import com.example.can.event.ProductEvent;

public interface BackOfficeEventConsumer<T> {
    void consume(ProductEvent.StatusChanged statusChanged, T acknowledgment);
}
