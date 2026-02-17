package net.jordimp.productoffers.price.domain.valueobjects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Unit tests for PriceList Value Object")
class PriceListTest {

    @Test
    @DisplayName("Should create PriceList with valid positive value")
    void shouldCreatePriceListWithValidValue() {
        PriceList priceList = new PriceList(1L);
        assertEquals(1L, priceList.value());
    }

    @Test
    @DisplayName("Should create PriceList with large positive value")
    void shouldCreatePriceListWithLargeValue() {
        PriceList priceList = new PriceList(999999L);
        assertEquals(999999L, priceList.value());
    }

    @Test
    @DisplayName("Should throw exception when value is null")
    void shouldThrowExceptionWhenValueIsNull() {
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> new PriceList(null));
        assertEquals("Price list must be a positive number", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when value is zero")
    void shouldThrowExceptionWhenValueIsZero() {
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> new PriceList(0L));
        assertEquals("Price list must be a positive number", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when value is negative")
    void shouldThrowExceptionWhenValueIsNegative() {
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> new PriceList(-1L));
        assertEquals("Price list must be a positive number", exception.getMessage());
    }
}
