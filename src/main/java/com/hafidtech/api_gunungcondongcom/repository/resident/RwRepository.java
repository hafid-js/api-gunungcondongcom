package com.hafidtech.api_gunungcondongcom.repository.resident;

import com.hafidtech.api_gunungcondongcom.model.resident.RW;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RwRepository extends JpaRepository<RW, Long> {

    Optional<RW> findByRw(String rw);
}
