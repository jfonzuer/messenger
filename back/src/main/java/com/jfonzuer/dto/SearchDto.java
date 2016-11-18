package com.jfonzuer.dto;

import com.jfonzuer.entities.UserType;

/**
 * Created by pgm on 18/11/16.
 */
public class SearchDto {
    private String keyword;
    private LocalizationDto localization;
    private UserTypeDto userType;
    private String birthDateOne;
    private String birthDateTwo;

    public SearchDto() {
    }

    public SearchDto(String keyword, LocalizationDto localization, UserTypeDto userType, String birthDateOne, String birthDateTwo) {
        this.keyword = keyword;
        this.localization = localization;
        this.userType = userType;
        this.birthDateOne = birthDateOne;
        this.birthDateTwo = birthDateTwo;
    }

    public String getKeyword() {
        return keyword;
    }

    public LocalizationDto getLocalization() {
        return localization;
    }

    public UserTypeDto getUserType() {
        return userType;
    }

    public String getBirthDateOne() {
        return birthDateOne;
    }

    public String getBirthDateTwo() {
        return birthDateTwo;
    }

    public static class SearchDtoBuilder {
        private String keyword;
        private LocalizationDto localization;
        private UserTypeDto userType;
        private String birthDateOne;
        private String birthDateTwo;

        public SearchDtoBuilder setKeyword(String keyword) {
            this.keyword = keyword;
            return this;
        }

        public SearchDtoBuilder setLocalization(LocalizationDto localization) {
            this.localization = localization;
            return this;
        }

        public SearchDtoBuilder setUserType(UserTypeDto userType) {
            this.userType = userType;
            return this;
        }

        public SearchDtoBuilder setBirthDateOne(String birthDateOne) {
            this.birthDateOne = birthDateOne;
            return this;
        }

        public SearchDtoBuilder setBirthDateTwo(String birthDateTwo) {
            this.birthDateTwo = birthDateTwo;
            return this;
        }

        public SearchDto createSearchDto() {
            return new SearchDto(keyword, localization, userType, birthDateOne, birthDateTwo);
        }
    }

    @Override
    public String toString() {
        return "SearchDto{" +
                "keyword='" + keyword + '\'' +
                ", localization=" + localization +
                ", userType=" + userType +
                ", birthDateOne='" + birthDateOne + '\'' +
                ", birthDateTwo='" + birthDateTwo + '\'' +
                '}';
    }
}

