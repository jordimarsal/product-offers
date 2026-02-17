package net.jordimp.productoffers.price.application.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.ConstraintViolationException;
import net.jordimp.productoffers.price.application.controllers.ProductOffersController;

@RestControllerAdvice(assignableTypes = ProductOffersController.class)
public class ProductOffersControllerAdvice {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private ErrorMessage handle(ResponseStatusException exception) {
        return new ErrorMessage(((HttpStatus) exception.getStatusCode()).value(), getExceptionMessage(exception),
            java.time.OffsetDateTime.now().toString(), org.slf4j.MDC.get("correlator"));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private ErrorMessage handle(net.jordimp.productoffers.price.domain.exceptions.PriceNotFoundException ex) {
        return new ErrorMessage(HttpStatus.NOT_FOUND.value(), ex.getMessage(), java.time.OffsetDateTime.now().toString(), org.slf4j.MDC.get("correlator"));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ErrorMessage handle(ConstraintViolationException exception) {
        return new ErrorMessage(HttpStatus.BAD_REQUEST.value(), exception.getMessage(), java.time.OffsetDateTime.now().toString(), org.slf4j.MDC.get("correlator"));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ErrorMessage handle(MethodArgumentTypeMismatchException exception) {
        return new ErrorMessage(HttpStatus.BAD_REQUEST.value(), exception.getMessage(), java.time.OffsetDateTime.now().toString(), org.slf4j.MDC.get("correlator"));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ErrorMessage handle(MissingServletRequestParameterException exception) {
        return new ErrorMessage(HttpStatus.BAD_REQUEST.value(), exception.getMessage(), java.time.OffsetDateTime.now().toString(), org.slf4j.MDC.get("correlator"));
    }

    private String getExceptionMessage(ResponseStatusException exception) {
        return java.util.Optional.ofNullable(exception.getReason()).orElse(exception.getMessage());
    }

}
