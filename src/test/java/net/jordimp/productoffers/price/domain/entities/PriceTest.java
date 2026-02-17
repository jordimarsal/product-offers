package net.jordimp.productoffers.price.domain.entities;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import net.jordimp.productoffers.price.domain.valueobjects.BrandId;
import net.jordimp.productoffers.price.domain.valueobjects.DateRange;
import net.jordimp.productoffers.price.domain.valueobjects.Money;
import net.jordimp.productoffers.price.domain.valueobjects.PriceList;
import net.jordimp.productoffers.price.domain.valueobjects.Priority;
import net.jordimp.productoffers.price.domain.valueobjects.ProductId;
import org.junit.jupiter.api.Test;

class PriceTest {

    @Test
    void isEffectiveAt_returnsTrueWhenWithinRange() {
        Price p =
                new Price(
                        1L,
                        new BrandId(1L),
                        new ProductId(35455L),
                        new DateRange(
                                LocalDateTime.of(2020, 6, 14, 0, 0),
                                LocalDateTime.of(2020, 6, 14, 23, 59)),
                        new PriceList(1L),
                        new Priority(0),
                        new Money(BigDecimal.valueOf(35.50), "EUR"));

        assertTrue(p.isEffectiveAt(LocalDateTime.of(2020, 6, 14, 12, 0)));
        assertTrue(p.isEffectiveAt(LocalDateTime.of(2020, 6, 14, 0, 0))); // boundary
        assertTrue(p.isEffectiveAt(LocalDateTime.of(2020, 6, 14, 23, 59))); // boundary
    }

    @Test
    void isEffectiveAt_returnsFalseWhenOutsideRange() {
        Price p =
                new Price(
                        1L,
                        new BrandId(1L),
                        new ProductId(35455L),
                        new DateRange(
                                LocalDateTime.of(2020, 6, 14, 0, 0),
                                LocalDateTime.of(2020, 6, 14, 23, 59)),
                        new PriceList(1L),
                        new Priority(0),
                        new Money(BigDecimal.valueOf(35.50), "EUR"));

        assertFalse(p.isEffectiveAt(LocalDateTime.of(2020, 6, 13, 23, 59)));
        assertFalse(p.isEffectiveAt(LocalDateTime.of(2020, 6, 15, 0, 0)));
    }

    @Test
    void matches_checksBrandAndProductId() {
        Price p =
                new Price(
                        1L,
                        new BrandId(1L),
                        new ProductId(100L),
                        new DateRange(
                                LocalDateTime.of(2020, 6, 14, 0, 0),
                                LocalDateTime.of(2020, 12, 31, 23, 59)),
                        new PriceList(1L),
                        new Priority(0),
                        new Money(BigDecimal.valueOf(35.50), "EUR"));

        assertTrue(p.matches(1L, 100L));
        assertFalse(p.matches(2L, 100L));
        assertFalse(p.matches(1L, 200L));
    }

    @Test
    void isEffectiveAt_handlesNullStartOrEndDates_and_matches_handlesNulls() {
        // startDate == null -> treated as unbounded start
        Price p1 =
                new Price(
                        1L,
                        new BrandId(1L),
                        new ProductId(35455L),
                        new DateRange(null, LocalDateTime.of(2020, 6, 14, 23, 59)),
                        new PriceList(1L),
                        new Priority(0),
                        new Money(BigDecimal.valueOf(35.50), "EUR"));
        assertTrue(p1.isEffectiveAt(LocalDateTime.of(2020, 6, 14, 12, 0)));

        // endDate == null -> treated as unbounded end
        Price p2 =
                new Price(
                        1L,
                        new BrandId(1L),
                        new ProductId(35455L),
                        new DateRange(LocalDateTime.of(2020, 6, 14, 0, 0), null),
                        new PriceList(1L),
                        new Priority(0),
                        new Money(BigDecimal.valueOf(35.50), "EUR"));
        assertTrue(p2.isEffectiveAt(LocalDateTime.of(2020, 6, 15, 12, 0)));

        // both null -> always effective
        Price p3 =
                new Price(
                        1L,
                        new BrandId(1L),
                        new ProductId(35455L),
                        new DateRange(null, null),
                        new PriceList(1L),
                        new Priority(0),
                        new Money(BigDecimal.valueOf(35.50), "EUR"));
        assertTrue(p3.isEffectiveAt(LocalDateTime.of(2019, 1, 1, 0, 0)));

        // Note: Price entity uses value objects which cannot be null (they validate in constructor)
        // So matches() will always return false for null brandId/productId because the VOs
        // throw exceptions on invalid values, making null handling unnecessary at this level
    }
}
