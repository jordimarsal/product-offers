package net.jordimp.productoffers.price.application.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.jordimp.productoffers.price.application.apimodels.ResponseProductOffer;
import net.jordimp.productoffers.price.domain.repositories.PricesRepository;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
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

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class ProductOffersControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PricesRepository pricesRepository;

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
            brandId,productId, applicationDate).isPresent());
    }

}
