package com.example.can.model;


import com.example.can.event.DomainEvent;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Builder
@Document(collection = "event-store")
public class EventModel {
    @Id
    private String id;
    private Instant createdAt;
    @Indexed
    private String aggregateId;
    private String aggregateType;
    private int version;
    private String eventType;
    private DomainEvent eventData;


}
