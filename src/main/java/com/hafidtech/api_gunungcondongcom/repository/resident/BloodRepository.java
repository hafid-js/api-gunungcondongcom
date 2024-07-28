package com.hafidtech.api_gunungcondongcom.repository.resident;

import com.hafidtech.api_gunungcondongcom.model.resident.Blood;
import com.hafidtech.api_gunungcondongcom.model.resident.Gender;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BloodRepository extends JpaRepository<Blood, Long> {
    Optional<Blood> findById(Blood blood);

//    Optional<Blood> findById(String bloodType);
}
