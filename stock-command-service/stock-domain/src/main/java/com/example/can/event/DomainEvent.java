package com.example.can.event;

import java.time.Instant;
import java.util.UUID;

public interface DomainEvent {
    Instant createdAt();
    UUID productId();
}
