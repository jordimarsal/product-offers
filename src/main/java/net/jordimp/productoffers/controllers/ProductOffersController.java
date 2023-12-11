package net.jordimp.productoffers.controllers;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

import net.jordimp.productoffers.apimodels.ResponseProductOffer;
import net.jordimp.productoffers.services.ProductOffersService;
import reactor.core.publisher.Mono;

@RestController
public class ProductOffersController {

    private ProductOffersService imp;

    public ProductOffersController(ProductOffersService imp) {
        this.imp = imp;
    }

    @GetMapping("/inquiry-prices")
    Mono<ResponseProductOffer> getInquiryPrices(
        @RequestParam("applicationDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime applicationDate,
        @RequestParam("productId") Long productId, @RequestParam("brandId") Long brandId,
        @RequestHeader(value = "x-correlator") String xCorrelator, ServerWebExchange exchange) {

        return imp.getInquiryPrices(xCorrelator, applicationDate, productId, brandId);
    }

}
// ?applicationDate=2023-01-01%2012:00:00&productId=123&brandId=456