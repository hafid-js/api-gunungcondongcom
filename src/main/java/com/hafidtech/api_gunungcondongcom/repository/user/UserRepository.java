package com.hafidtech.api_gunungcondongcom.repository.user;

import com.hafidtech.api_gunungcondongcom.model.user.User;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsByUsername(@NotBlank String username);
    Boolean existsByEmail(@NotBlank String email);

    User findByEmail(String username);
}
