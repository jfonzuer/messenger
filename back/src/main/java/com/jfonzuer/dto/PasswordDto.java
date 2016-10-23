package com.jfonzuer.dto;

/**
 * Created by pgm on 22/10/16.
 */
public class PasswordDto {
    private String password;
    private String confirmation;

    public PasswordDto() {
    }

    public PasswordDto(String password, String confirmation) {
        this.password = password;
        this.confirmation = confirmation;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmation() {
        return confirmation;
    }

    public void setConfirmation(String confirmation) {
        this.confirmation = confirmation;
    }

    public static class PasswordDtoBuilder {
        private String password;
        private String confirmation;

        public PasswordDtoBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public PasswordDtoBuilder setConfirmation(String confirmation) {
            this.confirmation = confirmation;
            return this;
        }

        public PasswordDto createPasswordDto() {
            return new PasswordDto(password, confirmation);
        }
    }

    @Override
    public String toString() {
        return "PasswordDto{" +
                "password='" + password + '\'' +
                ", confirmation='" + confirmation + '\'' +
                '}';
    }
}
