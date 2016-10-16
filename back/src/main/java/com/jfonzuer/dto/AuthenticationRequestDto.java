package com.jfonzuer.dto;

import java.io.Serializable;

/**
 * Created by stephan on 20.03.16.
 */
public class AuthenticationRequestDto implements Serializable {

    private static final long serialVersionUID = -8445943548965154778L;

    private String email;
    private String password;

    public AuthenticationRequestDto() {
        super();
    }

    public AuthenticationRequestDto(String username, String password) {
        this.setEmail(username);
        this.setPassword(password);
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
