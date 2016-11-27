package com.jfonzuer.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jfonzuer.dto.FetishDto;
import com.jfonzuer.dto.ImageDto;
import com.jfonzuer.dto.LocalizationDto;
import com.jfonzuer.dto.UserTypeDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by jfonzuer on 20.03.16.
 */
public class JwtUser implements UserDetails {

    private Long id;
    private String username;
    private String password;
    private String email;
    private String description;
    private String birthDate;
    private List<FetishDto> fetishes;
    private LocalizationDto localization;
    private Collection<? extends GrantedAuthority> authorities;
    private boolean enabled;
    private Date lastPasswordResetDate;
    private List<ImageDto> images;
    private UserTypeDto userType;


    private String lastActivityDate;
    private Long reportedAsFake;


    public JwtUser() {
    }

    public JwtUser(Long id, String username, String password, String email, String description, String birthDate, List<FetishDto> fetishes, LocalizationDto localization, Collection<? extends GrantedAuthority> authorities, boolean enabled, Date lastPasswordResetDate, List<ImageDto> images, UserTypeDto userType, String lastActivityDate, Long reportedAsFake) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.description = description;
        this.birthDate = birthDate;
        this.fetishes = fetishes;
        this.localization = localization;
        this.authorities = authorities;
        this.enabled = enabled;
        this.lastPasswordResetDate = lastPasswordResetDate;
        this.images = images;
        this.userType = userType;
        this.lastActivityDate = lastActivityDate;
        this.reportedAsFake = reportedAsFake;
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

    public String getBirthDate() {
        return birthDate;
    }

    public List<FetishDto> getFetishes() {
        return fetishes;
    }

    public LocalizationDto getLocalization() {
        return localization;
    }

    public List<ImageDto> getImages() {
        return images;
    }

    public UserTypeDto getUserType() {
        return userType;
    }

    public String getLastActivityDate() {
        return lastActivityDate;
    }

    public Long getReportedAsFake() {
        return reportedAsFake;
    }

    @Override
    public String toString() {
        return "JwtUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", description='" + description + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", fetishes=" + fetishes +
                ", localization=" + localization +
                ", authorities=" + authorities +
                ", enabled=" + enabled +
                ", lastPasswordResetDate=" + lastPasswordResetDate +
                '}';
    }

    public static class JwtUserBuilder {
        private Long id;
        private String username;
        private String password;
        private String email;
        private String description;
        private String birthDate;
        private List<FetishDto> fetishes;
        private LocalizationDto localization;
        private Collection<? extends GrantedAuthority> authorities;
        private boolean enabled;
        private Date lastPasswordResetDate;
        private List<ImageDto> images;
        private UserTypeDto userType;
        private String lastActivityDate;
        private Long reportedAsFake;

        public JwtUserBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public JwtUserBuilder setUsername(String username) {
            this.username = username;
            return this;
        }

        public JwtUserBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public JwtUserBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public JwtUserBuilder setDescription(String description) {
            this.description = description;
            return this;
        }

        public JwtUserBuilder setBirthDate(String birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        public JwtUserBuilder setFetishes(List<FetishDto> fetishes) {
            this.fetishes = fetishes;
            return this;
        }

        public JwtUserBuilder setLocalization(LocalizationDto localization) {
            this.localization = localization;
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

        public JwtUserBuilder setImages(List<ImageDto> images) {
            this.images = images;
            return this;
        }

        public JwtUserBuilder setLastPasswordResetDate(Date lastPasswordResetDate) {
            this.lastPasswordResetDate = lastPasswordResetDate;
            return this;
        }

        public JwtUserBuilder setUserType(UserTypeDto userType) {
            this.userType = userType;
            return this;
        }

        public JwtUserBuilder setLastActivityDate(String lastActivityDate) {
            this.lastActivityDate = lastActivityDate;
            return this;
        }

        public JwtUserBuilder setReportedAsFake(Long reportedAsFake) {
            this.reportedAsFake = reportedAsFake;
            return this;
        }

        public JwtUser createJwtUser() {
            return new JwtUser(id, username, password, email, description, birthDate, fetishes, localization, authorities, enabled, lastPasswordResetDate, images, userType, lastActivityDate, reportedAsFake);
        }
    }
}
