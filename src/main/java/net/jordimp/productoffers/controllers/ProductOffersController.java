package net.jordimp.productoffers.controllers;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.jordimp.productoffers.apimodels.ResponseProductOffer;
import net.jordimp.productoffers.services.ProductOffersService;
import reactor.core.publisher.Mono;

import static net.jordimp.productoffers.utils.ReactiveRequestContext.getRequest;

@RestController
public class ProductOffersController {

    private ProductOffersService imp;

    public ProductOffersController(ProductOffersService imp) {
        this.imp = imp;
    }

    @GetMapping("/inquiry-prices")
    Mono<ResponseProductOffer> getInquiryPrices(ServerHttpRequest httpRequest,
        @RequestParam("applicationDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime applicationDate,
        @RequestParam("productId") Long productId, @RequestParam("brandId") Long brandId,
        @RequestHeader(value = "x-correlator") String xCorrelator) {

        return getRequest().flatMap(
            request -> imp.getInquiryPrices(request, xCorrelator, applicationDate, productId, brandId));
    }

}
