package net.jordimp.productoffers.price.application.errors;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorMessage {
    private int status;
    private String message;
    private String timestamp;
    private String correlator;
}
