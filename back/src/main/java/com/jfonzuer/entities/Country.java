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

    public static class CountryBuilder {
        private Long id;
        private String name;

        public CountryBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public CountryBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public Country createCountry() {
            return new Country(id, name);
        }
    }
}
