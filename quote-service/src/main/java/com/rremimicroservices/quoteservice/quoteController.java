package com.rremimicroservices.quoteservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.zankowski.iextrading4j.api.exception.IEXTradingException;
import pl.zankowski.iextrading4j.client.rest.request.stocks.QuoteRequestBuilder;

import java.util.List;

@RestController
public class quoteController {
    //provides the core functionality
    private quoteService quoteService;

    @Autowired
    public quoteController(quoteService quoteService) {
        this.quoteService = quoteService;
    }

    //returns a userQuote when given a quote's symbol
    @GetMapping("/quote/{symbol}")
    public userQuote getQuote(@PathVariable(name = "symbol") String symbol) {
        userQuote quote = quoteService.getQuote(symbol);
        if (quote == null) throw new QuoteNotFound(symbol);
        return quote;
    }

    //checks to see if a quote is valid
    @GetMapping("/validation/{symbol}")
    public Boolean validateSymbol(@PathVariable(name = "symbol") String symbol){
        return quoteService.validateQuote(symbol);
    }

    @ExceptionHandler(QuoteNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private quoteErrorMessage errorMessage(QuoteNotFound q) {
        return new quoteErrorMessage(404,"Quote with symbol "+q.getSymbol().toUpperCase()+" does not exist");
    }
}
