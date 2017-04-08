package com.jfonzuer.dto.response;

import com.jfonzuer.dto.UserDto;
import com.jfonzuer.entities.User;

/**
 * Created by pgm on 08/04/17.
 */
public class InformationUpdateDto {
    private UserDto user;
    private String token;

    public InformationUpdateDto() {
    }

    public InformationUpdateDto(UserDto user, String token) {
        this.user = user;
        this.token = token;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public static final class Builder {
        private UserDto user;
        private String token;

        private Builder() {
        }

        public static Builder anInformationUpdateDto() {
            return new Builder();
        }

        public Builder withUser(UserDto user) {
            this.user = user;
            return this;
        }

        public Builder withToken(String token) {
            this.token = token;
            return this;
        }

        public InformationUpdateDto build() {
            InformationUpdateDto informationUpdateDto = new InformationUpdateDto();
            informationUpdateDto.setUser(user);
            informationUpdateDto.setToken(token);
            return informationUpdateDto;
        }
    }
}
