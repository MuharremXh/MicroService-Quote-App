package com.rremimicroservices.quoteservice;

//an exception thrown when there couldnt find a quotw with the given symbol
public class QuoteNotFound extends RuntimeException {
    private String symbol;
    public QuoteNotFound(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
