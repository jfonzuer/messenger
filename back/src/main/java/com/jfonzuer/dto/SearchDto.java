package com.jfonzuer.dto;

/**
 * Created by pgm on 18/11/16.
 */
public class SearchDto {

    private String keyword;
    private AreaDto area;
    private CountryDto country;
    private UserTypeDto userType;
    private String birthDateOne;
    private String birthDateTwo;
    private Integer heightOne;
    private Integer heightTwo;
    private Integer weightOne;
    private Integer weightTwo;

    public SearchDto() {
    }

    public SearchDto(String keyword, AreaDto area, CountryDto country, UserTypeDto userType, String birthDateOne, String birthDateTwo, Integer heightOne, Integer heightTwo, Integer weightOne, Integer weightTwo) {
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

    public AreaDto getArea() {
        return area;
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

    public CountryDto getCountry() {
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
        return "SearchDto{" +
                "keyword='" + keyword + '\'' +
                ", userType=" + userType +
                ", birthDateOne='" + birthDateOne + '\'' +
                ", birthDateTwo='" + birthDateTwo + '\'' +
                '}';
    }

    public static final class Builder {
        private String keyword;
        private AreaDto area;
        private CountryDto country;
        private UserTypeDto userType;
        private String birthDateOne;
        private String birthDateTwo;
        private Integer heightOne;
        private Integer heightTwo;
        private Integer weightOne;
        private Integer weightTwo;

        private Builder() {
        }

        public static Builder aSearchDto() {
            return new Builder();
        }

        public Builder withKeyword(String keyword) {
            this.keyword = keyword;
            return this;
        }

        public Builder withArea(AreaDto area) {
            this.area = area;
            return this;
        }

        public Builder withCountry(CountryDto country) {
            this.country = country;
            return this;
        }

        public Builder withUserType(UserTypeDto userType) {
            this.userType = userType;
            return this;
        }

        public Builder withBirthDateOne(String birthDateOne) {
            this.birthDateOne = birthDateOne;
            return this;
        }

        public Builder withBirthDateTwo(String birthDateTwo) {
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

        public SearchDto build() {
            SearchDto searchDto = new SearchDto(keyword, area, country, userType, birthDateOne, birthDateTwo, heightOne, heightTwo, weightOne, weightTwo);
            return searchDto;
        }
    }
}

