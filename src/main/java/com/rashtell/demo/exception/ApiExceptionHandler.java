package com.rashtell.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice //This class should be served for exception handling
public class ApiExceptionHandler {

    @ExceptionHandler(value = {ApiRequestException.class}) //This method should be used for exception handling
    public ResponseEntity<Object> handleApiRequestException(
            ApiRequestException e) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException(
                e.getMessage(),
                e,
                badRequest,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return  new ResponseEntity<>(apiException, badRequest);
    }
}
