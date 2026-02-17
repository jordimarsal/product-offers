package net.jordimp.productoffers.price.domain.valueobjects;

import java.math.BigDecimal;

/**
 * Value Object representing a monetary amount with currency.
 *
 * @param amount the monetary amount (must be positive)
 * @param currency the currency code (e.g., "EUR", "USD")
 */
public record Money(BigDecimal amount, String currency) {

    public Money {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount must be a positive number");
        }
        if (currency == null || currency.isBlank()) {
            throw new IllegalArgumentException("Currency cannot be null or blank");
        }
    }

    /**
     * Returns the amount value.
     *
     * @return the BigDecimal amount
     */
    public BigDecimal amount() {
        return amount;
    }

    /**
     * Returns the currency code.
     *
     * @return the currency code
     */
    public String currency() {
        return currency;
    }
}
