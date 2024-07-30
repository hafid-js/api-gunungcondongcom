package com.hafidtech.api_gunungcondongcom.repository.resident;

import com.hafidtech.api_gunungcondongcom.model.resident.Gender;
import com.hafidtech.api_gunungcondongcom.model.resident.Hemlet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HemletRepository extends JpaRepository<Hemlet, Long> {

    Optional<Hemlet> findByHemlet(String hemlet);
}