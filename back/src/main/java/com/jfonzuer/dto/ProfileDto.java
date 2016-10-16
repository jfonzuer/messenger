package com.jfonzuer.dto;

/**
 * Created by pgm on 17/10/16.
 */
public class ProfileDto {
    private String description;

    public ProfileDto() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ProfileDto{" +
                "description='" + description + '\'' +
                '}';
    }
}
