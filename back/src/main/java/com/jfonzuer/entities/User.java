package com.jfonzuer.entities;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by pgm on 18/09/16.
 */
@Entity
public class User implements UserDetails, Serializable {

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
    private Country country;

    @ManyToOne
    private Area area;

    @OneToMany(mappedBy = "user")
    private Collection<Image> images;

    @ManyToOne
    private UserType type;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<UserRole> userRoles = new HashSet<>(0);

    @Column(nullable = false)
    private LocalDateTime lastActivityDatetime;

    @Column(nullable = false)
    private Long reportedAsFake;

    @Column(nullable = false)
    private LocalDate lastReportDate;

    @Transient
    private Collection<? extends GrantedAuthority> authorities;

    @Column(nullable = false)
    private Boolean notifyMessage;

    @Column(nullable = false)
    private Boolean notifyVisit;

    @Column(nullable = false)
    private Boolean isBlocked;

    @Column(nullable = false)
    private Boolean activated;

    @ManyToOne
    private User lastVisitedBy;

    @ManyToOne
    private User lastMessageBy;

    @ManyToMany
    private Set<User> blockedUsers;

    @Column
    private String subscriptionId;

    @Column
    private Timestamp lastSubscriptionCheck;

    public User() {
    }

    public User(String username, String password, String email, String description, Boolean enabled, Date lastPasswordResetDate, LocalDate birthDate, Collection<Fetish> fetishes, Country country, Area area, Collection<Image> images, UserType type, Set<UserRole> userRoles, LocalDateTime lastActivityDatetime, Long reportedAsFake, LocalDate lastReportDate, Collection<? extends GrantedAuthority> authorities, Boolean notifyMessage, Boolean notifyVisit, Boolean isBlocked, Boolean activated, User lastVisitedBy, User lastMessageBy, Set<User> blockedUsers, String subscriptionId, Timestamp lastSubscriptionCheck) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.description = description;
        this.enabled = enabled;
        this.lastPasswordResetDate = lastPasswordResetDate;
        this.birthDate = birthDate;
        this.fetishes = fetishes;
        this.country = country;
        this.area = area;
        this.images = images;
        this.type = type;
        this.userRoles = userRoles;
        this.lastActivityDatetime = lastActivityDatetime;
        this.reportedAsFake = reportedAsFake;
        this.lastReportDate = lastReportDate;
        this.authorities = authorities;
        this.notifyMessage = notifyMessage;
        this.notifyVisit = notifyVisit;
        this.isBlocked = isBlocked;
        this.activated = activated;
        this.lastVisitedBy = lastVisitedBy;
        this.lastMessageBy = lastMessageBy;
        this.blockedUsers = blockedUsers;
        this.subscriptionId = subscriptionId;
        this.lastSubscriptionCheck = lastSubscriptionCheck;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return enabled;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
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

    public Country getCountry() {
        return country;
    }

