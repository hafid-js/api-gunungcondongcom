package com.hafidtech.api_gunungcondongcom.registration.password;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hafidtech.api_gunungcondongcom.model.user.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

@Getter
@Setter
@Entity(name = "password_reset_token")
@NoArgsConstructor
@Data
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long token_id;
    private String token;
    private Date expirationTime;
    @Column(name = "request_non_locked")
    private boolean requestNonLocked = true;
    @Column(name = "request_attempt")
    private int requestAttempt;
    @Column(name = "lock_time")
    private Date lockTime;
    private static final int EXPIRATION_TIME = 5;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public PasswordResetToken(String token, User user) {
        this.token_id = token_id;
        this.token = token;
        this.expirationTime = this.getTokenExpirationTime();
        this.requestNonLocked = this.isRequestNonLocked();
        this.requestAttempt = requestAttempt;
        this.lockTime = lockTime;
        this.user = user;
    }

    public boolean isRequestNonLocked() {
        return true;
    }

    public PasswordResetToken(String token) {
        super();
        this.token = token;
        this.expirationTime = this.getTokenExpirationTime();
    }



    public Date getTokenExpirationTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, EXPIRATION_TIME);

        return new Date(calendar.getTime().getTime());
    }
}
