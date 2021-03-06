package com.jfonzuer.dto;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by jfonzuer on 20.03.16.
 */
public class UserDto {

    private Long id;

    @NotNull
    private String username;

    @NotNull
    private String email;

    private String description;

    @NotNull
    private String birthDate;

    private List<FetishDto> fetishes;

    private List<String> authorities;

    private List<ImageDto> images;

    @NotNull
    private UserTypeDto userType;

    private String lastActivityDatetime;

    private Long reportedAsFake;

    private Boolean notifyMessage;

    private Boolean notifyVisit;

    private Boolean notifyOther;

    private Boolean enabled;

    private Boolean isBlocked;

    private List<UserDto> blockedUsers;

    @NotNull
    private Integer height;

    @NotNull
    private Integer weight;

    private String search;

    @NotNull
    private CountryDto country;

    @NotNull
    private AreaDto area;

    public UserDto() {
    }

    public UserDto(Long id, String username, String email, String description, String birthDate, List<FetishDto> fetishes, List<String> authorities, List<ImageDto> images, UserTypeDto userType, String lastActivityDatetime, Long reportedAsFake, Boolean notifyMessage, Boolean notifyVisit, Boolean notifyOther, Boolean enabled, Boolean isBlocked, List<UserDto> blockedUsers, Integer height, Integer weight, String search, CountryDto country, AreaDto area) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.description = description;
        this.birthDate = birthDate;
        this.fetishes = fetishes;
        this.authorities = authorities;
        this.images = images;
        this.userType = userType;
        this.lastActivityDatetime = lastActivityDatetime;
        this.reportedAsFake = reportedAsFake;
        this.notifyMessage = notifyMessage;
        this.notifyVisit = notifyVisit;
        this.notifyOther = notifyOther;
        this.enabled = enabled;
        this.isBlocked = isBlocked;
        this.blockedUsers = blockedUsers;
        this.height = height;
        this.weight = weight;
        this.search = search;
        this.country = country;
        this.area = area;
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

    public String getLastActivityDatetime() {
        return lastActivityDatetime;
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

    public Integer getHeight() {
        return height;
    }

    public Integer getWeight() {
        return weight;
    }

    public String getSearch() {
        return search;
    }

    public Boolean getNotifyOther() {
        return notifyOther;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", fetishes=" + fetishes +
                ", authorities=" + authorities +
                '}';
    }


    public static final class Builder {
        private Long id;
        private String username;
        private String email;
        private String description;
        private String birthDate;
        private List<FetishDto> fetishes;
        private List<String> authorities;
        private List<ImageDto> images;
        private UserTypeDto userType;
        private String lastActivityDatetime;
        private Long reportedAsFake;
        private Boolean notifyMessage;
        private Boolean notifyVisit;
        private Boolean notifyOther;
        private Boolean enabled;
        private Boolean isBlocked;
        private List<UserDto> blockedUsers;
        private Integer height;
        private Integer weight;
        private String search;
        private CountryDto country;
        private AreaDto area;

        private Builder() {
        }

        public static Builder builder() {
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

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withBirthDate(String birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        public Builder withFetishes(List<FetishDto> fetishes) {
            this.fetishes = fetishes;
            return this;
        }

        public Builder withAuthorities(List<String> authorities) {
            this.authorities = authorities;
            return this;
        }

        public Builder withImages(List<ImageDto> images) {
            this.images = images;
            return this;
        }

        public Builder withUserType(UserTypeDto userType) {
            this.userType = userType;
            return this;
        }

        public Builder withLastActivityDatetime(String lastActivityDatetime) {
            this.lastActivityDatetime = lastActivityDatetime;
            return this;
        }

        public Builder withReportedAsFake(Long reportedAsFake) {
            this.reportedAsFake = reportedAsFake;
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

        public Builder withNotifyOther(Boolean notifyOther) {
            this.notifyOther = notifyOther;
            return this;
        }

        public Builder withEnabled(Boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public Builder withIsBlocked(Boolean isBlocked) {
            this.isBlocked = isBlocked;
            return this;
        }

        public Builder withBlockedUsers(List<UserDto> blockedUsers) {
            this.blockedUsers = blockedUsers;
            return this;
        }

        public Builder withHeight(Integer height) {
            this.height = height;
            return this;
        }

        public Builder withWeight(Integer weight) {
            this.weight = weight;
            return this;
        }

        public Builder withSearch(String search) {
            this.search = search;
            return this;
        }

        public Builder withCountry(CountryDto country) {
            this.country = country;
            return this;
        }

        public Builder withArea(AreaDto area) {
            this.area = area;
            return this;
        }

        public UserDto build() {
            UserDto userDto = new UserDto(id, username, email, description, birthDate, fetishes, authorities, images, userType, lastActivityDatetime, reportedAsFake, notifyMessage, notifyVisit, notifyOther, enabled, isBlocked, blockedUsers, height, weight, search, country, area);
            return userDto;
        }
    }
}
