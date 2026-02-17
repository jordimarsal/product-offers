package net.jordimp.productoffers.price.domain.valueobjects;

import java.time.LocalDateTime;

/**
 * Value Object representing a date range with start and end dates.
 *
 * @param startDate the start date of the range (can be null for open start)
 * @param endDate the end date of the range (can be null for open end)
 */
public record DateRange(LocalDateTime startDate, LocalDateTime endDate) {

    public DateRange {
        if (startDate != null && endDate != null && startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }
    }

    /**
     * Returns the start date of the range.
     *
     * @return the start date (may be null)
     */
    public LocalDateTime startDate() {
        return startDate;
    }

    /**
     * Returns the end date of the range.
     *
     * @return the end date (may be null)
     */
    public LocalDateTime endDate() {
        return endDate;
    }

    /**
     * Checks if the given date falls within this date range (inclusive).
     *
     * @param date the date to check
     * @return true if the date is within the range, false otherwise
     */
    public boolean contains(LocalDateTime date) {
        return (startDate == null || !date.isBefore(startDate))
                && (endDate == null || !date.isAfter(endDate));
    }
}
