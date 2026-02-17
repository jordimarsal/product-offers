package net.jordimp.productoffers.price.application.services;

import static net.jordimp.productoffers.price.application.mappers.MapperProductOffers.entityToResponse;
import static net.jordimp.productoffers.shared.constants.MessageConstants.LOG_INFO_GET;
import static net.jordimp.productoffers.shared.constants.MessageConstants.LOG_WARN_404;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import net.jordimp.productoffers.price.application.apimodels.ResponseProductOffer;
import net.jordimp.productoffers.price.domain.ports.PriceRepositoryPort;
import net.jordimp.productoffers.price.domain.exceptions.PriceNotFoundException;

@Service
@Slf4j
public class ProductOffersServiceImpl implements ProductOffersService {

    private final PriceRepositoryPort pricesRepository;

    public ProductOffersServiceImpl(PriceRepositoryPort pricesRepository) {
        this.pricesRepository = pricesRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public ResponseProductOffer getInquiryPrices(final String xCorrelator, final LocalDateTime applicationDate,
        final Long productId, final Long brandId) {

        log.info(LOG_INFO_GET, xCorrelator, brandId, productId, applicationDate);

        return pricesRepository.findBestPrice(brandId, productId, applicationDate)
            .map(price -> entityToResponse(price))
            .orElseThrow(() -> {
                log.warn(LOG_WARN_404, xCorrelator, brandId, productId, applicationDate);
                return new PriceNotFoundException();
            });
    }

}
