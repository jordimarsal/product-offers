package net.jordimp.productoffers.price.domain.valueobjects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Unit tests for Money Value Object")
class MoneyTest {

    @Test
    @DisplayName("Should create Money with valid amount and currency")
    void shouldCreateMoneyWithValidValues() {
        Money money = new Money(new BigDecimal("35.50"), "EUR");
        assertEquals(new BigDecimal("35.50"), money.amount());
        assertEquals("EUR", money.currency());
    }

    @Test
    @DisplayName("Should create Money with zero amount")
    void shouldCreateMoneyWithZeroAmount() {
        Money money = new Money(BigDecimal.ZERO, "USD");
        assertEquals(BigDecimal.ZERO, money.amount());
        assertEquals("USD", money.currency());
    }

    @Test
    @DisplayName("Should throw exception when amount is null")
    void shouldThrowExceptionWhenAmountIsNull() {
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> new Money(null, "EUR"));
        assertEquals("Amount must be a positive number", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when amount is negative")
    void shouldThrowExceptionWhenAmountIsNegative() {
        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> new Money(new BigDecimal("-10.00"), "EUR"));
        assertEquals("Amount must be a positive number", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when currency is null")
    void shouldThrowExceptionWhenCurrencyIsNull() {
        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> new Money(new BigDecimal("35.50"), null));
        assertEquals("Currency cannot be null or blank", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when currency is blank")
    void shouldThrowExceptionWhenCurrencyIsBlank() {
        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> new Money(new BigDecimal("35.50"), "  "));
        assertEquals("Currency cannot be null or blank", exception.getMessage());
    }

    @Test
    @DisplayName("Should return correct amount using amount() method")
    void shouldReturnCorrectAmount() {
        Money money = new Money(new BigDecimal("25.45"), "EUR");
        assertEquals(new BigDecimal("25.45"), money.amount());
    }

    @Test
    @DisplayName("Should return correct currency using currency() method")
    void shouldReturnCorrectCurrency() {
        Money money = new Money(new BigDecimal("30.50"), "USD");
        assertEquals("USD", money.currency());
    }
}
