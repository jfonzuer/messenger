package com.jfonzuer.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

/**
 * Created by pgm on 18/09/16.
 */
@Entity
public class User implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Boolean enabled;

    @Column(nullable = false)
    private Date lastPasswordResetDate;

    @Column(nullable = false)
    private LocalDate birthDate;

    @ManyToMany
    private Collection<Fetish> fetishes;

    @ManyToOne
    private Localization localization;

    @OneToMany(mappedBy = "user")
    private Collection<Image> images;

    @ManyToOne
    private UserType type;


    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<UserRole> userRoles = new HashSet<>(0);

    @Column(nullable = false)
    private LocalDate lastActivityDate;

    @Column(nullable = false)
    private Long reportedAsFake;

    @Column(nullable = false)
    private LocalDate lastReportDate;

    public User() {
    }

    public User(Long id, String username, String password, String email, String description, Boolean enabled, Date lastPasswordResetDate, LocalDate birthDate, Collection<Fetish> fetishes, Localization localization, Collection<Image> images, UserType type, Set<UserRole> userRoles, LocalDate lastActivityDate, Long reportedAsFake, LocalDate lastReportDate) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.description = description;
        this.enabled = enabled;
        this.lastPasswordResetDate = lastPasswordResetDate;
        this.birthDate = birthDate;
        this.fetishes = fetishes;
        this.localization = localization;
        this.images = images;
        this.type = type;
        this.userRoles = userRoles;
        this.lastActivityDate = lastActivityDate;
        this.reportedAsFake = reportedAsFake;
        this.lastReportDate = lastReportDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Date getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

    public void setLastPasswordResetDate(Date lastPasswordResetDate) {
        this.lastPasswordResetDate = lastPasswordResetDate;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Collection<Fetish> getFetishes() {
        return fetishes;
    }

    public void setFetishes(Collection<Fetish> fetishes) {
        this.fetishes = fetishes;
    }

    public Localization getLocalization() {
        return localization;
    }

    public void setLocalization(Localization localization) {
        this.localization = localization;
    }

    public Set<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public Collection<Image> getImages() {
        return images;
    }

    public void setImages(Collection<Image> images) {
        this.images = images;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public LocalDate getLastActivityDate() {
        return lastActivityDate;
    }

    public void setLastActivityDate(LocalDate lastActivityDate) {
        this.lastActivityDate = lastActivityDate;
    }

    public Long getReportedAsFake() {
        return reportedAsFake;
    }

    public void setReportedAsFake(Long reportedAsFake) {
        this.reportedAsFake = reportedAsFake;
    }

    public LocalDate getLastReportDate() {
        return lastReportDate;
    }

    public void setLastReportDate(LocalDate lastReportDate) {
        this.lastReportDate = lastReportDate;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", description='" + description + '\'' +
                ", enabled=" + enabled +
                ", lastPasswordResetDate=" + lastPasswordResetDate +
                ", birthDate=" + birthDate +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof User)) return false;
        User user = (User) obj;
        if (user.getId() == this.getId()) return true;
        return false;
    }

    public static class UserBuilder {
        private Long id;
        private String username;
        private String password;
        private String email;
        private String description;
        private Boolean enabled;
        private Date lastPasswordResetDate;
        private LocalDate birthDate;
        private Collection<Fetish> fetishes;
        private Localization localization;
        private Collection<Image> images;
        private UserType type;
        private Set<UserRole> userRoles;
        private LocalDate lastActivityDate;
        private Long reportedAsFake;
        private LocalDate lastReportDate;

        public UserBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public UserBuilder setUsername(String username) {
            this.username = username;
            return this;
        }

        public UserBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder setDescription(String description) {
            this.description = description;
            return this;
        }

        public UserBuilder setEnabled(Boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public UserBuilder setLastPasswordResetDate(Date lastPasswordResetDate) {
            this.lastPasswordResetDate = lastPasswordResetDate;
            return this;
        }

        public UserBuilder setBirthDate(LocalDate birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        public UserBuilder setFetishes(Collection<Fetish> fetishes) {
            this.fetishes = fetishes;
            return this;
        }

        public UserBuilder setLocalization(Localization localization) {
            this.localization = localization;
            return this;
        }

        public UserBuilder setImages(Collection<Image> images) {
            this.images = images;
            return this;
        }

        public UserBuilder setType(UserType type) {
            this.type = type;
            return this;
        }

        public UserBuilder setUserRoles(Set<UserRole> userRoles) {
            this.userRoles = userRoles;
            return this;
        }

        public UserBuilder setLastActivityDate(LocalDate lastActivityDate) {
            this.lastActivityDate = lastActivityDate;
            return this;
        }

        public UserBuilder setReportedAsFake(Long reportedAsFake) {
            this.reportedAsFake = reportedAsFake;
            return this;
        }
        public UserBuilder setLastReportDate(LocalDate lastReportDate) {
            this.lastReportDate = lastReportDate;
            return this;
        }

        public User createUser() {
            return new User(id, username, password, email, description, enabled, lastPasswordResetDate, birthDate, fetishes, localization, images, type, userRoles, lastActivityDate, reportedAsFake, lastReportDate);
        }
    }
}
