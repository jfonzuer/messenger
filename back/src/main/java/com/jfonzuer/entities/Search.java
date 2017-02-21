package com.jfonzuer.entities;

import java.time.LocalDate;

/**
 * Created by pgm on 18/11/16.
 */
public class Search {
    private String keyword;
    private Area area;
    private Country country;
    private UserType userType;
    private LocalDate birthDateOne;
    private LocalDate birthDateTwo;

    public Search(String keyword, Area area, Country country, UserType userType, LocalDate birthDateOne, LocalDate birthDateTwo) {
        this.keyword = keyword;
        this.area = area;
        this.country = country;
        this.userType = userType;
        this.birthDateOne = birthDateOne;
        this.birthDateTwo = birthDateTwo;
    }


    public String getKeyword() {
        return keyword;
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

    public Area getArea() {
        return area;
    }

    public Country getCountry() {
        return country;
    }

    @Override
    public String toString() {
        return "Search{" +
                "keyword='" + keyword + '\'' +
                ", userType=" + userType +
                ", birthDateOne=" + birthDateOne +
                ", birthDateTwo=" + birthDateTwo +
                '}';
    }

    public static class SearchBuilder {
        private String keyword;
        private Area area;
        private Country country;
        private UserType userType;
        private LocalDate birthDateOne;
        private LocalDate birthDateTwo;

        public SearchBuilder setKeyword(String keyword) {
            this.keyword = keyword;
            return this;
        }

        public SearchBuilder setArea(Area area) {
            this.area = area;
            return this;
        }

        public SearchBuilder setCountry(Country country) {
            this.country = country;
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
            return new Search(keyword, area, country, userType, birthDateOne, birthDateTwo);
        }
    }
}
