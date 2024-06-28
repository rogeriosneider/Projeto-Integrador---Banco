package com.integracao.bank.exceptions;

public class SaldoException extends RuntimeException {
    public SaldoException(String message) {
        super(message);
    }
}