package com.hafidtech.api_gunungcondongcom.model.resident;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @Size(min = 4, max = 30)
    @Column(length = 30)
    private String hemlet;

    @NotBlank
    @JsonIgnore
    @Size(min = 3, max = 50)
    @Column(length = 50)
    private String leader;

    @OneToMany(mappedBy = "hemlet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RW> rw;

    @OneToMany(mappedBy = "hemlet", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Resident> resident;


}
