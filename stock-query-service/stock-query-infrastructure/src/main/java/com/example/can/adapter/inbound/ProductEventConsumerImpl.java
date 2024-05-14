package com.example.can.adapter.inbound;

import com.example.can.event.ProductEvent;
import com.example.can.port.inbound.ProductEventConsumer;
import com.example.can.port.outbound.ProductEventHandler;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class ProductEventConsumerImpl implements ProductEventConsumer {

    private static final Logger log = LoggerFactory.getLogger(ProductEventConsumerImpl.class);
    private final ProductEventHandler eventHandler;

    @Override
    @KafkaListener(topics = "Created", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(@Payload ProductEvent.Created created, Acknowledgment acknowledgment) {
        log.info("Created event consumed: {} {}", created, Instant.now());
        eventHandler.handle(created);
        acknowledgment.acknowledge();
    }

    @Override
    @KafkaListener(topics = "UnitInStockIncreased", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(@Payload ProductEvent.UnitInStockIncreased unitInStockIncreased, Acknowledgment acknowledgment) {
        log.info("UnitInStockIncreased event consumed: {} {}", unitInStockIncreased, Instant.now());
        eventHandler.handle(unitInStockIncreased);
        acknowledgment.acknowledge();
    }

    @Override
    @KafkaListener(topics = "UnitInStockDecreased", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(@Payload ProductEvent.UnitInStockDecreased unitInStockDecreased, Acknowledgment acknowledgment) {
        log.info("UnitInStockDecreased event consumed: {} {}", unitInStockDecreased, Instant.now());
        eventHandler.handle(unitInStockDecreased);
        acknowledgment.acknowledge();
    }

    @Override
    @KafkaListener(topics = "Closed", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(@Payload ProductEvent.Closed closed, Acknowledgment acknowledgment) {
        log.info("Closed event consumed: {} {}", closed, Instant.now());
        eventHandler.handle(closed);
        acknowledgment.acknowledge();
    }

    @Override
    @KafkaListener(topics = "${spring.kafka.replay.topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(ProductEvent productEvent, Acknowledgment acknowledgment) {
        try {
            var eventHandlerMethod = eventHandler.getClass().getDeclaredMethod("handle", productEvent.getClass());
            eventHandlerMethod.invoke(eventHandler, productEvent);
        }catch (Exception e){
            log.error("Error while replaying event: {}", e.getMessage());
        }
    }
}