    public Area getArea() {
        return area;
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

    public LocalDateTime getLastActivityDatetime() {
        return lastActivityDatetime;
    }

    public void setLastActivityDatetime(LocalDateTime lastActivityDatetime) {
        this.lastActivityDatetime = lastActivityDatetime;
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

    public Boolean getEnabled() {
        return enabled;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public Boolean getNotifyMessage() {
        return notifyMessage;
    }

    public void setNotifyMessage(Boolean notifyMessage) {
        this.notifyMessage = notifyMessage;
    }

    public Boolean getNotifyVisit() {
        return notifyVisit;
    }

    public void setNotifyVisit(Boolean notifyVisit) {
        this.notifyVisit = notifyVisit;
    }

    public Boolean getBlocked() {
        return isBlocked;
    }

    public void setBlocked(Boolean blocked) {
        isBlocked = blocked;
    }

    public User getLastVisitedBy() {
        return lastVisitedBy;
    }

    public void setLastVisitedBy(User lastVisitedBy) {
        this.lastVisitedBy = lastVisitedBy;
    }

    public User getLastMessageBy() {
        return lastMessageBy;
    }

    public void setLastMessageBy(User lastMessageBy) {
        this.lastMessageBy = lastMessageBy;
    }

    public Set<User> getBlockedUsers() {
        return blockedUsers;
    }

    public void setBlockedUsers(Set<User> blockedUsers) {
        this.blockedUsers = blockedUsers;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public Boolean getActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public String getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public Timestamp getLastSubscriptionCheck() {
        return lastSubscriptionCheck;
    }

    public void setLastSubscriptionCheck(Timestamp lastSubscriptionCheck) {
        this.lastSubscriptionCheck = lastSubscriptionCheck;
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


    public static final class Builder {
        private Long id;
        private String username;
        private String password;
        private String email;
        private String description;
        private Boolean enabled;
        private Date lastPasswordResetDate;
        private LocalDate birthDate;
        private Collection<Fetish> fetishes;
        private Country country;
        private Area area;
        private Collection<Image> images;
        private UserType type;
        private Set<UserRole> userRoles = new HashSet<>(0);
        private LocalDateTime lastActivityDatetime;
        private Long reportedAsFake;
        private LocalDate lastReportDate;
        private Collection<? extends GrantedAuthority> authorities;
        private Boolean notifyMessage;
        private Boolean notifyVisit;
        private Boolean isBlocked;
        private Boolean activated;
        private User lastVisitedBy;
        private User lastMessageBy;
        private Set<User> blockedUsers;
        private String subscriptionId;
        private Timestamp lastSubscriptionCheck;

        private Builder() {
        }

        public static Builder anUser() {
            return new Builder();
        }

        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder withUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder withPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withEnabled(Boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public Builder withLastPasswordResetDate(Date lastPasswordResetDate) {
            this.lastPasswordResetDate = lastPasswordResetDate;
            return this;
        }

        public Builder withBirthDate(LocalDate birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        public Builder withFetishes(Collection<Fetish> fetishes) {
            this.fetishes = fetishes;
            return this;
        }

        public Builder withCountry(Country country) {
            this.country = country;
            return this;
        }

        public Builder withArea(Area area) {
            this.area = area;
            return this;
        }

        public Builder withImages(Collection<Image> images) {
            this.images = images;
            return this;
        }

        public Builder withType(UserType type) {
            this.type = type;
            return this;
        }

        public Builder withUserRoles(Set<UserRole> userRoles) {
            this.userRoles = userRoles;
            return this;
        }

        public Builder withLastActivityDatetime(LocalDateTime lastActivityDatetime) {
            this.lastActivityDatetime = lastActivityDatetime;
            return this;
        }

        public Builder withReportedAsFake(Long reportedAsFake) {
            this.reportedAsFake = reportedAsFake;
            return this;
        }

        public Builder withLastReportDate(LocalDate lastReportDate) {
            this.lastReportDate = lastReportDate;
            return this;
        }

        public Builder withAuthorities(Collection<? extends GrantedAuthority> authorities) {
            this.authorities = authorities;
            return this;
        }

        public Builder withNotifyMessage(Boolean notifyMessage) {
            this.notifyMessage = notifyMessage;
            return this;
        }

        public Builder withNotifyVisit(Boolean notifyVisit) {
            this.notifyVisit = notifyVisit;
            return this;
        }

        public Builder withIsBlocked(Boolean isBlocked) {
            this.isBlocked = isBlocked;
            return this;
        }

        public Builder withActivated(Boolean activated) {
            this.activated = activated;
            return this;
        }

        public Builder withLastVisitedBy(User lastVisitedBy) {
            this.lastVisitedBy = lastVisitedBy;
            return this;
        }

        public Builder withLastMessageBy(User lastMessageBy) {
            this.lastMessageBy = lastMessageBy;
            return this;
        }

        public Builder withBlockedUsers(Set<User> blockedUsers) {
            this.blockedUsers = blockedUsers;
            return this;
        }

        public Builder withSubscriptionId(String subscriptionId) {
            this.subscriptionId = subscriptionId;
            return this;
        }

        public Builder withLastSubscriptionCheck(Timestamp lastSubscriptionCheck) {
            this.lastSubscriptionCheck = lastSubscriptionCheck;
            return this;
        }

        public User build() {
            User user = new User();
            user.setId(id);
            user.setBlocked(isBlocked);
            user.setUsername(username);
            user.setPassword(password);
            user.setEmail(email);
            user.setDescription(description);
            user.setEnabled(enabled);
            user.setLastPasswordResetDate(lastPasswordResetDate);
            user.setBirthDate(birthDate);
            user.setFetishes(fetishes);
            user.setCountry(country);
            user.setArea(area);
            user.setImages(images);
            user.setType(type);
            user.setUserRoles(userRoles);
            user.setLastActivityDatetime(lastActivityDatetime);
            user.setReportedAsFake(reportedAsFake);
            user.setLastReportDate(lastReportDate);
            user.setAuthorities(authorities);
            user.setNotifyMessage(notifyMessage);
            user.setNotifyVisit(notifyVisit);
            user.setActivated(activated);
            user.setLastVisitedBy(lastVisitedBy);
            user.setLastMessageBy(lastMessageBy);
            user.setBlockedUsers(blockedUsers);
            user.setSubscriptionId(subscriptionId);
            user.setLastSubscriptionCheck(lastSubscriptionCheck);
            return user;
        }
    }
}
