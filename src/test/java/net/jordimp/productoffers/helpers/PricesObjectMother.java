package net.jordimp.productoffers.helpers;

import static net.jordimp.productoffers.constants.Patterns.TEST_FORMAT;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import lombok.experimental.UtilityClass;
import net.jordimp.productoffers.entities.Prices;

@UtilityClass
public class PricesObjectMother {

    public static List<Prices> mockPrices() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TEST_FORMAT);

        Prices price1 = new Prices();
        price1.setBrandId(1L);
        price1.setStartDate(LocalDateTime.parse("2020-06-14-00.00.00", formatter));
        price1.setEndDate(LocalDateTime.parse("2020-12-31-23.59.59", formatter));
        price1.setPriceList(1L);
        price1.setProductId(35455L);
        price1.setPriority(0);
        price1.setPrice(BigDecimal.valueOf(35.50));
        price1.setCurrency("EUR");

        Prices price2 = new Prices();
        price2.setBrandId(1L);
        price2.setStartDate(LocalDateTime.parse("2020-06-14-15.00.00", formatter));
        price2.setEndDate(LocalDateTime.parse("2020-06-14-18.30.00", formatter));
        price2.setPriceList(2L);
        price2.setProductId(35455L);
        price2.setPriority(1);
        price2.setPrice(BigDecimal.valueOf(25.45));
        price2.setCurrency("EUR");

        Prices price3 = new Prices();
        price3.setBrandId(1L);
        price3.setStartDate(LocalDateTime.parse("2020-06-15-00.00.00", formatter));
        price3.setEndDate(LocalDateTime.parse("2020-06-15-11.00.00", formatter));
        price3.setPriceList(3L);
        price3.setProductId(35455L);
        price3.setPriority(1);
        price3.setPrice(BigDecimal.valueOf(30.50));
        price3.setCurrency("EUR");

        Prices price4 = new Prices();
        price4.setBrandId(1L);
        price4.setStartDate(LocalDateTime.parse("2020-06-15-16.00.00", formatter));
        price4.setEndDate(LocalDateTime.parse("2020-12-31-23.59.59", formatter));
        price4.setPriceList(4L);
        price4.setProductId(35455L);
        price4.setPriority(1);
        price4.setPrice(BigDecimal.valueOf(38.95));
        price4.setCurrency("EUR");

        return Arrays.asList(price1, price2, price3, price4);
    }

    public static List<Prices> mockPricesByBrandIdAndProductId(Long brandId, Long productId) {
        return mockPrices().stream()
            .filter(price -> price.getBrandId().equals(brandId) && price.getProductId().equals(productId))
            .toList();
    }
}
