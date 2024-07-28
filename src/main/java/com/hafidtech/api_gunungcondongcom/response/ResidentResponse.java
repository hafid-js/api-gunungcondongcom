package com.hafidtech.api_gunungcondongcom.response;

import com.hafidtech.api_gunungcondongcom.model.resident.Resident;
import com.hafidtech.api_gunungcondongcom.model.user.User;
import lombok.Data;

@Data
public class ResidentResponse {
    private String message;
    private Resident resident;

    public ResidentResponse() {
        this.message = message;
        this.resident = resident;

    }
}
