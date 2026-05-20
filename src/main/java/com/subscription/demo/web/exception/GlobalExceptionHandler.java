package com.subscription.demo.web.exception;

import com.subscription.demo.web.response.ErrorResponse;
import com.subscription.demo.web.response.ValidationErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ValidationErrorResponse handleValidationErrors(MethodArgumentNotValidException exception){

        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult().getFieldErrors().forEach(
                error -> errors.put(error.getField(), error.getDefaultMessage())
        );

        return new ValidationErrorResponse(HttpStatus.BAD_REQUEST.value(),
                                        "Validation Failed",
                                                 errors);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(SubscriptionNotFoundException.class)
    public ErrorResponse handleSubscriptionNotFound(SubscriptionNotFoundException exception){

        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage());

    }
}
