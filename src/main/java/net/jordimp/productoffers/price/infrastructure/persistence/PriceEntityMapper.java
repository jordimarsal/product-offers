package net.jordimp.productoffers.price.infrastructure.persistence;

import lombok.experimental.UtilityClass;
import net.jordimp.productoffers.price.domain.entities.Price;
import net.jordimp.productoffers.price.domain.valueobjects.BrandId;
import net.jordimp.productoffers.price.domain.valueobjects.DateRange;
import net.jordimp.productoffers.price.domain.valueobjects.Money;
import net.jordimp.productoffers.price.domain.valueobjects.PriceList;
import net.jordimp.productoffers.price.domain.valueobjects.Priority;
import net.jordimp.productoffers.price.domain.valueobjects.ProductId;

/** Mapper utility class to convert between Price (domain entity) and PriceEntity (JPA entity). */
@UtilityClass
public class PriceEntityMapper {

    /**
     * Converts a PriceEntity (JPA) to a Price (domain).
     *
     * @param entity the JPA entity to convert
     * @return the domain entity
     */
    public Price toDomain(PriceEntity entity) {
        if (entity == null) {
            return null;
        }

        return new Price(
                entity.getId(),
                new BrandId(entity.getBrandId()),
                new ProductId(entity.getProductId()),
                new DateRange(entity.getStartDate(), entity.getEndDate()),
                new PriceList(entity.getPriceList()),
                new Priority(entity.getPriority()),
                new Money(entity.getPrice(), entity.getCurrency()));
    }

    /**
     * Converts a Price (domain) to a PriceEntity (JPA).
     *
     * @param domain the domain entity to convert
     * @return the JPA entity
     */
    public PriceEntity toEntity(Price domain) {
        if (domain == null) {
            return null;
        }

        PriceEntity entity = new PriceEntity();
        entity.setId(domain.getId());
        entity.setBrandId(domain.getBrandId().value());
        entity.setProductId(domain.getProductId().value());
        entity.setStartDate(domain.getDateRange().startDate());
        entity.setEndDate(domain.getDateRange().endDate());
        entity.setPriceList(domain.getPriceList().value());
        entity.setPriority(domain.getPriority().value());
        entity.setPrice(domain.getPrice().amount());
        entity.setCurrency(domain.getPrice().currency());

        return entity;
    }
}
