package net.jordimp.productoffers.price.application.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.jordimp.productoffers.price.application.apimodels.ResponseProductOffer;
import net.jordimp.productoffers.price.infrastructure.persistence.SpringDataPricesRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class ProductOffersControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SpringDataPricesRepository pricesRepository;

    @Test
    void fetchInquiryPricesEndpoint() throws Exception {
        final LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0);
        final Long productId = 35455L;
        final Long brandId = 1L;
        final String xCorrelator = "correlator";
        mockMvc.perform(get("/inquiry-prices")
            .param("applicationDate", applicationDate.toString())
            .param("productId", productId.toString())
            .param("brandId", brandId.toString())
                .header("x-correlator", xCorrelator)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content()
                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("priceList", is(1)));

        assert(pricesRepository.findTopByBrandIdAndProductIdAndApplicationDateBetweenOrderByPriorityDesc(
            brandId, productId, applicationDate).isPresent());
    }

    @Test
    void fetchInquiryPrices_MissingParameter_returnsBadRequest() throws Exception {
        final LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0);
        mockMvc.perform(get("/inquiry-prices")
            .param("applicationDate", applicationDate.toString())
            // missing productId
            .param("brandId", "1")
            .header("x-correlator", "correlator"))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").exists());
    }

    @Test
    void fetchInquiryPrices_TypeMismatch_returnsBadRequest() throws Exception {
        final LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0);
        mockMvc.perform(get("/inquiry-prices")
            .param("applicationDate", applicationDate.toString())
            .param("productId", "not-a-number")
            .param("brandId", "1")
            .header("x-correlator", "correlator"))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").exists());
    }

    @Test
    void fetchInquiryPrices_ValidationViolation_returnsBadRequest() throws Exception {
        final LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0);
        // productId is annotated with @Min(1)
        mockMvc.perform(get("/inquiry-prices")
            .param("applicationDate", applicationDate.toString())
            .param("productId", "0")
            .param("brandId", "1")
            .header("x-correlator", "correlator"))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").exists());
    }
}
