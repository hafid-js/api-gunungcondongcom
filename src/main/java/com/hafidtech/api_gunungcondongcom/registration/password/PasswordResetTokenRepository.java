package com.hafidtech.api_gunungcondongcom.registration.password;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

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

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query("update password_reset_token p set p.token =:passwordToken where p.user.id =:userId")
    void updateResetToken(String passwordToken, Long userId);

    @Modifying
    @Query("UPDATE password_reset_token p SET p.requestAttempt =:requestAttempts WHERE p.user.id =:userId")
    void updateRequestAttempts(int requestAttempts, Long userId);
}
