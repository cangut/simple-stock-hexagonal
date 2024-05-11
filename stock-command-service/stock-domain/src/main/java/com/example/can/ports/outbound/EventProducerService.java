package com.example.can.ports.outbound;

import com.example.can.event.DomainEvent;

public interface EventProducerService {
    void produce(String topic, DomainEvent event);
}
