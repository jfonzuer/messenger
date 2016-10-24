package com.jfonzuer.entities;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Created by pgm on 23/10/16.
 */
@Entity
public class Token {

    private static final int EXPIRATION = 60 * 24;

    @Id
    @GeneratedValue
    private Long id;

    private String token;

    @OneToOne(fetch = FetchType.EAGER)
    private User user;

    private LocalDate expiryDate;

    public Token() {
    }

    public Token(String token, User user, LocalDate expiryDate) {
        this.token = token;
        this.user = user;
        this.expiryDate = expiryDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public static class TokenBuilder {
        private String token;
        private User user;
        private LocalDate expiryDate;

        public TokenBuilder setToken(String token) {
            this.token = token;
            return this;
        }

        public TokenBuilder setUser(User user) {
            this.user = user;
            return this;
        }

        public TokenBuilder setExpiryDate(LocalDate expiryDate) {
            this.expiryDate = expiryDate;
            return this;
        }

        public Token createToken() {
            return new Token(token, user, expiryDate);
        }
    }

    @Override
    public String toString() {
        return "Token{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", user=" + user +
                ", expiryDate=" + expiryDate +
                '}';
    }
}
