package com.example.can.adapter;

import com.example.can.aggregate.Product;
import com.example.can.event.DomainEvent;
import com.example.can.exception.ProductApplicationException;
import com.example.can.model.EventModel;
import com.example.can.ports.outbound.EventProducerService;
import com.example.can.ports.outbound.EventStoreService;
import com.example.can.repository.EventStoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventStoreServiceImpl implements EventStoreService {

    private final EventStoreRepository eventStoreRepository;
    private final EventProducerService eventProducerService;

    @Autowired
    public EventStoreServiceImpl(EventStoreRepository eventStoreRepository, EventProducerService eventProducerService) {
        this.eventStoreRepository = eventStoreRepository;
        this.eventProducerService = eventProducerService;
    }

    @Override
    public void saveEvents(Product product) {
        var eventModels = eventStoreRepository.findByAggregateId(product.getId().getValue().toString());
        var unconsumedDomainEvents = product.getUnconsumedDomainEvents();

        if (product.getVersion() != -1 && eventModels.getLast().getVersion() != product.getVersion()) {
            throw new ProductApplicationException.OptimisticConcurrency("Aggregate inserted or updated by another process");
        }

        int version = product.getVersion();
        for (DomainEvent event : unconsumedDomainEvents) {

            version++;

            var eventModel = EventModel.builder()
                    .aggregateId(product.getId().getValue().toString())
                    .aggregateType(Product.class.getTypeName())
                    .createdAt(Instant.now())
                    .eventType(event.getClass().getTypeName())
                    .version(version)
                    .eventData(event)
                    .build();

            var persistentEvent = eventStoreRepository.save(eventModel);
            if (!persistentEvent.getId().isEmpty()) {
                eventProducerService.produce(event.getClass().getSimpleName(), event);
            }
        }
        product.setDomainEventsAsConsumed();
    }

    @Override
    public Product getProductById(String aggregateId) {
        var aggregate = Product.builder().build();
        var eventModels = getEvents(aggregateId);
        if (!eventModels.isEmpty()) {
            var domainEvents = eventModels.stream().map(EventModel::getEventData).collect(Collectors.toList());

            aggregate.replayEvents(domainEvents);

            var latestVersion = eventModels.stream()
                    .map(EventModel::getVersion)
                    .max(Comparator.naturalOrder());
            aggregate.setVersion(latestVersion.get());
        }

        return aggregate;
    }

    @Override
    public void republishEvents() {
        var aggregateIds = getAggregateIds();
        for (String aggregateId : aggregateIds) {
            var aggregate = this.getProductById(aggregateId);
            if (aggregate == null) continue;
            var events = getEvents(aggregate.getId().getValue().toString());
            for (EventModel eventModel : events) {
                eventProducerService.produce(eventModel.getEventData().getClass().getSimpleName(), eventModel.getEventData());
            }
        }
    }

    private List<EventModel> getEvents(String aggregateId) {
        var eventList = eventStoreRepository.findByAggregateId(aggregateId);
        if (eventList == null || eventList.isEmpty()) {
            throw new ProductApplicationException.AggregateNotFound("Aggregate does not found.");
        }
        return eventList;
    }

    private List<String> getAggregateIds() {
        var eventList = eventStoreRepository.findAll();
        if(eventList.isEmpty()){
            throw new ProductApplicationException.EventStoreIsEmpty("Event store is empty.");
        }
        return eventList.stream()
                .map(EventModel::getAggregateId)
                .distinct()
                .collect(Collectors.toList());
    }
}
