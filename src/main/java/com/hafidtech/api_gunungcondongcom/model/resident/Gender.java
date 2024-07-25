package com.hafidtech.api_gunungcondongcom.model.resident;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Gender {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NaturalId
    @Column(name = "gender")
    private GenderType gender;

    @OneToMany(mappedBy = "gender", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Resident> resident;

    public Gender(GenderType gender) {
        this.gender = gender;
    }
}
