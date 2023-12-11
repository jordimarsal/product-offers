package net.jordimp.productoffers.apimodels;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ResponseProductOffer {
    private  String productId;
    private  String brandId;
    private  String appDate;
    private  String priceList;
    private  String price;
    private  String currency;
}
