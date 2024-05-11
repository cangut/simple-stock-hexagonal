package com.example.can.adapter.inbound;

import com.example.can.event.ProductEvent;
import com.example.can.port.inbound.ProductEventConsumer;
import com.example.can.port.outbound.ProductEventHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductEventConsumerImpl implements ProductEventConsumer {

    private final ProductEventHandler eventHandler;

    @Override
    @KafkaListener(topics = "Created", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(@Payload ProductEvent.Created created, Acknowledgment acknowledgment) {
        eventHandler.handle(created);
        acknowledgment.acknowledge();
    }

    @Override
    @KafkaListener(topics = "UnitInStockIncreased", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(@Payload ProductEvent.UnitInStockIncreased unitInStockIncreased, Acknowledgment acknowledgment) {
        eventHandler.handle(unitInStockIncreased);
        acknowledgment.acknowledge();
    }

    @Override
    @KafkaListener(topics = "UnitInStockDecreased", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(@Payload ProductEvent.UnitInStockDecreased unitInStockDecreased, Acknowledgment acknowledgment) {
        eventHandler.handle(unitInStockDecreased);
        acknowledgment.acknowledge();
    }

    @Override
    @KafkaListener(topics = "Closed", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(@Payload ProductEvent.Closed closed, Acknowledgment acknowledgment) {
        eventHandler.handle(closed);
        acknowledgment.acknowledge();
    }
}
