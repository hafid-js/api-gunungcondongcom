package com.hafidtech.api_gunungcondongcom.repository.user;

import com.hafidtech.api_gunungcondongcom.model.user.User;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsByUsername(@NotBlank String username);
    Boolean existsByEmail(@NotBlank String email);
    Optional<User> findByEmailAndIsEnabled(String email, boolean isEnabled);
    Optional<User> findByEmailAndPassword(String email, String password);
    User findByEmail(String username);
}
