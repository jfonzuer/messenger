package com.jfonzuer.dto;

import com.jfonzuer.security.JwtUser;

/**
 * Created by pgm on 22/10/16.
 */
public class RegisterDto {
    private JwtUser user;
    private PasswordDto passwordConfirmation;

    public RegisterDto() {
    }

    public JwtUser getUser() {
        return user;
    }

    public void setUser(JwtUser user) {
        this.user = user;
    }

    public PasswordDto getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(PasswordDto passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    @Override
    public String toString() {
        return "RegisterDto{" +
                "user=" + user +
                ", passwordConfirmation=" + passwordConfirmation +
                '}';
    }
}
