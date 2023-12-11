package net.jordimp.productoffers.services;

import java.time.LocalDateTime;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;

import net.jordimp.productoffers.apimodels.PriceList;
import net.jordimp.productoffers.apimodels.ResponseProductOffer;
import reactor.core.publisher.Mono;

@Service
public class ProductOffersServiceImpl implements ProductOffersService {

    @Override
    public Mono<ResponseProductOffer> getInquiryPrices(final ServerHttpRequest httpRequest, final String xCorrelator,
        final LocalDateTime applicationDate, final Long productId, final Long brandId) {

        return Mono.just(new ResponseProductOffer().setProductId(productId.toString()).setBrandId(brandId.toString())
            .setAppDate(applicationDate.toString()).setPriceList(PriceList.PRICE_LIST_2.getPriceList())
            .setPrice("35.50").setCurrency("EUR"));
    }

}
