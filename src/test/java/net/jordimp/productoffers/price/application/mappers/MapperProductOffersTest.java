package net.jordimp.productoffers.price.application.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import net.jordimp.productoffers.helpers.PricesObjectMother;
import net.jordimp.productoffers.price.application.apimodels.ResponseProductOffer;
import org.junit.jupiter.api.Test;

class MapperProductOffersTest {

    @Test
    void entityToResponse_mapsAllFields() {
        var price = PricesObjectMother.mockPricesExpected(1L);
        ResponseProductOffer resp = MapperProductOffers.entityToResponse(price);

        assertEquals(price.getProductId().value(), resp.getProductId());
        assertEquals(price.getBrandId().value(), resp.getBrandId());
        assertEquals(price.getPriceList().value(), resp.getPriceList());
        assertEquals(price.getPrice().amount(), resp.getPrice());
        assertEquals(price.getPrice().currency(), resp.getCurrency());
        assertEquals(price.getDateRange().startDate(), resp.getStartDate());
        assertEquals(price.getDateRange().endDate(), resp.getEndDate());
    }
}
