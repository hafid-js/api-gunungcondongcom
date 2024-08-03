package com.hafidtech.api_gunungcondongcom.registration.password;

import com.hafidtech.api_gunungcondongcom.exception.UserException;
import com.hafidtech.api_gunungcondongcom.model.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PasswordResetTokenService {

    private final PasswordResetTokenRepository passwordResetTokenRepository;

    @Transactional
    public void createPasswordResetTokenForUser(User user, String passwordToken) throws UserException {
        PasswordResetToken userId = passwordResetTokenRepository.findByUserId(user.getId());
        if (userId != null) {
//            passwordResetTokenRepository.deleteByUserId(user.getId());
            throw new UserException ("we have sent, check your email again");
        }
        PasswordResetToken passwordResetToken = new PasswordResetToken(passwordToken, user);
        passwordResetTokenRepository.save(passwordResetToken);
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
}
