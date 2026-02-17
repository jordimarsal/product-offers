package net.jordimp.productoffers.price.application.controllers;

import static net.jordimp.productoffers.shared.constants.Patterns.INQUIRY_PRICES_FORMAT;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import net.jordimp.productoffers.price.application.apimodels.ResponseProductOffer;
import net.jordimp.productoffers.price.application.services.ProductOffersService;

@RestController
@Validated
public class ProductOffersController {

    private final ProductOffersService imp;

    public ProductOffersController(ProductOffersService imp) {
        this.imp = imp;
    }

    @GetMapping("/inquiry-prices")
    ResponseProductOffer getInquiryPrices(
        @RequestParam("applicationDate") @DateTimeFormat(pattern = INQUIRY_PRICES_FORMAT) LocalDateTime applicationDate,
        @RequestParam("productId") @NotNull @Min(1)Long productId,
        @RequestParam("brandId") @NotNull @Min(1)Long brandId,
        @RequestHeader(value = "x-correlator") String xCorrelator) {

        return imp.getInquiryPrices(xCorrelator, applicationDate, productId, brandId);
    }

}