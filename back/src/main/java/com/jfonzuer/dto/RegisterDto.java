package com.jfonzuer.dto;

/**
 * Created by pgm on 22/10/16.
 */
public class RegisterDto {
    private UserDto user;
    private String password;

    public RegisterDto() {
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "RegisterDto{" +
                "user=" + user +
                '}';
    }
}
