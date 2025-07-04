package com.java.hash.utils.exception;

public class ErrorDTO {

    private String errorDate;

    private String error;

    private String httpCodeMessage;

    public String getErrorDate() {
        return errorDate;
    }

    public void setErrorDate(String errorDate) {
        this.errorDate = errorDate;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getHttpCodeMessage() {
        return httpCodeMessage;
    }

    public void setHttpCodeMessage(String httpCodeMessage) {
        this.httpCodeMessage = httpCodeMessage;
    }
}