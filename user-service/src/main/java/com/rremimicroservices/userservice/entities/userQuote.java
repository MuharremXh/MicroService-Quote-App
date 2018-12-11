package com.rremimicroservices.userservice.entities;

import java.math.BigDecimal;

public class userQuote {
    private String companyName;
    private String symbol;
    private String sector;
    private BigDecimal latestPrice;
    private BigDecimal open;
    private BigDecimal close;

    public userQuote(String companyName, String symbol, String sector, BigDecimal latestPrice, BigDecimal open, BigDecimal close) {
        this.companyName = companyName;
        this.symbol = symbol;
        this.sector = sector;
        this.latestPrice = latestPrice;
        this.open = open;
        this.close = close;
    }

    public userQuote() {
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public BigDecimal getLatestPrice() {
        return latestPrice;
    }

    public void setLatestPrice(BigDecimal latestPrice) {
        this.latestPrice = latestPrice;
    }

    public BigDecimal getOpen() {
        return open;
    }

    public void setOpen(BigDecimal open) {
        this.open = open;
    }

    public BigDecimal getClose() {
        return close;
    }

    public void setClose(BigDecimal close) {
        this.close = close;
    }
}
