package net.jordimp.productoffers.price.domain.services;

import java.time.LocalDateTime;

import net.jordimp.productoffers.price.application.apimodels.ResponseProductOffer;

public interface ProductOffersService {

    ResponseProductOffer getInquiryPrices(String xCorrelator, LocalDateTime applicationDate, Long productId,
        Long brandId);

}
