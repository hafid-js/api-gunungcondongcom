package com.hafidtech.api_gunungcondongcom.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank
    @Email
    public String email;

    @NotBlank
    public String password;
}
