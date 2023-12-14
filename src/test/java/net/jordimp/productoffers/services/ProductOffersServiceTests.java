package net.jordimp.productoffers.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import net.jordimp.productoffers.helpers.PricesObjectMother;
import net.jordimp.productoffers.repositories.PricesRepository;

class ProductOffersServiceTests {

    @InjectMocks
    private ProductOffersServiceImpl service;

    @Mock
    private PricesRepository pricesRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);

    }

    private static Stream<Arguments> getOffersParams() {
        return Stream.of(
            // shouldRetrieveCorrectPriceList
            Arguments.of(LocalDateTime.of(2020, 6,14,10,0), 35455L, 1L, 1L),
            Arguments.of(LocalDateTime.of(2020, 6,14,16,0), 35455L, 1L, 2L),
            Arguments.of(LocalDateTime.of(2020, 6,14,21,0), 35455L, 1L, 1L),
            Arguments.of(LocalDateTime.of(2020, 6,15,10,0), 35455L, 1L, 3L),
            Arguments.of(LocalDateTime.of(2020, 6,16,21,0), 35455L, 1L, 4L)
        );
    }

    @ParameterizedTest
    @MethodSource("getOffersParams")
    void testGetInquiryPrices(LocalDateTime applicationDate, Long productId, Long brandId, Long expectedPriceList) {
        // When
        when(pricesRepository.findByBrandIdAndProductId(brandId, productId)).thenReturn(PricesObjectMother.mockPrices());

        // Then
        var result = service.getInquiryPrices("correlator", applicationDate, productId, brandId);

        // Assert
        assertNotNull(result);
        assertEquals(expectedPriceList, result.getPriceList());
    }

    private static Stream<Arguments> getOffersParamsError() {
        return Stream.of(
            // shouldReturnErrorWhenBrandIdNotFound
            Arguments.of(LocalDateTime.of(2019, 6,14,10,0), 35455L, 0L, null)
        );
    }

    @ParameterizedTest
    @MethodSource("getOffersParamsError")
    void testGetInquiryPricesErrorCases(LocalDateTime applicationDate, Long productId, Long brandId, Long expectedPriceList) {
        // When
        when(pricesRepository.findByBrandIdAndProductId(brandId, productId)).thenReturn(PricesObjectMother.mockPrices());

        // Then
        var result = service.getInquiryPrices("correlator", applicationDate, productId, brandId);

        // Assert
        assertNull(result);
        //assertEquals(expectedPriceList, result.getPriceList());
    }

}
