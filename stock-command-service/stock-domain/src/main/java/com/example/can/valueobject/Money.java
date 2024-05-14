package com.example.can.valueobject;

import com.example.can.exception.ProductDomainException;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record Money(BigDecimal amount) {
    public static final Money ZERO = new Money(BigDecimal.ZERO);

    public boolean isGreaterThanZero() {
        return this.amount != null && this.amount.compareTo(BigDecimal.ZERO) > 0;
    }

    public boolean isGreaterThan(Money money) {
        return this.amount != null && this.amount.compareTo(money.amount()) > 0;
    }

    public Money add(Money money) {
        return new Money(setScale(this.amount.add(money.amount())));
    }

    public Money subtract(Money money) {
        return new Money(setScale(this.amount.subtract(money.amount())));
    }

    public Money multiply(int multiplier) {
        return new Money(setScale(this.amount.multiply(new BigDecimal(multiplier))));
    }

    public Money divide(int divisor) {
        return new Money(setScale(this.amount.divide(new BigDecimal(divisor))));
    }

    public static Money calculateVatRateIncludedPrice(Integer vatRate, Money basePrice) {
        boolean greaterThan = basePrice.isGreaterThanZero();
        if (!greaterThan) {
            throw new ProductDomainException.AmountMustBeGreaterThanZero("Money amount must be greater than zero");
        }
        var vatRatePrice = (basePrice.multiply(vatRate)).divide(100);
        return basePrice.add(vatRatePrice);
    }

    private BigDecimal setScale(BigDecimal input) {
        return input.setScale(2, RoundingMode.HALF_EVEN);
    }
}
