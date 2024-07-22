package com.hafidtech.api_gunungcondongcom.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class CommonResponseBean {
    @JsonFormat(pattern = "yyyy-MM-dd")
    public LocalDateTime timestamp;
    public List<ErrorBean> errors;
}