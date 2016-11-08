package com.jfonzuer.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by pgm on 17/10/16.
 */

@Entity
public class Fetish implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    public Fetish() {
    }

    public Fetish(Long id, String name) {
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

    public static class FetishBuilder {
        private Long id;
        private String name;

        public FetishBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public FetishBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public Fetish createFetish() {
            return new Fetish(id, name);
        }
    }

    @Override
    public String toString() {
        return "Fetish{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
