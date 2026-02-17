package net.jordimp.productoffers.price.domain.valueobjects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Unit tests for Priority Value Object")
class PriorityTest {

    @Test
    @DisplayName("Should create Priority with valid non-negative value")
    void shouldCreatePriorityWithValidValue() {
        Priority priority = new Priority(0);
        assertEquals(0, priority.value());
    }

    @Test
    @DisplayName("Should create Priority with positive value")
    void shouldCreatePriorityWithPositiveValue() {
        Priority priority = new Priority(1);
        assertEquals(1, priority.value());
    }

    @Test
    @DisplayName("Should create Priority with large positive value")
    void shouldCreatePriorityWithLargeValue() {
        Priority priority = new Priority(999);
        assertEquals(999, priority.value());
    }

    @Test
    @DisplayName("Should throw exception when value is null")
    void shouldThrowExceptionWhenValueIsNull() {
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> new Priority(null));
        assertEquals("Priority must be a non-negative number", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when value is negative")
    void shouldThrowExceptionWhenValueIsNegative() {
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> new Priority(-1));
        assertEquals("Priority must be a non-negative number", exception.getMessage());
    }
}
