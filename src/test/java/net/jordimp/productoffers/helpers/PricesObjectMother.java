package net.jordimp.productoffers.helpers;

import static net.jordimp.productoffers.shared.constants.ConstantsTest.TEST_FORMAT;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import lombok.experimental.UtilityClass;
import net.jordimp.productoffers.price.domain.entities.Price;
import net.jordimp.productoffers.price.domain.valueobjects.BrandId;
import net.jordimp.productoffers.price.domain.valueobjects.DateRange;
import net.jordimp.productoffers.price.domain.valueobjects.Money;
import net.jordimp.productoffers.price.domain.valueobjects.PriceList;
import net.jordimp.productoffers.price.domain.valueobjects.Priority;
import net.jordimp.productoffers.price.domain.valueobjects.ProductId;

@UtilityClass
public class PricesObjectMother {

    public static List<Price> mockPrices() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TEST_FORMAT);

        Price price1 =
                new Price(
                        1L,
                        new BrandId(1L),
                        new ProductId(35455L),
                        new DateRange(
                                LocalDateTime.parse("2020-06-14-00.00.00", formatter),
                                LocalDateTime.parse("2020-12-31-23.59.59", formatter)),
                        new PriceList(1L),
                        new Priority(0),
                        new Money(BigDecimal.valueOf(35.50), "EUR"));

        Price price2 =
                new Price(
                        2L,
                        new BrandId(1L),
                        new ProductId(35455L),
                        new DateRange(
                                LocalDateTime.parse("2020-06-14-15.00.00", formatter),
                                LocalDateTime.parse("2020-06-14-18.30.00", formatter)),
                        new PriceList(2L),
                        new Priority(1),
                        new Money(BigDecimal.valueOf(25.45), "EUR"));

        Price price3 =
                new Price(
                        3L,
                        new BrandId(1L),
                        new ProductId(35455L),
                        new DateRange(
                                LocalDateTime.parse("2020-06-15-00.00.00", formatter),
                                LocalDateTime.parse("2020-06-15-11.00.00", formatter)),
                        new PriceList(3L),
                        new Priority(1),
                        new Money(BigDecimal.valueOf(30.50), "EUR"));

        Price price4 =
                new Price(
                        4L,
                        new BrandId(1L),
                        new ProductId(35455L),
                        new DateRange(
                                LocalDateTime.parse("2020-06-15-16.00.00", formatter),
                                LocalDateTime.parse("2020-12-31-23.59.59", formatter)),
                        new PriceList(4L),
                        new Priority(1),
                        new Money(BigDecimal.valueOf(38.95), "EUR"));

        return Arrays.asList(price1, price2, price3, price4);
    }

    public static List<Price> mockPricesByBrandIdAndProductId(Long brandId, Long productId) {
        return mockPrices().stream()
                .filter(
                        price ->
                                price.getBrandId().value().equals(brandId)
                                        && price.getProductId().value().equals(productId))
                .toList();
    }

    public static Price mockPricesExpected(Long priceList) {
        return mockPrices().stream()
                .filter(price -> price.getPriceList().value().equals(priceList))
                .findFirst()
                .orElse(null);
    }
}
