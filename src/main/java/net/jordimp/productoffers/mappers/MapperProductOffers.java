package net.jordimp.productoffers.mappers;

import lombok.experimental.UtilityClass;
import net.jordimp.productoffers.apimodels.ResponseProductOffer;
import net.jordimp.productoffers.entities.Prices;

@UtilityClass
public class MapperProductOffers {

    public static ResponseProductOffer buildResponse(final Long productId, final Prices price) {
        return new ResponseProductOffer().setProductId(productId).setBrandId(price.getBrandId())
            .setStartDate(price.getStartDate()).setEndDate(price.getEndDate()).setPriceList(price.getPriceList())
            .setPrice(price.getPrice()).setCurrency(price.getCurrency());
    }
}
