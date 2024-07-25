package com.hafidtech.api_gunungcondongcom.repository;

import com.hafidtech.api_gunungcondongcom.model.user.role.Role;
import com.hafidtech.api_gunungcondongcom.model.user.role.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(RoleName name);
}
