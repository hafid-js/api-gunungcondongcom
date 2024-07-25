package com.hafidtech.api_gunungcondongcom.model.resident;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Hemlet {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String hemlet;

    @NotBlank
    private String leader;

    @OneToMany(mappedBy = "hemlet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RW> rw;

    @OneToMany(mappedBy = "hemlet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Resident> resident;


}
