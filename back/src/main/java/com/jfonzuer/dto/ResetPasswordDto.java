package com.jfonzuer.dto;

import javax.validation.constraints.NotNull;

/**
 * Created by pgm on 24/10/16.
 */
public class ResetPasswordDto {

    @NotNull
    private PasswordDto passwordConfirmation;

    @NotNull
    private String token;

    @NotNull
    private Long userId;

    public ResetPasswordDto() {
    }

    public ResetPasswordDto(PasswordDto passwordConfirmation, String token, Long userId) {
        this.passwordConfirmation = passwordConfirmation;
        this.token = token;
        this.userId = userId;
    }

    public PasswordDto getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(PasswordDto passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "ResetPasswordDto{" +
                "passwordConfirmation=" + passwordConfirmation +
                ", token='" + token + '\'' +
                ", userId=" + userId +
                '}';
    }
}
