package com.hafidtech.api_gunungcondongcom.response;

import lombok.Data;

@Data
public class LoginResponse {
    private String message;
    private String jwt;

    public LoginResponse() {
        this.message = message;
        this.jwt = jwt;
    }
}
