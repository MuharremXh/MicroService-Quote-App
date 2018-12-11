package com.rremimicroservices.quoteservice;


//an error message that represents that something has gone wrong
public class quoteErrorMessage {
    private int errorCode;
    private String message;

    public quoteErrorMessage(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }
}
