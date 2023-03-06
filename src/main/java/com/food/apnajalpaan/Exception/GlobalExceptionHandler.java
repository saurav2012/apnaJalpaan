package com.food.apnajalpaan.Exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<Mono<Map<String,String>>> handleMethodArgsNotValidException(WebExchangeBindException ex){
        Map<String,String> response = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(
                error -> {
                    String fieldName = ((FieldError) error).getField();
                    String msg = error.getDefaultMessage();
                    response.put(fieldName,msg);
                }
        );
        return new ResponseEntity<>(Mono.just(response),HttpStatus.BAD_REQUEST);
    }

}
