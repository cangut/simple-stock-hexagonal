package com.example.can.exception;

public class ProductDomainException extends RuntimeException {

    private final String message;

    public ProductDomainException(String message) {
        super(message);
        this.message = message;
    }


    public static class ProductStateIsNotActive extends ProductDomainException {
        public ProductStateIsNotActive(String message) {
            super(message);
        }
    }

    public static class AmountMustBeGreaterThanZero extends ProductDomainException {
        public AmountMustBeGreaterThanZero(String message) {
            super(message);
        }
    }
}
