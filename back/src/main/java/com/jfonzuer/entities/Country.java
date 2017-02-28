package com.jfonzuer.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by pgm on 17/02/17.
 */
@Entity
public class Country {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String flag;

    public Country() {
    }

    public Country(Long id, String name) {
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

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public static final class Builder {
        private Long id;
        private String name;
        private String flag;

        private Builder() {
        }

        public static Builder country() {
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

        public Builder withFlag(String flag) {
            this.flag = flag;
            return this;
        }

        public Country build() {
            Country country = new Country();
            country.setId(id);
            country.setName(name);
            country.setFlag(flag);
            return country;
        }
    }
}
