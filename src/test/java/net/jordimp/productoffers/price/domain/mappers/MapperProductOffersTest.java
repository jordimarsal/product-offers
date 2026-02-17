package net.jordimp.productoffers.price.domain.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import net.jordimp.productoffers.helpers.PricesObjectMother;
import net.jordimp.productoffers.price.application.apimodels.ResponseProductOffer;

class MapperProductOffersTest {

    @Test
    void entityToResponse_mapsAllFields() {
        var price = PricesObjectMother.mockPricesExpected(1L);
        ResponseProductOffer resp = MapperProductOffers.entityToResponse(price);

        assertEquals(price.getProductId(), resp.getProductId());
        assertEquals(price.getBrandId(), resp.getBrandId());
        assertEquals(price.getPriceList(), resp.getPriceList());
        assertEquals(price.getPrice(), resp.getPrice());
        assertEquals(price.getCurrency(), resp.getCurrency());
        assertEquals(price.getStartDate(), resp.getStartDate());
        assertEquals(price.getEndDate(), resp.getEndDate());
    }
}
