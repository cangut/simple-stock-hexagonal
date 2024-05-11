package com.example.can.exception;

public class ProductApplicationException extends RuntimeException {

    private final String message;

    public ProductApplicationException(String message) {
        super(message);
        this.message = message;
    }


    public static class OptimisticConcurrency extends ProductApplicationException {
        public OptimisticConcurrency(String message) {
            super(message);
        }
    }

    public static class AggregateNotFound extends ProductApplicationException {
        public AggregateNotFound(String message) {
            super(message);
        }
    }

    public static class EventStoreIsEmpty extends ProductApplicationException {
        public EventStoreIsEmpty(String message) {
            super(message);
        }
    }
}
