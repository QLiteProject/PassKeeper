package com.example.passkeeper.HttpsClient;

public class RequestException extends Exception{
    private final int statusCode;
    private final String reason;
    private final String errorMessage;
    private final String errorDescription;

    public RequestException(int statusCode, String reason, String errorMessage, String errorDescription){
        this.statusCode = statusCode;
        this.reason = reason;
        this.errorMessage = errorMessage;
        this.errorDescription = errorDescription;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getReason() {
        return reason;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getErrorDescription() {
        return errorDescription;
    }
}
