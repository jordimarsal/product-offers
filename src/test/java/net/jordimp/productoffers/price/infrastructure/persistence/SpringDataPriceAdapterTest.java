package net.jordimp.productoffers.price.infrastructure.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import net.jordimp.productoffers.price.domain.entities.Price;
import net.jordimp.productoffers.price.domain.valueobjects.BrandId;
import net.jordimp.productoffers.price.domain.valueobjects.DateRange;
import net.jordimp.productoffers.price.domain.valueobjects.Money;
import net.jordimp.productoffers.price.domain.valueobjects.PriceList;
import net.jordimp.productoffers.price.domain.valueobjects.Priority;
import net.jordimp.productoffers.price.domain.valueobjects.ProductId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class SpringDataPriceAdapterTest {

    @Mock private SpringDataPricesRepository repository;

    private SpringDataPriceAdapter adapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        adapter = new SpringDataPriceAdapter(repository);
    }

    @Test
    void whenRepositoryReturnsPrice_adapterReturnsSame() {
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0);

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

        Price expected =
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

        when(repository.findTopByBrandIdAndProductIdAndApplicationDateBetweenOrderByPriorityDesc(
                        1L, 35455L, applicationDate))
                .thenReturn(Optional.of(entity));

        Optional<Price> result = adapter.findBestPrice(1L, 35455L, applicationDate);

        assertTrue(result.isPresent());
        assertEquals(expected.getId(), result.get().getId());
        assertEquals(expected.getBrandId().value(), result.get().getBrandId().value());
        assertEquals(expected.getProductId().value(), result.get().getProductId().value());
        assertEquals(expected.getPriceList().value(), result.get().getPriceList().value());
        assertEquals(expected.getPriority().value(), result.get().getPriority().value());
        verify(repository, times(1))
                .findTopByBrandIdAndProductIdAndApplicationDateBetweenOrderByPriorityDesc(
                        1L, 35455L, applicationDate);
    }

    @Test
    void whenRepositoryReturnsEmpty_adapterReturnsEmpty() {
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0);

        when(repository.findTopByBrandIdAndProductIdAndApplicationDateBetweenOrderByPriorityDesc(
                        1L, 35455L, applicationDate))
                .thenReturn(Optional.empty());

        var result = adapter.findBestPrice(1L, 35455L, applicationDate);

        assertTrue(result.isEmpty());
    }
}
