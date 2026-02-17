package net.jordimp.productoffers.price.domain.valueobjects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Unit tests for DateRange Value Object")
class DateRangeTest {

    @Test
    @DisplayName("Should create DateRange with valid start and end dates")
    void shouldCreateDateRangeWithValidDates() {
        LocalDateTime start = LocalDateTime.of(2020, 6, 14, 0, 0);
        LocalDateTime end = LocalDateTime.of(2020, 12, 31, 23, 59);
        DateRange dateRange = new DateRange(start, end);

        assertThat(dateRange.startDate()).isEqualTo(start);
        assertThat(dateRange.endDate()).isEqualTo(end);
    }

    @Test
    @DisplayName("Should create DateRange with null start date (open start)")
    void shouldCreateDateRangeWithNullStartDate() {
        LocalDateTime end = LocalDateTime.of(2020, 12, 31, 23, 59);
        DateRange dateRange = new DateRange(null, end);

        assertThat(dateRange.startDate()).isNull();
        assertThat(dateRange.endDate()).isEqualTo(end);
    }

    @Test
    @DisplayName("Should create DateRange with null end date (open end)")
    void shouldCreateDateRangeWithNullEndDate() {
        LocalDateTime start = LocalDateTime.of(2020, 6, 14, 0, 0);
        DateRange dateRange = new DateRange(start, null);

        assertThat(dateRange.startDate()).isEqualTo(start);
        assertThat(dateRange.endDate()).isNull();
    }

    @Test
    @DisplayName("Should throw exception when start date is after end date")
    void shouldThrowExceptionWhenStartDateIsAfterEndDate() {
        LocalDateTime start = LocalDateTime.of(2020, 12, 31, 23, 59);
        LocalDateTime end = LocalDateTime.of(2020, 6, 14, 0, 0);

        assertThrows(IllegalArgumentException.class, () -> new DateRange(start, end));
    }

    @Test
    @DisplayName("Should return true when date is within range")
    void shouldReturnTrueWhenDateIsWithinRange() {
        LocalDateTime start = LocalDateTime.of(2020, 6, 14, 0, 0);
        LocalDateTime end = LocalDateTime.of(2020, 12, 31, 23, 59);
        DateRange dateRange = new DateRange(start, end);
        LocalDateTime testDate = LocalDateTime.of(2020, 7, 1, 12, 0);

        assertTrue(dateRange.contains(testDate));
    }

    @Test
    @DisplayName("Should return true when date equals start date (inclusive)")
    void shouldReturnTrueWhenDateEqualsStartDate() {
        LocalDateTime start = LocalDateTime.of(2020, 6, 14, 0, 0);
        LocalDateTime end = LocalDateTime.of(2020, 12, 31, 23, 59);
        DateRange dateRange = new DateRange(start, end);

        assertTrue(dateRange.contains(start));
    }

    @Test
    @DisplayName("Should return true when date equals end date (inclusive)")
    void shouldReturnTrueWhenDateEqualsEndDate() {
        LocalDateTime start = LocalDateTime.of(2020, 6, 14, 0, 0);
        LocalDateTime end = LocalDateTime.of(2020, 12, 31, 23, 59);
        DateRange dateRange = new DateRange(start, end);

        assertTrue(dateRange.contains(end));
    }

    @Test
    @DisplayName("Should return false when date is before start date")
    void shouldReturnFalseWhenDateIsBeforeStartDate() {
        LocalDateTime start = LocalDateTime.of(2020, 6, 14, 0, 0);
        LocalDateTime end = LocalDateTime.of(2020, 12, 31, 23, 59);
        DateRange dateRange = new DateRange(start, end);
        LocalDateTime testDate = LocalDateTime.of(2020, 5, 1, 12, 0);

        assertFalse(dateRange.contains(testDate));
    }

    @Test
    @DisplayName("Should return false when date is after end date")
    void shouldReturnFalseWhenDateIsAfterEndDate() {
        LocalDateTime start = LocalDateTime.of(2020, 6, 14, 0, 0);
        LocalDateTime end = LocalDateTime.of(2020, 12, 31, 23, 59);
        DateRange dateRange = new DateRange(start, end);
        LocalDateTime testDate = LocalDateTime.of(2021, 1, 1, 0, 0);

        assertFalse(dateRange.contains(testDate));
    }

    @Test
    @DisplayName("Should return true when start date is null and date is before end")
    void shouldReturnTrueWhenStartDateIsNull() {
        LocalDateTime end = LocalDateTime.of(2020, 12, 31, 23, 59);
        DateRange dateRange = new DateRange(null, end);
        LocalDateTime testDate = LocalDateTime.of(2020, 1, 1, 0, 0);

        assertTrue(dateRange.contains(testDate));
    }

    @Test
    @DisplayName("Should return true when end date is null and date is after start")
    void shouldReturnTrueWhenEndDateIsNull() {
        LocalDateTime start = LocalDateTime.of(2020, 6, 14, 0, 0);
        DateRange dateRange = new DateRange(start, null);
        LocalDateTime testDate = LocalDateTime.of(2021, 1, 1, 0, 0);

        assertTrue(dateRange.contains(testDate));
    }
}
