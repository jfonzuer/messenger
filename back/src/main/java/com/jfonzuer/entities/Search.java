package com.jfonzuer.entities;

import java.sql.Date;
import java.time.LocalDate;

/**
 * Created by pgm on 18/11/16.
 */
public class Search {
    private String keyword;
    private Localization localization;
    private UserType userType;
    private LocalDate birthDateOne;
    private LocalDate birthDateTwo;

    public Search(String keyword, Localization localization, UserType userType, LocalDate birthDateOne, LocalDate birthDateTwo) {
        this.keyword = keyword;
        this.localization = localization;
        this.userType = userType;
        this.birthDateOne = birthDateOne;
        this.birthDateTwo = birthDateTwo;
    }

    public static class SearchBuilder {
        private String keyword;
        private Localization localization;
        private UserType userType;
        private LocalDate birthDateOne;
        private LocalDate birthDateTwo;

        public SearchBuilder setKeyword(String keyword) {
            this.keyword = keyword;
            return this;
        }

        public SearchBuilder setLocalization(Localization localization) {
            this.localization = localization;
            return this;
        }

        public SearchBuilder setUserType(UserType userType) {
            this.userType = userType;
            return this;
        }

        public SearchBuilder setBirthDateOne(LocalDate birthDateOne) {
            this.birthDateOne = birthDateOne;
            return this;
        }

        public SearchBuilder setBirthDateTwo(LocalDate birthDateTwo) {
            this.birthDateTwo = birthDateTwo;
            return this;
        }

        public Search createSearch() {
            return new Search(keyword, localization, userType, birthDateOne, birthDateTwo);
        }
    }

    public String getKeyword() {
        return keyword;
    }

    public Localization getLocalization() {
        return localization;
    }

    public UserType getUserType() {
        return userType;
    }

    public LocalDate getBirthDateOne() {
        return birthDateOne;
    }

    public LocalDate getBirthDateTwo() {
        return birthDateTwo;
    }

    @Override
    public String toString() {
        return "Search{" +
                "keyword='" + keyword + '\'' +
                ", localization=" + localization +
                ", userType=" + userType +
                ", birthDateOne=" + birthDateOne +
                ", birthDateTwo=" + birthDateTwo +
                '}';
    }
}
