package net.jordimp.productoffers.price.domain.services;

import static java.lang.String.format;
import static net.jordimp.productoffers.price.domain.mappers.MapperProductOffers.entityToResponse;
import static net.jordimp.productoffers.shared.constants.MessageConstants.LOG_INFO_GET;
import static net.jordimp.productoffers.shared.constants.MessageConstants.LOG_WARN_404;

import java.time.LocalDateTime;
import java.util.logging.Level;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.java.Log;
import net.jordimp.productoffers.price.application.apimodels.ResponseProductOffer;
import net.jordimp.productoffers.price.domain.ports.PriceRepositoryPort;
import net.jordimp.productoffers.price.domain.exceptions.PriceNotFoundException;

@Service
@Log
public class ProductOffersServiceImpl implements ProductOffersService {

    private final PriceRepositoryPort pricesRepository;

    public ProductOffersServiceImpl(PriceRepositoryPort pricesRepository) {
        this.pricesRepository = pricesRepository;
    }

    @Transactional
    @Override
    public ResponseProductOffer getInquiryPrices(final String xCorrelator, final LocalDateTime applicationDate,
        final Long productId, final Long brandId) {

        logInfo(format(LOG_INFO_GET, xCorrelator, brandId, productId, applicationDate.toString()));

        return pricesRepository.findBestPrice(brandId, productId, applicationDate)
            .map(price -> entityToResponse(price))
            .orElseThrow(() -> {
                log.warning(format(LOG_WARN_404, xCorrelator, brandId, productId, applicationDate));
                return new PriceNotFoundException();
            });
    }

    void logInfo(String msg) {
        if (log.isLoggable(Level.INFO)){
            log.info(msg);
        }
    }

}
