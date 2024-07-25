package com.hafidtech.api_gunungcondongcom.model.resident;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.hafidtech.api_gunungcondongcom.model.user.Address;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class RW {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String rw;

    @NotBlank
    private String leader;

    @ManyToOne
    @JoinColumn(name = "id_hemlet")
    private Hemlet hemlet;

    @OneToMany(mappedBy = "rw", cascade = CascadeType.ALL, orphanRemoval = true, fetch=FetchType.LAZY)
    private List<RT> rt;

    @OneToMany(mappedBy = "rw", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Resident> resident;
}
