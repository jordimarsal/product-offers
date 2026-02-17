package net.jordimp.productoffers.price.domain.valueobjects;

/**
 * Value Object representing a price list identifier.
 *
 * @param value the price list ID value (must be positive)
 */
public record PriceList(Long value) {

    public PriceList {
        if (value == null || value < 1) {
            throw new IllegalArgumentException("Price list must be a positive number");
        }
    }
}
