package com.jfonzuer.dto;

import java.io.Serializable;

/**
 * Created by stephan on 20.03.16.
 */
public class JwtAuthenticationResponse implements Serializable {

    private static final long serialVersionUID = 1250166508152483573L;

    private final String token;
    private final UserDto user;

    public JwtAuthenticationResponse(String token, UserDto user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return this.token;
    }

    public UserDto getUser() {
        return user;
    }
}
