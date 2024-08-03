package com.hafidtech.api_gunungcondongcom.registration.password;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    PasswordResetToken findByToken(String theToken);

//    @Query("DELETE FROM password_reset_token WHERE userId = ?1")
//    PasswordResetToken deleteByUserId(String userId);

//    @Query("SELECT p FROM passwordResetToken p WHERE p.user.id=:userId")
//    PasswordResetToken findByUserId(Long userId);

//    @Query("DELETE FROM password_reset_token p WHERE p.user.id=:userId")
//    void deleteByUserId(Long userId);

    PasswordResetToken findByUserId(Long id);

    void deleteByUserId(Long id);

    void deleteByToken(String passwordResetToken);
}
