package net.jordimp.productoffers.price.domain.exceptions;

public class PriceNotFoundException extends RuntimeException {
    public PriceNotFoundException() {
        super("Price not found for the given parameters");
    }

    public PriceNotFoundException(String message) {
        super(message);
    }
}
