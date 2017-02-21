package com.jfonzuer.entities;

import javax.persistence.*;

/**
 * Created by pgm on 17/02/17.
 */
@Entity
public class Area {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    private Country country;

    public Area() {
    }

    public Area(Long id, String name, Country country) {
        this.id = id;
        this.name = name;
        this.country = country;
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

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }


    public static class AreaBuilder {
        private Long id;
        private String name;
        private Country country;

        public AreaBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public AreaBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public AreaBuilder setCountry(Country country) {
            this.country = country;
            return this;
        }

        public Area createArea() {
            return new Area(id ,name, country);
        }
    }
}
