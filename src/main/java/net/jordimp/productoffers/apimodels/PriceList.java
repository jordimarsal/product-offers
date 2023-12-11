package net.jordimp.productoffers.apimodels;

public enum PriceList {
    PRICE_LIST_1("1"),
    PRICE_LIST_2("2"),
    PRICE_LIST_3("3"),
    PRICE_LIST_4("4");

    private final String tariff;

    PriceList(final String priceList) {
        this.tariff = priceList;
    }

    public String getPriceList() {
        return tariff;
    }
}
