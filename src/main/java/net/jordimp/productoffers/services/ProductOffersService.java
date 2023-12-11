package net.jordimp.productoffers.services;

import java.time.LocalDateTime;

import org.springframework.http.server.reactive.ServerHttpRequest;

import net.jordimp.productoffers.apimodels.ResponseProductOffer;
import reactor.core.publisher.Mono;

public interface ProductOffersService {

    Mono<ResponseProductOffer> getInquiryPrices(ServerHttpRequest httpRequest, String xCorrelator,
        LocalDateTime applicationDate, Long productId, Long brandId);

}
