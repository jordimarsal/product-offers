package net.jordimp.productoffers.price.domain.valueobjects;

/**
 * Value Object representing a brand identifier.
 *
 * @param value the brand ID value (must be positive)
 */
public record BrandId(Long value) {

    public BrandId {
        if (value == null || value < 1) {
            throw new IllegalArgumentException("Brand ID must be a positive number");
        }
    }
}
