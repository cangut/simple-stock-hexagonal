package com.example.can.port.inbound;

import com.example.can.event.ProductEvent;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;

public interface ProductEventConsumer {
    void consume(@Payload ProductEvent.Created created, Acknowledgment acknowledgment);
    void consume(@Payload ProductEvent.UnitInStockIncreased unitInStockIncreased, Acknowledgment acknowledgment);
    void consume(@Payload ProductEvent.UnitInStockDecreased unitInStockDecreased, Acknowledgment acknowledgment);
    void consume(@Payload ProductEvent.Closed closed, Acknowledgment acknowledgment);

}
