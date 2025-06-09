package com.plaza.plazoleta.infraestructure.exceptionhandler;

import com.plaza.plazoleta.domain.exception.MenuValidationException;
import com.plaza.plazoleta.domain.exception.OrderUserCaseValidationException;
import com.plaza.plazoleta.domain.exception.OrderValidationException;
import com.plaza.plazoleta.domain.exception.RestaurantValidationException;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor {

    private static final String MESSAGE = "Message";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleRestaurantAlreadyExistsException(
            MethodArgumentNotValidException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, ex.getMessage()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handlerHttpMessageNotReadableException(
            HttpMessageNotReadableException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, ex.getMessage()));
    }

    @ExceptionHandler(RestaurantValidationException.class)
    public ResponseEntity<Map<String, String>> restaurantValidation(
            RestaurantValidationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, ex.getMessage()));
    }

    @ExceptionHandler(MenuValidationException.class)
    public ResponseEntity<Map<String, String>> menuValidation(
            MenuValidationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, ex.getMessage()));
    }



    @ExceptionHandler(FeignException.class)
    public ResponseEntity<Map<String, String>> handlerFeignexception(
            FeignException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(MESSAGE, ex.getMessage()));
    }

    @ExceptionHandler(OrderValidationException.class)
    public ResponseEntity<Map<String, String>> handlerOrderValidationexception(
            OrderValidationException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(MESSAGE, ex.getMessage()));
    }

    @ExceptionHandler(    OrderUserCaseValidationException.class)
    public ResponseEntity<Map<String, String>> handlerOrderUserCaseValidationException(
            OrderUserCaseValidationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, ex.getMessage()));
    }



}
