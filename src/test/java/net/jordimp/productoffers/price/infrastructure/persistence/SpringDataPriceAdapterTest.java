package net.jordimp.productoffers.price.infrastructure.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import net.jordimp.productoffers.helpers.PricesObjectMother;
import net.jordimp.productoffers.price.domain.entities.Prices;

class SpringDataPriceAdapterTest {

    @Mock
    private SpringDataPricesRepository repository;

    private SpringDataPriceAdapter adapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        adapter = new SpringDataPriceAdapter(repository);
    }

    @Test
    void whenRepositoryReturnsPrice_adapterReturnsSame() {
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0);
        Prices expected = PricesObjectMother.mockPricesExpected(1L);

        when(repository.findTopByBrandIdAndProductIdAndApplicationDateBetweenOrderByPriorityDesc(
            1L, 35455L, applicationDate)).thenReturn(Optional.of(expected));

        Optional<Prices> result = adapter.findTopByBrandIdAndProductIdAndApplicationDateBetweenOrderByPriorityDesc(
            1L, 35455L, applicationDate);

        assertTrue(result.isPresent());
        assertEquals(expected, result.get());
        verify(repository, times(1)).findTopByBrandIdAndProductIdAndApplicationDateBetweenOrderByPriorityDesc(
            1L, 35455L, applicationDate);
    }

    @Test
    void whenRepositoryReturnsEmpty_adapterReturnsEmpty() {
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0);

        when(repository.findTopByBrandIdAndProductIdAndApplicationDateBetweenOrderByPriorityDesc(
            1L, 35455L, applicationDate)).thenReturn(Optional.empty());

        Optional<Prices> result = adapter.findTopByBrandIdAndProductIdAndApplicationDateBetweenOrderByPriorityDesc(
            1L, 35455L, applicationDate);

        assertTrue(result.isEmpty());
    }
}
