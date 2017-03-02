package com.jfonzuer.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by pgm on 14/11/16.
 */

@Entity
public class UserType {

    @Id
    private Long id;

    @Column(nullable = false)
    private String name;

    public UserType() {
    }

    public UserType(Long id, String name) {
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
        return "UserType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }


    public static final class Builder {
        private Long id;
        private String name;

        private Builder() {
        }

        public static Builder anUserType() {
            return new Builder();
        }

        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public UserType build() {
            UserType userType = new UserType();
            userType.setId(id);
            userType.setName(name);
            return userType;
        }
    }
}
