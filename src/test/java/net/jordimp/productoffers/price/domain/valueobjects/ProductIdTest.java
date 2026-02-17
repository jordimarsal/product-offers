package net.jordimp.productoffers.price.domain.valueobjects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Unit tests for ProductId Value Object")
class ProductIdTest {

    @Test
    @DisplayName("Should create ProductId with valid positive value")
    void shouldCreateProductIdWithValidValue() {
        ProductId productId = new ProductId(1L);
        assertEquals(1L, productId.value());
    }

    @Test
    @DisplayName("Should create ProductId with large positive value")
    void shouldCreateProductIdWithLargeValue() {
        ProductId productId = new ProductId(35455L);
        assertEquals(35455L, productId.value());
    }

    @Test
    @DisplayName("Should throw exception when value is null")
    void shouldThrowExceptionWhenValueIsNull() {
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> new ProductId(null));
        assertEquals("Product ID must be a positive number", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when value is zero")
    void shouldThrowExceptionWhenValueIsZero() {
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> new ProductId(0L));
        assertEquals("Product ID must be a positive number", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when value is negative")
    void shouldThrowExceptionWhenValueIsNegative() {
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> new ProductId(-1L));
        assertEquals("Product ID must be a positive number", exception.getMessage());
    }
}
