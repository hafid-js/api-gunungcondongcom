package com.hafidtech.api_gunungcondongcom.response;

import com.hafidtech.api_gunungcondongcom.model.user.User;
import lombok.Data;

@Data
public class AuthResponse {
    private String message;
    private User user;

    public AuthResponse() {
        this.message = message;
        this.user = user;

    }
}
