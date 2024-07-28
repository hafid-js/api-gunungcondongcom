package com.hafidtech.api_gunungcondongcom.repository.resident;

import com.hafidtech.api_gunungcondongcom.model.resident.Gender;
import com.hafidtech.api_gunungcondongcom.model.resident.Hemlet;
import com.hafidtech.api_gunungcondongcom.model.resident.Religion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReligionRepository extends JpaRepository<Religion, Long> {

    Optional<Religion> findByReligion(String religion);
}
