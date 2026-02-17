package net.jordimp.productoffers.price.domain.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class PriceNotFoundExceptionTest {

    @Test
    void messageConstructor_setsProvidedMessage() {
        var ex = new PriceNotFoundException("custom reason");
        assertEquals("custom reason", ex.getMessage());
    }
}
