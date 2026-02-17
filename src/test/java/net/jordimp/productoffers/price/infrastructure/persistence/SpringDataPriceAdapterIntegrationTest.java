package net.jordimp.productoffers.price.infrastructure.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.Optional;
import net.jordimp.productoffers.price.domain.entities.Price;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(SpringDataPriceAdapter.class)
class SpringDataPriceAdapterIntegrationTest {

    @Autowired private SpringDataPricesRepository repository;

    @Autowired private SpringDataPriceAdapter adapter;

    @Test
    void adapterDelegatesToRepository_andReturnsBestPrice() {
        // repository is pre-populated via src/main/resources/data.sql for tests; do not insert
        // duplicates

        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 16, 0);

        Optional<Price> result = adapter.findBestPrice(1L, 35455L, applicationDate);

        assertTrue(result.isPresent());
        assertEquals(2L, result.get().getPriceList().value());

        // verify repository query returns the expected ordered list (priority DESC)
        var list =
                repository
                        .findByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
                                1L, 35455L, applicationDate, applicationDate);
        assertEquals(2L, list.get(0).getPriceList());
    }
}
