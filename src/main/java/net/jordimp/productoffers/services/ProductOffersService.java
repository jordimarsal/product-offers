package net.jordimp.productoffers.services;

import java.time.LocalDateTime;

import net.jordimp.productoffers.apimodels.ResponseProductOffer;

public interface ProductOffersService {

    ResponseProductOffer getInquiryPrices(String xCorrelator, LocalDateTime applicationDate, Long productId,
        Long brandId);

}
