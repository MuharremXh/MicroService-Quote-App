package com.rremimicroservices.quoteservice;

import org.springframework.stereotype.Service;
import pl.zankowski.iextrading4j.api.exception.IEXTradingException;
import pl.zankowski.iextrading4j.api.stocks.Quote;
import pl.zankowski.iextrading4j.client.IEXTradingClient;
import pl.zankowski.iextrading4j.client.rest.request.stocks.QuoteRequestBuilder;

import java.util.ArrayList;
import java.util.List;

@Service
public class quoteService {
    //the IEXT api for quote retrieval
    private final IEXTradingClient tradingClient = IEXTradingClient.create();

    public quoteService() {
    }


    public userQuote getQuote(String symbol) {
        Quote  quote ;
        try {
            quote = tradingClient.executeRequest(new QuoteRequestBuilder() //create a request to IEXT api
                     .withSymbol(symbol).build()); //add the symbol and execute the request
            return quoteTranslator(quote);//convert from detaild quote to a simplified one(userQuote)
        } catch (IEXTradingException e) {
            return null; //if an exception is thrown return null
        }
    }

    //validate if a given symbol is valid
    public Boolean validateQuote(String symbol){
        try {
            tradingClient.executeRequest(new QuoteRequestBuilder()
                    .withSymbol(symbol).build());
            return true; //if request doesnt throw an exception it means that symbol is valid
        } catch (IEXTradingException e) {
            return false; //if esception is thrown it means that the quote is not valid
        }
    }

    //convert the qute
    private userQuote quoteTranslator(Quote quote){
        userQuote userQuote = new userQuote(quote.getCompanyName(),quote.getSymbol(),quote.getSector(),quote.getLatestPrice()
        ,quote.getOpen(),quote.getClose());
        return userQuote;
    }
}
