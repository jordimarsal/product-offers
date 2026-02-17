package net.jordimp.productoffers.price.domain.entities;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class PricesTest {

    @Test
    void isEffectiveAt_returnsTrueWhenWithinRange() {
        Prices p = new Prices();
        p.setStartDate(LocalDateTime.of(2020, 6, 14, 0, 0));
        p.setEndDate(LocalDateTime.of(2020, 6, 14, 23, 59));

        assertTrue(p.isEffectiveAt(LocalDateTime.of(2020, 6, 14, 12, 0)));
        assertTrue(p.isEffectiveAt(LocalDateTime.of(2020, 6, 14, 0, 0))); // boundary
        assertTrue(p.isEffectiveAt(LocalDateTime.of(2020, 6, 14, 23, 59))); // boundary
    }

    @Test
    void isEffectiveAt_returnsFalseWhenOutsideRange() {
        Prices p = new Prices();
        p.setStartDate(LocalDateTime.of(2020, 6, 14, 0, 0));
        p.setEndDate(LocalDateTime.of(2020, 6, 14, 23, 59));

        assertFalse(p.isEffectiveAt(LocalDateTime.of(2020, 6, 13, 23, 59)));
        assertFalse(p.isEffectiveAt(LocalDateTime.of(2020, 6, 15, 0, 0)));
    }

    @Test
    void matches_checksBrandAndProductId() {
        Prices p = new Prices();
        p.setBrandId(1L);
        p.setProductId(100L);

        assertTrue(p.matches(1L, 100L));
        assertFalse(p.matches(2L, 100L));
        assertFalse(p.matches(1L, 200L));
    }

    @Test
    void isEffectiveAt_handlesNullStartOrEndDates_and_matches_handlesNulls() {
        Prices p = new Prices();

        // startDate == null -> treated as unbounded start
        p.setStartDate(null);
        p.setEndDate(LocalDateTime.of(2020, 6, 14, 23, 59));
        assertTrue(p.isEffectiveAt(LocalDateTime.of(2020, 6, 14, 12, 0)));

        // endDate == null -> treated as unbounded end
        p.setStartDate(LocalDateTime.of(2020, 6, 14, 0, 0));
        p.setEndDate(null);
        assertTrue(p.isEffectiveAt(LocalDateTime.of(2020, 6, 15, 12, 0)));

        // both null -> always effective
        p.setStartDate(null);
        p.setEndDate(null);
        assertTrue(p.isEffectiveAt(LocalDateTime.of(2019, 1, 1, 0, 0)));

        // matches: null brand/product on entity should return false
        p.setBrandId(null);
        p.setProductId(100L);
        assertFalse(p.matches(1L, 100L));

        p.setBrandId(1L);
        p.setProductId(null);
        assertFalse(p.matches(1L, 100L));
    }
}
