package net.jordimp.productoffers.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.ConstraintViolationException;
import net.jordimp.productoffers.controllers.ProductOffersController;

@RestControllerAdvice(assignableTypes = ProductOffersController.class)
public class ProductOffersControllerAdvice {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private ErrorMessage handle(ResponseStatusException exception) {
        return new ErrorMessage((HttpStatus) exception.getStatusCode(), getExceptionMessage(exception));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ErrorMessage handle(ConstraintViolationException exception) {
        return new ErrorMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ErrorMessage handle(MethodArgumentTypeMismatchException exception) {
        return new ErrorMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ErrorMessage handle(MissingServletRequestParameterException exception) {
        return new ErrorMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    private String getExceptionMessage(ResponseStatusException exception) {
        return exception.getMessage()
            .substring(exception.getStatusCode().toString().length() +1)
            .replace("\"", "");
    }

}
