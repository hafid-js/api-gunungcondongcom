package com.hafidtech.api_gunungcondongcom.repository.resident;

import com.hafidtech.api_gunungcondongcom.model.resident.Education;
import com.hafidtech.api_gunungcondongcom.model.resident.Gender;
import com.hafidtech.api_gunungcondongcom.model.resident.Hemlet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EducationRepository extends JpaRepository<Education, Long> {

    Optional<Education> findByEducation(String education);
}
