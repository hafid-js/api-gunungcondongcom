package com.hafidtech.api_gunungcondongcom.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hafidtech.api_gunungcondongcom.model.audit.UserDateAudit;
import com.hafidtech.api_gunungcondongcom.model.resident.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
public class ResidentRequest extends UserDateAudit {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String noIdentity;
    private String placeOfBirth;
    private String dateOfBirth;

    @NotNull
    private Long genderId;

    @NotNull
    private Long hemletId;
    @NotNull
    private Long rwId;

    @NotNull
    private Long rtId;

    @NotNull
    private Integer houseNumber;

    @NotNull
    private Long religionId;
    @NotNull
    private Long jobId;

    @NotNull
    private Long educationId;
    @NotNull
    private Long bloodId;


}
