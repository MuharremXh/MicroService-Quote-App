package com.rremimicroservices.userservice.exceptions;

public class SymbolIsNotValidException extends RuntimeException {
    private String symbol;
    public SymbolIsNotValidException(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
