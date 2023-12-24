package net.jordimp.productoffers.price.domain.mappers;

import lombok.experimental.UtilityClass;
import net.jordimp.productoffers.price.application.apimodels.ResponseProductOffer;
import net.jordimp.productoffers.price.domain.entities.Prices;

@UtilityClass
public class MapperProductOffers {

    public static ResponseProductOffer entityToResponse(final Long productId, final Prices price) {
        return new ResponseProductOffer().setProductId(productId).setBrandId(price.getBrandId())
            .setStartDate(price.getStartDate()).setEndDate(price.getEndDate()).setPriceList(price.getPriceList())
            .setPrice(price.getPrice()).setCurrency(price.getCurrency());
    }
}
