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
    private Integer heightOne;
    private Integer heightTwo;
    private Integer weightOne;
    private Integer weightTwo;

    public Search(String keyword, Area area, Country country, UserType userType, LocalDate birthDateOne, LocalDate birthDateTwo, Integer heightOne, Integer heightTwo, Integer weightOne, Integer weightTwo) {
        this.keyword = keyword;
        this.area = area;
        this.country = country;
        this.userType = userType;
        this.birthDateOne = birthDateOne;
        this.birthDateTwo = birthDateTwo;
        this.heightOne = heightOne;
        this.heightTwo = heightTwo;
        this.weightOne = weightOne;
        this.weightTwo = weightTwo;
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

    public Integer getHeightOne() {
        return heightOne;
    }

    public Integer getHeightTwo() {
        return heightTwo;
    }

    public Integer getWeightOne() {
        return weightOne;
    }

    public Integer getWeightTwo() {
        return weightTwo;
    }

    @Override
    public String toString() {
        return "Search{" +
                "keyword='" + keyword + '\'' +
                ", area=" + area +
                ", country=" + country +
                ", userType=" + userType +
                ", birthDateOne=" + birthDateOne +
                ", birthDateTwo=" + birthDateTwo +
                ", heightOne=" + heightOne +
                ", heightTwo=" + heightTwo +
                ", weightOne=" + weightOne +
                ", weightTwo=" + weightTwo +
                '}';
    }

    public static final class Builder {
        private String keyword;
        private Area area;
        private Country country;
        private UserType userType;
        private LocalDate birthDateOne;
        private LocalDate birthDateTwo;
        private Integer heightOne;
        private Integer heightTwo;
        private Integer weightOne;
        private Integer weightTwo;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder withKeyword(String keyword) {
            this.keyword = keyword;
            return this;
        }

        public Builder withArea(Area area) {
            this.area = area;
            return this;
        }

        public Builder withCountry(Country country) {
            this.country = country;
            return this;
        }

        public Builder withUserType(UserType userType) {
            this.userType = userType;
            return this;
        }

        public Builder withBirthDateOne(LocalDate birthDateOne) {
            this.birthDateOne = birthDateOne;
            return this;
        }

        public Builder withBirthDateTwo(LocalDate birthDateTwo) {
            this.birthDateTwo = birthDateTwo;
            return this;
        }

        public Builder withHeightOne(Integer heightOne) {
            this.heightOne = heightOne;
            return this;
        }

        public Builder withHeightTwo(Integer heightTwo) {
            this.heightTwo = heightTwo;
            return this;
        }

        public Builder withWeightOne(Integer weightOne) {
            this.weightOne = weightOne;
            return this;
        }

        public Builder withWeightTwo(Integer weightTwo) {
            this.weightTwo = weightTwo;
            return this;
        }

        public Search build() {
            Search search = new Search(keyword, area, country, userType, birthDateOne, birthDateTwo, heightOne, heightTwo, weightOne, weightTwo);
            return search;
        }
    }
}
