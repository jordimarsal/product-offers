package net.jordimp.productoffers.price.domain.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "PRICES")
public class Prices {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "BRAND_ID", nullable = false)
    private Long brandId;

    @Column(name = "START_DATE", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "END_DATE", nullable = false)
    private LocalDateTime endDate;

    @Column(name = "PRICE_LIST", nullable = false)
    private Long priceList;

    @Column(name = "PRODUCT_ID", nullable = false)
    private Long productId;

    @Column(name = "PRIORITY", nullable = false)
    private Integer priority;

    @Column(name = "PRICE", nullable = false)
    private BigDecimal price;

    @Column(name = "CURRENCY", nullable = false)
    private String currency;

    /**
     * Return true when the given application date falls between startDate and endDate (inclusive).
     */
    public boolean isEffectiveAt(LocalDateTime applicationDate) {
        return (startDate == null || !applicationDate.isBefore(startDate))
            && (endDate == null || !applicationDate.isAfter(endDate));
    }

    /**
     * Return true when this price matches the provided brandId and productId.
     */
    public boolean matches(Long brandId, Long productId) {
        return this.brandId != null && this.brandId.equals(brandId)
            && this.productId != null && this.productId.equals(productId);
    }

}

