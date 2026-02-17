package net.jordimp.productoffers.price.application.controllers;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import net.jordimp.productoffers.price.application.services.ProductOffersService;
import net.jordimp.productoffers.price.domain.exceptions.PriceNotFoundException;
import net.jordimp.productoffers.price.infrastructure.persistence.SpringDataPriceAdapter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

@WebMvcTest(
        controllers = ProductOffersController.class,
        excludeAutoConfiguration = {
            org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration.class,
            org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration.class
        },
        excludeFilters =
                @org.springframework.context.annotation.ComponentScan.Filter(
                        type = org.springframework.context.annotation.FilterType.ASSIGNABLE_TYPE,
                        classes =
                                net.jordimp.productoffers.price.infrastructure.persistence
                                        .SpringDataPriceAdapter.class))
class ProductOffersControllerAdviceTest {

    @Autowired private MockMvc mockMvc;

    @MockBean private ProductOffersService productOffersService;

    @MockBean private SpringDataPriceAdapter springDataPriceAdapter;

    @Test
    void whenServiceThrowsPriceNotFound_thenControllerAdviceReturns404() throws Exception {
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0);
        when(productOffersService.getInquiryPrices("correlator", applicationDate, 35455L, 1L))
                .thenThrow(new PriceNotFoundException());

        mockMvc.perform(
                        get("/inquiry-prices")
                                .param("applicationDate", applicationDate.toString())
                                .param("productId", "35455")
                                .param("brandId", "1")
                                .header("x-correlator", "correlator"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Price not found for the given parameters")))
                .andExpect(jsonPath("$.correlator", is("correlator")));
    }

    @Test
    void whenServiceThrowsResponseStatusException_thenControllerAdviceUsesReason()
            throws Exception {
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0);
        when(productOffersService.getInquiryPrices("correlator", applicationDate, 35455L, 1L))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "custom reason"));

        mockMvc.perform(
                        get("/inquiry-prices")
                                .param("applicationDate", applicationDate.toString())
                                .param("productId", "35455")
                                .param("brandId", "1")
                                .header("x-correlator", "correlator"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("custom reason")))
                .andExpect(jsonPath("$.correlator", is("correlator")));
    }

    @Test
    void whenServiceThrowsGenericException_thenControllerAdviceReturns500() throws Exception {
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0);
        when(productOffersService.getInquiryPrices("correlator", applicationDate, 35455L, 1L))
                .thenThrow(new RuntimeException("Unexpected error"));

        mockMvc.perform(
                        get("/inquiry-prices")
                                .param("applicationDate", applicationDate.toString())
                                .param("productId", "35455")
                                .param("brandId", "1")
                                .header("x-correlator", "correlator"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message", is("Internal server error")))
                .andExpect(jsonPath("$.correlator", is("correlator")));
    }
}
