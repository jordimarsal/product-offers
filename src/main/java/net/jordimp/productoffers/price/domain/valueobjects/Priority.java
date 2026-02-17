package net.jordimp.productoffers.price.domain.valueobjects;

/**
 * Value Object representing a priority level for price selection. Higher values indicate higher
 * priority.
 *
 * @param value the priority value (non-negative)
 */
public record Priority(Integer value) {

    public Priority {
        if (value == null || value < 0) {
            throw new IllegalArgumentException("Priority must be a non-negative number");
        }
    }
}
