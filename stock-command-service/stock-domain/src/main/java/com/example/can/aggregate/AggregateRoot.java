package com.example.can.aggregate;

import com.example.can.event.DomainEvent;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

@Slf4j
public abstract class AggregateRoot<Id> {
    @Getter
    @Setter
    private Id id;

    @Getter
    @Setter
    private int version = -1;

    private final List<DomainEvent> domainEvents = new ArrayList<>();

    public void setDomainEventsAsConsumed() {
        domainEvents.clear();
    }

    public List<DomainEvent> getUnconsumedDomainEvents() {
        return this.domainEvents;
    }

    protected void applyChange(DomainEvent event, boolean isNew) {
        try {
            var method = getClass().getDeclaredMethod("apply", event.getClass());
            method.setAccessible(true);
            method.invoke(this, event);
        } catch (NoSuchMethodException e) {
            log.warn("apply method was not found in the aggregate for {}. Exception type: {}", event.getClass().getName(), e.getClass());
        }catch (Exception e) {
            log.error("Error occurred applying event");
        } finally {
            if (isNew) {
                domainEvents.add(event);
            }
        }
    }

    protected void raiseEvent(DomainEvent event) {
        applyChange(event, true);
    }

    public void replayEvents(List<DomainEvent> events) {
        events.forEach(event -> applyChange(event, false));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AggregateRoot<?> that = (AggregateRoot<?>) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
