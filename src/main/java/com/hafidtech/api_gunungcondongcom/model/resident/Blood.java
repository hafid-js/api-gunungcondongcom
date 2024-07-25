package com.hafidtech.api_gunungcondongcom.model.resident;

import com.hafidtech.api_gunungcondongcom.model.user.role.RoleName;
import jakarta.persistence.*;
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
    @Column(name = "blood_type")
    private BloodType bloodType;

    @OneToMany(mappedBy = "blood", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Resident> resident;

    public Blood(BloodType bloodType) {
        this.bloodType = bloodType;
    }
}
