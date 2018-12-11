package com.rremimicroservices.userservice.exceptions;

public class SymbolDoesNotExistException extends RuntimeException {
    String symbol;
    public SymbolDoesNotExistException(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
