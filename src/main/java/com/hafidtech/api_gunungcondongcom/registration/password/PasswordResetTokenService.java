package com.hafidtech.api_gunungcondongcom.registration.password;

import com.hafidtech.api_gunungcondongcom.exception.UserException;
import com.hafidtech.api_gunungcondongcom.model.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PasswordResetTokenService {

    public static final int MAX_REQUEST_ATTEMPTS = 3;
    private static final long LOCK_TIME_DURATION = 1 * 60 * 1000; // 2 minutes

    @Autowired
    private final PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private PasswordResetTokenService passwordResetTokenService;

    @Transactional
    public void createPasswordResetTokenForUser(User user, String passwordToken) throws UserException {
        PasswordResetToken userPasswordReset = passwordResetTokenRepository.findByUserId(user.getId());

        if (userPasswordReset != null) {
            PasswordResetToken passwordResetToken = new PasswordResetToken(passwordToken, user);
            passwordResetTokenRepository.updateResetToken(passwordResetToken.getToken(), passwordResetToken.getUser().getId());
//            if (userPasswordReset.isRequestNonLocked()) {
//                if (userPasswordReset.getRequestAttempt() < passwordResetTokenService.MAX_REQUEST_ATTEMPTS - 1) {
//                    passwordResetTokenService.increaseRequestAttempts(userPasswordReset.getUser().getId());
//                } else {
//                    throw new UserException("Your account has been locked due to 3 request attempts."
//                            + "It will be unlocked after 24 hours");
//                }
//            } else if (!userPasswordReset.isRequestNonLocked()) {
////                passwordResetTokenRepository.updateResetToken(user.getId(), passwordToken, userPasswordReset.getToken_id());
//                throw new UserException("Your account has been locked due to 3 request attempts."
//                        + "It will be unlocked after 24 hours");
////                }
//            }



        } else {
            PasswordResetToken passwordResetToken = new PasswordResetToken(passwordToken, user);
            passwordResetTokenRepository.save(passwordResetToken);
        }
    }

   public String validatePasswordResetToken(String theToken) {
           PasswordResetToken token = passwordResetTokenRepository.findByToken(theToken);
           if (token == null) {
               return "Invalid password reset token";
           }

           User user = token.getUser();
           Calendar calendar = Calendar.getInstance();
           if ((token.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0) {
//            tokenRepository.delete(token);
               return "Link already expired, resend link";
           }
           return "valid";
   }


   public Optional<User> findUserByPasswordToken(String passwordToken) {
        return Optional.ofNullable(passwordResetTokenRepository.findByToken(passwordToken).getUser());
   }


    @Transactional
    public void increaseRequestAttempts(Long userId) {

        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByUserId(userId);
        int newRequestAttempts = passwordResetToken.getRequestAttempt() + 1;
        passwordResetTokenRepository.updateRequestAttempts(newRequestAttempts, userId);
    }


    public void lock(PasswordResetToken passwordResetToken) {

        passwordResetToken.setRequestNonLocked(false);
        passwordResetToken.setLockTime(new Date());

        passwordResetTokenRepository.save(passwordResetToken);
    }

    public void resetRequestAttempt(Long userId) {
        passwordResetTokenRepository.updateRequestAttempts(0, userId);
    }

    public boolean unlockWhenTimeExpired(PasswordResetToken userPasswordReset) {
        long lockTimeInMillis = userPasswordReset.getLockTime().getTime();
        long currentTimeInMillis = System.currentTimeMillis();

        if (lockTimeInMillis + LOCK_TIME_DURATION > currentTimeInMillis) {
            userPasswordReset.setRequestNonLocked(true);
            userPasswordReset.setLockTime(null);
            userPasswordReset.setRequestAttempt(0);

            passwordResetTokenRepository.save(userPasswordReset);
            return true;
        }

        return false;
    }
}
