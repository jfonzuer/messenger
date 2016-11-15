package com.jfonzuer.dto;

/**
 * Created by pgm on 14/11/16.
 */
public class UserTypeDto {
    private Long id;
    private String name;

    public UserTypeDto() {
    }

    public UserTypeDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "UserTypeDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public static class UserTypeDtoBuilder {
        private Long id;
        private String label;

        public UserTypeDtoBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public UserTypeDtoBuilder setLabel(String label) {
            this.label = label;
            return this;
        }

        public UserTypeDto createUserTypeDto() {
            return new UserTypeDto(id, label);
        }
    }
}
