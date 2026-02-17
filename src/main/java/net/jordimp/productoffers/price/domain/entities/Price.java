package net.jordimp.productoffers.price.domain.entities;

import java.time.LocalDateTime;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import net.jordimp.productoffers.price.domain.valueobjects.BrandId;
import net.jordimp.productoffers.price.domain.valueobjects.DateRange;
import net.jordimp.productoffers.price.domain.valueobjects.Money;
import net.jordimp.productoffers.price.domain.valueobjects.PriceList;
import net.jordimp.productoffers.price.domain.valueobjects.Priority;
import net.jordimp.productoffers.price.domain.valueobjects.ProductId;

/**
 * Domain entity representing a price for a product. This is a pure domain entity without any
 * infrastructure dependencies (no JPA annotations).
 */
@Getter
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Price {

    @EqualsAndHashCode.Include private final Long id;

    private final BrandId brandId;

    private final ProductId productId;

    private final DateRange dateRange;

    private final PriceList priceList;

    private final Priority priority;

    private final Money price;

    /**
     * Return true when the given application date falls within this price's date range (inclusive).
     *
     * @param applicationDate the date to check
     * @return true if the date is within the range, false otherwise
     */
    public boolean isEffectiveAt(LocalDateTime applicationDate) {
        return dateRange.contains(applicationDate);
    }

    /**
     * Return true when this price matches the provided brandId and productId.
     *
     * @param brandId the brand ID to match
     * @param productId the product ID to match
     * @return true if both IDs match, false otherwise
     */
    public boolean matches(Long brandId, Long productId) {
        return this.brandId != null
                && this.brandId.value().equals(brandId)
                && this.productId != null
                && this.productId.value().equals(productId);
    }
}
