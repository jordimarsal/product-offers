package net.jordimp.productoffers.price.domain.valueobjects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Unit tests for BrandId Value Object")
class BrandIdTest {

    @Test
    @DisplayName("Should create BrandId with valid positive value")
    void shouldCreateBrandIdWithValidValue() {
        BrandId brandId = new BrandId(1L);
        assertEquals(1L, brandId.value());
    }

    @Test
    @DisplayName("Should create BrandId with large positive value")
    void shouldCreateBrandIdWithLargeValue() {
        BrandId brandId = new BrandId(999999L);
        assertEquals(999999L, brandId.value());
    }

    @Test
    @DisplayName("Should throw exception when value is null")
    void shouldThrowExceptionWhenValueIsNull() {
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> new BrandId(null));
        assertEquals("Brand ID must be a positive number", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when value is zero")
    void shouldThrowExceptionWhenValueIsZero() {
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> new BrandId(0L));
        assertEquals("Brand ID must be a positive number", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when value is negative")
    void shouldThrowExceptionWhenValueIsNegative() {
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> new BrandId(-1L));
        assertEquals("Brand ID must be a positive number", exception.getMessage());
    }
}
