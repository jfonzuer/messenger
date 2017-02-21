package com.jfonzuer.dto;

/**
 * Created by pgm on 22/10/16.
 */
public class RegisterDto {
    private UserDto user;
    private PasswordDto passwordConfirmation;

    public RegisterDto() {
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
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
