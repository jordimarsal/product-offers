package net.jordimp.productoffers.price.application.mappers;

import lombok.experimental.UtilityClass;
import net.jordimp.productoffers.price.application.apimodels.ResponseProductOffer;
import net.jordimp.productoffers.price.domain.entities.Price;

@UtilityClass
public class MapperProductOffers {

    public static ResponseProductOffer entityToResponse(final Price price) {
        return new ResponseProductOffer()
                .setProductId(price.getProductId().value())
                .setBrandId(price.getBrandId().value())
                .setStartDate(price.getDateRange().startDate())
                .setEndDate(price.getDateRange().endDate())
                .setPriceList(price.getPriceList().value())
                .setPrice(price.getPrice().amount())
                .setCurrency(price.getPrice().currency());
    }
}
