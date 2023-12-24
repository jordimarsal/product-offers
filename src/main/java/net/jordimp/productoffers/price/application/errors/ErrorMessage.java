package net.jordimp.productoffers.price.application.errors;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorMessage {
    private HttpStatus code;
    private String message;
}
