package com.hafidtech.api_gunungcondongcom.response;

public class ErrorBean {
    public String  errorCode;
    public String  errorMessage;

    public ErrorBean(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}