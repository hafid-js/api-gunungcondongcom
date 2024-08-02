package com.hafidtech.api_gunungcondongcom.model.resident;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hafidtech.api_gunungcondongcom.model.user.role.RoleName;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Blood {

    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NaturalId
    @Size(min = 1, max = 2)
    @Column(name = "blood_type")
    private BloodType bloodType;

    @OneToMany(mappedBy = "blood", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Resident> resident;

    public Blood(BloodType bloodType) {
        this.bloodType = bloodType;
    }
}
