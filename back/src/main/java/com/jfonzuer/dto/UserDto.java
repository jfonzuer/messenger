package com.jfonzuer.dto;

import java.util.List;

/**
 * Created by jfonzuer on 20.03.16.
 */
public class UserDto {

    private Long id;
    private String username;
    private String email;
    private String description;
    private String birthDate;
    private List<FetishDto> fetishes;
    private List<String> authorities;
    private List<ImageDto> images;
    private UserTypeDto userType;
    private String lastActivityDate;
    private Long reportedAsFake;
    private Boolean notifyMessage;
    private Boolean notifyVisit;
    private Boolean enabled;
    private Boolean isBlocked;
    private List<UserDto> blockedUsers;
    private CountryDto country;
    private AreaDto area;
    private Boolean isPremium;

    public UserDto() {
    }

    public UserDto(Long id, String username, String email, String description, String birthDate, List<FetishDto> fetishes, List<String> authorities, List<ImageDto> images, UserTypeDto userType, String lastActivityDate, Long reportedAsFake, Boolean notifyMessage, Boolean notifyVisit, Boolean enabled, Boolean isBlocked, List<UserDto> blockedUsers, CountryDto country, AreaDto area, Boolean isPremium) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.description = description;
        this.birthDate = birthDate;
        this.fetishes = fetishes;
        this.authorities = authorities;
        this.images = images;
        this.userType = userType;
        this.lastActivityDate = lastActivityDate;
        this.reportedAsFake = reportedAsFake;
        this.notifyMessage = notifyMessage;
        this.notifyVisit = notifyVisit;
        this.enabled = enabled;
        this.isBlocked = isBlocked;
        this.blockedUsers = blockedUsers;
        this.country = country;
        this.area = area;
        this.isPremium = isPremium;
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

    public Boolean getNotifyMessage() {
        return notifyMessage;
    }

    public Boolean getNotifyVisit() {
        return notifyVisit;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public Boolean getBlocked() {
        return isBlocked;
    }

    public List<UserDto> getBlockedUsers() {
        return blockedUsers;
    }

    public CountryDto getCountry() {
        return country;
    }

    public AreaDto getArea() {
        return area;
    }

    public Boolean getPremium() {
        return isPremium;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", description='" + description + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", fetishes=" + fetishes +
                ", authorities=" + authorities +
                '}';
    }

    public static class UserDtoBuilder {
        private Long id;
        private String username;
        private String email;
        private String description;
        private String birthDate;
        private List<FetishDto> fetishes;
        private List<String> authorities;
        private List<ImageDto> images;
        private UserTypeDto userType;
        private String lastActivityDate;
        private Long reportedAsFake;
        private Boolean notifyMessage;
        private Boolean notifyVisit;
        private Boolean enabled;
        private Boolean isBlocked;
        private List<UserDto> blockedUsers;
        private CountryDto country;
        private AreaDto area;
        private Boolean isPremium;

        public UserDtoBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public UserDtoBuilder setUsername(String username) {
            this.username = username;
            return this;
        }

        public UserDtoBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public UserDtoBuilder setDescription(String description) {
            this.description = description;
            return this;
        }

        public UserDtoBuilder setBirthDate(String birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        public UserDtoBuilder setFetishes(List<FetishDto> fetishes) {
            this.fetishes = fetishes;
            return this;
        }

        public UserDtoBuilder setAuthorities(List<String> authorities) {
            this.authorities = authorities;
            return this;
        }

        public UserDtoBuilder setImages(List<ImageDto> images) {
            this.images = images;
            return this;
        }

        public UserDtoBuilder setUserType(UserTypeDto userType) {
            this.userType = userType;
            return this;
        }

        public UserDtoBuilder setLastActivityDate(String lastActivityDate) {
            this.lastActivityDate = lastActivityDate;
            return this;
        }

        public UserDtoBuilder setReportedAsFake(Long reportedAsFake) {
            this.reportedAsFake = reportedAsFake;
            return this;
        }

        public UserDtoBuilder setNotifyMessage(Boolean notifyMessage) {
            this.notifyMessage = notifyMessage;
            return this;
        }

        public UserDtoBuilder setNotifyVisit(Boolean notifyVisit) {
            this.notifyVisit = notifyVisit;
            return this;
        }

        public UserDtoBuilder setEnabled(Boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public UserDtoBuilder setIsBlocked(Boolean isBlocked) {
            this.isBlocked = isBlocked;
            return this;
        }

        public UserDtoBuilder setBlockedUsers(List<UserDto> blockedUsers) {
            this.blockedUsers = blockedUsers;
            return this;
        }

        public UserDtoBuilder setCountry(CountryDto country) {
            this.country = country;
            return this;
        }

        public UserDtoBuilder setArea(AreaDto area) {
            this.area = area;
            return this;
        }

        public UserDtoBuilder setIsPremium(Boolean isPremium) {
            this.isPremium = isPremium;
            return this;
        }

        public UserDto createJwtUser() {
            return new UserDto(id, username, email, description, birthDate, fetishes, authorities, images, userType, lastActivityDate, reportedAsFake, notifyMessage, notifyVisit, enabled, isBlocked, blockedUsers, country, area, isPremium);
        }
    }
}
