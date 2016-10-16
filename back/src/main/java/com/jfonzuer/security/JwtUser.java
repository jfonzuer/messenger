package com.jfonzuer.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;

/**
 * Created by stephan on 20.03.16.
 */
public class JwtUser implements UserDetails {

    private Long id;
    private String username;
    private String password;
    private String email;
    private String description;
    private Collection<? extends GrantedAuthority> authorities;
    private boolean enabled;
    private Date lastPasswordResetDate;

    public JwtUser() {
    }

    public JwtUser(Long id, String username, String email, String password, Date lastPasswordResetDate, Collection<? extends GrantedAuthority> authorities, boolean enabled, String description) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
        this.enabled = enabled;
        this.lastPasswordResetDate = lastPasswordResetDate;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    public String getEmail() {
        return email;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @JsonIgnore
    public Date getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

    public String getDescription() {
        return description;
    }

    public static class JwtUserBuilder {
        private Long id;
        private String username;
        private String email;
        private String password;
        private Date lastPasswordResetDate;
        private Collection<? extends GrantedAuthority> authorities;
        private boolean enabled;
        private String description;

        public JwtUserBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public JwtUserBuilder setUsername(String username) {
            this.username = username;
            return this;
        }

        public JwtUserBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public JwtUserBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public JwtUserBuilder setLastPasswordResetDate(Date lastPasswordResetDate) {
            this.lastPasswordResetDate = lastPasswordResetDate;
            return this;
        }

        public JwtUserBuilder setAuthorities(Collection<? extends GrantedAuthority> authorities) {
            this.authorities = authorities;
            return this;
        }

        public JwtUserBuilder setEnabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public JwtUserBuilder setDescription(String description) {
            this.description = description;
            return this;
        }

        public JwtUser createJwtUser() {
            return new JwtUser(id, username, email, password, lastPasswordResetDate, authorities, enabled, description);
        }
    }

    @Override
    public String toString() {
        return "JwtUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", description='" + description + '\'' +
                ", authorities=" + authorities +
                ", enabled=" + enabled +
                ", lastPasswordResetDate=" + lastPasswordResetDate +
                '}';
    }
}
