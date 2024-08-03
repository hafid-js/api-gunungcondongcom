package com.hafidtech.api_gunungcondongcom.service;

import com.hafidtech.api_gunungcondongcom.exception.UserException;
import com.hafidtech.api_gunungcondongcom.model.user.User;
import com.hafidtech.api_gunungcondongcom.registration.token.VerificationToken;

import java.util.Optional;

public interface UserService {
    User addUser(User user);

    User addAdmin(User user);

    VerificationToken generateNewVerificationToken(String oldToken);

    String validateToken(String theToken);

    User findUserProfileByJwt(String jwt) throws UserException;

    void saveUserVerificationToken(User theUser, String verificationToken);

    Optional<User> findByEmail(String email);

    void createPasswordResetTokenForUser(User user, String passwordToken) throws UserException;

    String validatePasswordResetToken(String passwordResetToken);

    User findUserProfileByToken(String passwordResetToken);

    void resetUserPassword(User user, String newPassword);
}
