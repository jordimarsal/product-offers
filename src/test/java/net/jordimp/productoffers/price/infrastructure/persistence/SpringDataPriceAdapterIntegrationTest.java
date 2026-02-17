package net.jordimp.productoffers.price.infrastructure.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import net.jordimp.productoffers.helpers.PricesObjectMother;
import net.jordimp.productoffers.price.domain.entities.Prices;

@DataJpaTest
@Import(SpringDataPriceAdapter.class)
class SpringDataPriceAdapterIntegrationTest {

    @Autowired
    private SpringDataPricesRepository repository;

    @Autowired
    private SpringDataPriceAdapter adapter;

    @Test
    void adapterDelegatesToRepository_andReturnsBestPrice() {
        List<Prices> prices = PricesObjectMother.mockPrices();
        repository.saveAll(prices);

        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 16, 0);

        Optional<Prices> result = adapter.findBestPrice(1L, 35455L, applicationDate);

        assertTrue(result.isPresent());
        assertEquals(2L, result.get().getPriceList());
    }
}
