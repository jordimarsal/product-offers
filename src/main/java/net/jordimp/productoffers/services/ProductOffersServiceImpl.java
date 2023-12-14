package net.jordimp.productoffers.services;

import static java.lang.String.format;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.Comparator;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import net.jordimp.productoffers.apimodels.ResponseProductOffer;
import net.jordimp.productoffers.entities.Prices;
import net.jordimp.productoffers.repositories.PricesRepository;

@Service
public class ProductOffersServiceImpl implements ProductOffersService {

    private final PricesRepository pricesRepository;
    private final Logger logger = Logger.getLogger(ProductOffersServiceImpl.class.getName());

    public ProductOffersServiceImpl(PricesRepository pricesRepository) {
        this.pricesRepository = pricesRepository;
    }

    @Override
    public ResponseProductOffer getInquiryPrices(final String xCorrelator, final LocalDateTime applicationDate,
        final Long productId, final Long brandId) {

        logInfo(format("### GET /inquiry-prices ### xCorrelator:%s, brandId:%d, productId:%d, appDate:%s", xCorrelator,
            brandId, productId, applicationDate.toString()));

        return pricesRepository.findByBrandIdAndProductId(brandId, productId).stream()
            .filter(
                price -> price.getStartDate().isBefore(ChronoLocalDateTime.from(applicationDate)) && price.getEndDate()
                    .isAfter(ChronoLocalDateTime.from(applicationDate)))
            .max(Comparator.comparing(Prices::getPriority))
            .map(price -> buildResponse(productId, price))
            .orElse(null);

    }

    private ResponseProductOffer buildResponse(final Long productId, final Prices price) {
        return new ResponseProductOffer()
            .setProductId(productId)
            .setBrandId(price.getBrandId())
            .setStartDate(price.getStartDate())
            .setEndDate(price.getEndDate())
            .setPriceList(price.getPriceList())
            .setPrice(price.getPrice())
            .setCurrency(price.getCurrency());
    }

    private void logInfo(String msg) {
        logger.info(msg);
    }

}
