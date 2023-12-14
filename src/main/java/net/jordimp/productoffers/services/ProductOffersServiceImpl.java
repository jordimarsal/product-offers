package net.jordimp.productoffers.services;

import static java.lang.String.format;
import static net.jordimp.productoffers.constants.MessageConstants.LOG_INFO_GET;
import static net.jordimp.productoffers.constants.MessageConstants.LOG_WARN_404;
import static net.jordimp.productoffers.constants.MessageConstants.MSG_PRICE_NOT_FOUND;
import static net.jordimp.productoffers.mappers.MapperProductOffers.buildResponse;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.Comparator;
import java.util.logging.Level;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import lombok.extern.java.Log;
import net.jordimp.productoffers.apimodels.ResponseProductOffer;
import net.jordimp.productoffers.entities.Prices;
import net.jordimp.productoffers.repositories.PricesRepository;

@Service
@Log
public class ProductOffersServiceImpl implements ProductOffersService {

    private final PricesRepository pricesRepository;

    public ProductOffersServiceImpl(PricesRepository pricesRepository) {
        this.pricesRepository = pricesRepository;
    }

    @Transactional
    @Override
    public ResponseProductOffer getInquiryPrices(final String xCorrelator, final LocalDateTime applicationDate,
        final Long productId, final Long brandId) {

        logInfo(format(LOG_INFO_GET, xCorrelator, brandId, productId, applicationDate.toString()));

        return pricesRepository.findByBrandIdAndProductId(brandId, productId).stream().filter(
                price -> price.getStartDate().isBefore(ChronoLocalDateTime.from(applicationDate)) && price.getEndDate()
                    .isAfter(ChronoLocalDateTime.from(applicationDate))).max(Comparator.comparing(Prices::getPriority))
            .map(price -> buildResponse(productId, price))
            .orElseThrow(() -> {
                log.warning(format(LOG_WARN_404, xCorrelator, brandId, productId, applicationDate));
                return new ResponseStatusException(HttpStatus.NOT_FOUND, MSG_PRICE_NOT_FOUND);}
            );

    }

    private void logInfo(String msg) {
        if (log.isLoggable(Level.INFO)){
            log.info(msg);
        }
    }

}
