package net.jordimp.productoffers.shared.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class MessageConstants {

    public static final String MSG_PRICE_NOT_FOUND = "Price not found for the given parameters";
    public static final String LOG_INFO_GET = "### GET /inquiry-prices ### xCorrelator={}, brandId={}, productId={}, appDate={}";
    public static final String LOG_WARN_404 = "### NOT_FOUND ### xCorrelator={}, brandId={}, productId={}, appDate={}";
}
