package net.jordimp.productoffers.controllers;

import static net.jordimp.productoffers.constants.Patterns.INQUIRY_PRICES_FORMAT;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.jordimp.productoffers.apimodels.ResponseProductOffer;
import net.jordimp.productoffers.services.ProductOffersService;

@RestController
public class ProductOffersController {

    private ProductOffersService imp;

    public ProductOffersController(ProductOffersService imp) {
        this.imp = imp;
    }

    @GetMapping("/inquiry-prices")
    ResponseProductOffer getInquiryPrices(
        @RequestParam("applicationDate") @DateTimeFormat(pattern = INQUIRY_PRICES_FORMAT) LocalDateTime applicationDate,
        @RequestParam("productId") Long productId, @RequestParam("brandId") Long brandId,
        @RequestHeader(value = "x-correlator") String xCorrelator) {

        return imp.getInquiryPrices(xCorrelator, applicationDate, productId, brandId);
    }

}