package com.hafidtech.api_gunungcondongcom.model.resident;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.hafidtech.api_gunungcondongcom.model.audit.UserDateAudit;
import com.hafidtech.api_gunungcondongcom.model.user.role.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Resident extends UserDateAudit {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 3, max = 20)
    @Column(name = "name",length = 20)
    private String name;

    @Size(min = 16, max = 16)
    @Column(name = "no_identity",length = 16, unique = true)
    private String noIdentity;

    @Size(min = 5, max = 50)
    @Column(length = 50)
    private String placeOfBirth;

    @Column(length = 50)
    private String DateOfBirth;

    @ManyToOne
    @JoinColumn(name = "gender_id")
    private Gender gender;

    @ManyToOne
    @JoinColumn(name = "hemlet_id")
    private Hemlet hemlet;

    @ManyToOne
    @JoinColumn(name = "rw_id")
    private RW rw;

    @ManyToOne
    @JoinColumn(name = "rt_id")
    private RT rt;

    @Column(length = 3)
    private Integer houseNumber;

    @ManyToOne
    @JoinColumn(name = "religion_id")
    private Religion religion;

    @ManyToOne
    @JoinColumn(name = "job_id")
    private Job job;

    @ManyToOne
    @JoinColumn(name = "education_id")
    private Education education;

    @ManyToOne
    @JoinColumn(name = "blood_id")
    private Blood blood;


}