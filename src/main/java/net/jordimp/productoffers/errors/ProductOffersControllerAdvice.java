package net.jordimp.productoffers.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import net.jordimp.productoffers.controllers.ProductOffersController;

@RestControllerAdvice(assignableTypes = ProductOffersController.class)
public class ProductOffersControllerAdvice {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private ErrorMessage handle(ResponseStatusException exception) {
        return new ErrorMessage(exception.getStatusCode().toString(), getExceptionMessage(exception));
    }

    private String getExceptionMessage(ResponseStatusException exception) {
        return exception.getMessage()
            .substring(exception.getStatusCode().toString().length() +1)
            .replace("\"", "");
    }

}
