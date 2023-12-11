package net.jordimp.productoffers.services;

import java.time.LocalDateTime;

import net.jordimp.productoffers.apimodels.ResponseProductOffer;
import reactor.core.publisher.Mono;

public interface ProductOffersService {

    Mono<ResponseProductOffer> getInquiryPrices(String xCorrelator, LocalDateTime applicationDate, Long productId,
        Long brandId);

}
