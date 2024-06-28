package com.integracao.bank.config;

import com.integracao.bank.error.ApiError;
import com.integracao.bank.exceptions.NotFoundException;
import com.integracao.bank.exceptions.SaldoException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException ex, WebRequest request) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage());
        log.error("ERRO:", ex);
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SaldoException.class)
    public ResponseEntity<Object> handleSaldoException(SaldoException se, WebRequest request) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, se.getMessage());
        log.error("ERRO:", se);
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

}
