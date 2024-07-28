package com.hafidtech.api_gunungcondongcom.repository.resident;

import com.hafidtech.api_gunungcondongcom.model.resident.Gender;
import com.hafidtech.api_gunungcondongcom.model.resident.Hemlet;
import com.hafidtech.api_gunungcondongcom.model.resident.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JobRepository extends JpaRepository<Job, Long> {

    Optional<Job> findByJob(String job);
}
