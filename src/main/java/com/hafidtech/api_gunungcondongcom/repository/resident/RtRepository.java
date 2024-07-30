package com.hafidtech.api_gunungcondongcom.repository.resident;

import com.hafidtech.api_gunungcondongcom.model.resident.RT;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RtRepository extends JpaRepository<RT, Long> {

    Optional<RT> findByRt(String rt);
}
