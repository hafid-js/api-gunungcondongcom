package com.hafidtech.api_gunungcondongcom.model.resident;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.hafidtech.api_gunungcondongcom.model.audit.UserDateAudit;
import com.hafidtech.api_gunungcondongcom.model.user.role.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
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

    @NotBlank
    @Column(name = "name",length = 50)
    private String name;

    @NotBlank
    @Column(name = "no_identity",length = 16)
    private String noIdentity;

    @NotBlank
    private String placeOfBirth;

    @ManyToOne
    @JoinColumn(name = "id_gender")
    private Gender gender;

    @ManyToOne
    @JoinColumn(name = "id_hemlet")
    private Hemlet hemlet;

    @ManyToOne
    @JoinColumn(name = "id_rw")
    private RW rw;

    @ManyToOne
    @JoinColumn(name = "id_rt")
    private RT rt;

    @NotBlank
    @Column(length = 3)
    private Integer houseNumber;

    @ManyToOne
    @JoinColumn(name = "id_religion")
    private Religion religion;

    @ManyToOne
    @JoinColumn(name = "id_job")
    private Job job;

    @ManyToOne
    @JoinColumn(name = "id_education")
    private Education education;

    @ManyToOne
    @JoinColumn(name = "id_blood")
    private Blood blood;


}
