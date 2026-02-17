package net.jordimp.productoffers.price.infrastructure.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import net.jordimp.productoffers.price.domain.entities.Price;
import net.jordimp.productoffers.price.domain.valueobjects.BrandId;
import net.jordimp.productoffers.price.domain.valueobjects.DateRange;
import net.jordimp.productoffers.price.domain.valueobjects.Money;
import net.jordimp.productoffers.price.domain.valueobjects.PriceList;
import net.jordimp.productoffers.price.domain.valueobjects.Priority;
import net.jordimp.productoffers.price.domain.valueobjects.ProductId;
import org.junit.jupiter.api.Test;

class PriceEntityMapperTest {

    @Test
    void toDomain_convertsEntityToPrice() {
        PriceEntity entity = new PriceEntity();
        entity.setId(1L);
        entity.setBrandId(1L);
        entity.setProductId(35455L);
        entity.setStartDate(LocalDateTime.of(2020, 6, 14, 0, 0));
        entity.setEndDate(LocalDateTime.of(2020, 12, 31, 23, 59));
        entity.setPriceList(1L);
        entity.setPriority(0);
        entity.setPrice(BigDecimal.valueOf(35.50));
        entity.setCurrency("EUR");

        Price domain = PriceEntityMapper.toDomain(entity);

        assertEquals(entity.getId(), domain.getId());
        assertEquals(entity.getBrandId(), domain.getBrandId().value());
        assertEquals(entity.getProductId(), domain.getProductId().value());
        assertEquals(entity.getStartDate(), domain.getDateRange().startDate());
        assertEquals(entity.getEndDate(), domain.getDateRange().endDate());
        assertEquals(entity.getPriceList(), domain.getPriceList().value());
        assertEquals(entity.getPriority(), domain.getPriority().value());
        assertEquals(entity.getPrice(), domain.getPrice().amount());
        assertEquals(entity.getCurrency(), domain.getPrice().currency());
    }

    @Test
    void toDomain_returnsNullWhenEntityIsNull() {
        Price domain = PriceEntityMapper.toDomain(null);
        assertNull(domain);
    }

    @Test
    void toEntity_convertsPriceToEntity() {
        Price domain =
                new Price(
                        1L,
                        new BrandId(1L),
                        new ProductId(35455L),
                        new DateRange(
                                LocalDateTime.of(2020, 6, 14, 0, 0),
                                LocalDateTime.of(2020, 12, 31, 23, 59)),
                        new PriceList(1L),
                        new Priority(0),
                        new Money(BigDecimal.valueOf(35.50), "EUR"));

        PriceEntity entity = PriceEntityMapper.toEntity(domain);

        assertEquals(domain.getId(), entity.getId());
        assertEquals(domain.getBrandId().value(), entity.getBrandId());
        assertEquals(domain.getProductId().value(), entity.getProductId());
        assertEquals(domain.getDateRange().startDate(), entity.getStartDate());
        assertEquals(domain.getDateRange().endDate(), entity.getEndDate());
        assertEquals(domain.getPriceList().value(), entity.getPriceList());
        assertEquals(domain.getPriority().value(), entity.getPriority());
        assertEquals(domain.getPrice().amount(), entity.getPrice());
        assertEquals(domain.getPrice().currency(), entity.getCurrency());
    }

    @Test
    void toEntity_returnsNullWhenPriceIsNull() {
        PriceEntity entity = PriceEntityMapper.toEntity(null);
        assertNull(entity);
    }

    @Test
    void roundTrip_domainToEntityToDomain_preservesData() {
        Price original =
                new Price(
                        1L,
                        new BrandId(1L),
                        new ProductId(35455L),
                        new DateRange(
                                LocalDateTime.of(2020, 6, 14, 0, 0),
                                LocalDateTime.of(2020, 12, 31, 23, 59)),
                        new PriceList(1L),
                        new Priority(0),
                        new Money(BigDecimal.valueOf(35.50), "EUR"));

        PriceEntity entity = PriceEntityMapper.toEntity(original);
        Price converted = PriceEntityMapper.toDomain(entity);

        assertEquals(original.getId(), converted.getId());
        assertEquals(original.getBrandId().value(), converted.getBrandId().value());
        assertEquals(original.getProductId().value(), converted.getProductId().value());
        assertEquals(original.getDateRange().startDate(), converted.getDateRange().startDate());
        assertEquals(original.getDateRange().endDate(), converted.getDateRange().endDate());
        assertEquals(original.getPriceList().value(), converted.getPriceList().value());
        assertEquals(original.getPriority().value(), converted.getPriority().value());
        assertEquals(original.getPrice().amount(), converted.getPrice().amount());
        assertEquals(original.getPrice().currency(), converted.getPrice().currency());
    }

    @Test
    void roundTrip_entityToDomainToEntity_preservesData() {
        PriceEntity original = new PriceEntity();
        original.setId(1L);
        original.setBrandId(1L);
        original.setProductId(35455L);
        original.setStartDate(LocalDateTime.of(2020, 6, 14, 0, 0));
        original.setEndDate(LocalDateTime.of(2020, 12, 31, 23, 59));
        original.setPriceList(1L);
        original.setPriority(0);
        original.setPrice(BigDecimal.valueOf(35.50));
        original.setCurrency("EUR");

        Price domain = PriceEntityMapper.toDomain(original);
        PriceEntity converted = PriceEntityMapper.toEntity(domain);

        assertEquals(original.getId(), converted.getId());
        assertEquals(original.getBrandId(), converted.getBrandId());
        assertEquals(original.getProductId(), converted.getProductId());
        assertEquals(original.getStartDate(), converted.getStartDate());
        assertEquals(original.getEndDate(), converted.getEndDate());
        assertEquals(original.getPriceList(), converted.getPriceList());
        assertEquals(original.getPriority(), converted.getPriority());
        assertEquals(original.getPrice(), converted.getPrice());
        assertEquals(original.getCurrency(), converted.getCurrency());
    }
}
