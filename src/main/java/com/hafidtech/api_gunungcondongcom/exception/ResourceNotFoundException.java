package com.hafidtech.api_gunungcondongcom.exception;

import com.hafidtech.api_gunungcondongcom.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Objects;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private transient ApiResponse apiResponse;

    private String resourceName;

    private String fieldName;

    private Object fieldValue;

    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super();
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public String getResourceName() {
        return resourceName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Object getFieldValue() {
        return fieldValue;
    }

    private void setApiResponse() {
        String message = String.format("%s not found with %s: '%s'", resourceName, fieldName, fieldValue);

        apiResponse = new ApiResponse(Boolean.FALSE, message);
    }
}

