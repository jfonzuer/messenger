package com.jfonzuer.dto;

import javax.validation.constraints.NotNull;

/**
 * Created by pgm on 22/10/16.
 */
public class PasswordDto {

    @NotNull
    private String current;

    @NotNull
    private String password;

    @NotNull
    private String confirmation;

    public PasswordDto() {
    }

    public PasswordDto(String current, String password, String confirmation) {
        this.current = current;
        this.password = password;
        this.confirmation = confirmation;
    }

    public String getCurrent() {
        return current;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmation() {
        return confirmation;
    }



    @Override
    public String toString() {
        return "PasswordDto{" +
                "password='" + password + '\'' +
                ", confirmation='" + confirmation + '\'' +
                '}';
    }

    public static final class Builder {
        private String current;
        private String password;
        private String confirmation;

        private Builder() {
        }

        public static Builder aPasswordDto() {
            return new Builder();
        }

        public Builder withCurrent(String current) {
            this.current = current;
            return this;
        }

        public Builder withPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder withConfirmation(String confirmation) {
            this.confirmation = confirmation;
            return this;
        }

        public PasswordDto build() {
            PasswordDto passwordDto = new PasswordDto(current, password, confirmation);
            return passwordDto;
        }
    }
}
