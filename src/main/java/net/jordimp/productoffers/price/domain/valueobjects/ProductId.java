package net.jordimp.productoffers.price.domain.valueobjects;

/**
 * Value Object representing a product identifier.
 *
 * @param value the product ID value (must be positive)
 */
public record ProductId(Long value) {

    public ProductId {
        if (value == null || value < 1) {
            throw new IllegalArgumentException("Product ID must be a positive number");
        }
    }
}
