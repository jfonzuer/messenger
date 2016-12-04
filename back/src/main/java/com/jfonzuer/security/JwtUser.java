package com.jfonzuer.security;

import com.jfonzuer.dto.FetishDto;
import com.jfonzuer.dto.ImageDto;
import com.jfonzuer.dto.LocalizationDto;
import com.jfonzuer.dto.UserTypeDto;

import java.util.List;

/**
 * Created by jfonzuer on 20.03.16.
 */
public class JwtUser {

    private Long id;
    private String username;
    private String email;
    private String description;
    private String birthDate;
    private List<FetishDto> fetishes;
    private LocalizationDto localization;
    private List<String> authorities;
    private boolean isActive;
    private List<ImageDto> images;
    private UserTypeDto userType;
    private String lastActivityDate;
    private Long reportedAsFake;
    private boolean notifyMessage;
    private boolean notifyVisit;

    public JwtUser() {
    }

    public JwtUser(Long id, String username, String email, String description, String birthDate, List<FetishDto> fetishes, LocalizationDto localization, List<String> authorities, boolean isActive, List<ImageDto> images, UserTypeDto userType, String lastActivityDate, Long reportedAsFake, boolean notifyMessage, boolean notifyVisit) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.description = description;
        this.birthDate = birthDate;
        this.fetishes = fetishes;
        this.localization = localization;
        this.authorities = authorities;
        this.isActive = isActive;
        this.images = images;
        this.userType = userType;
        this.lastActivityDate = lastActivityDate;
        this.reportedAsFake = reportedAsFake;
        this.notifyMessage = notifyMessage;
        this.notifyVisit = notifyVisit;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }


    public String getEmail() {
        return email;
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

    public List<String> getAuthorities() {
        return authorities;
    }

    public boolean isActive() {
        return isActive;
    }

    public boolean isNotifyMessage() {
        return notifyMessage;
    }

    public boolean isNotifyVisit() {
        return notifyVisit;
    }

    @Override
    public String toString() {
        return "JwtUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", description='" + description + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", fetishes=" + fetishes +
                ", localization=" + localization +
                ", authorities=" + authorities +
                '}';
    }


    public static class JwtUserBuilder {
        private Long id;
        private String username;
        private String email;
        private String description;
        private String birthDate;
        private List<FetishDto> fetishes;
        private LocalizationDto localization;
        private List<String> authorities;
        private boolean isActive;
        private List<ImageDto> images;
        private UserTypeDto userType;
        private String lastActivityDate;
        private Long reportedAsFake;
        private boolean notifyMessage;
        private boolean notifyVisit;

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

        public JwtUserBuilder setAuthorities(List<String> authorities) {
            this.authorities = authorities;
            return this;
        }

        public JwtUserBuilder setIsActive(boolean isActive) {
            this.isActive = isActive;
            return this;
        }

        public JwtUserBuilder setImages(List<ImageDto> images) {
            this.images = images;
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

        public JwtUserBuilder setNotifyMessage(boolean notifyMessage) {
            this.notifyMessage = notifyMessage;
            return this;
        }

        public JwtUserBuilder setNotifyVisit(boolean notifyVisit) {
            this.notifyVisit = notifyVisit;
            return this;
        }

        public JwtUser createJwtUser() {
            return new JwtUser(id, username, email, description, birthDate, fetishes, localization, authorities, isActive, images, userType, lastActivityDate, reportedAsFake, notifyMessage, notifyVisit);
        }
    }
}
