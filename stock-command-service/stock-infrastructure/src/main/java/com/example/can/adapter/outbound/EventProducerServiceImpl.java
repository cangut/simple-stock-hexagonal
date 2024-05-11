package com.example.can.adapter.outbound;

import com.example.can.event.DomainEvent;
import com.example.can.ports.outbound.EventProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class EventProducerServiceImpl implements EventProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void produce(String topic, DomainEvent event) {
        kafkaTemplate.send(topic, event.productId().toString(), event);
    }
}
