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

    public SearchDto() {
    }

    public SearchDto(String keyword, AreaDto area, CountryDto country, UserTypeDto userType, String birthDateOne, String birthDateTwo) {
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

    @Override
    public String toString() {
        return "SearchDto{" +
                "keyword='" + keyword + '\'' +
                ", userType=" + userType +
                ", birthDateOne='" + birthDateOne + '\'' +
                ", birthDateTwo='" + birthDateTwo + '\'' +
                '}';
    }

    public static class SearchDtoBuilder {
        private String keyword;
        private AreaDto area;
        private CountryDto country;
        private UserTypeDto userType;
        private String birthDateOne;
        private String birthDateTwo;

        public SearchDtoBuilder setKeyword(String keyword) {
            this.keyword = keyword;
            return this;
        }

        public SearchDtoBuilder setArea(AreaDto area) {
            this.area = area;
            return this;
        }

        public SearchDtoBuilder setCountry(CountryDto country) {
            this.country = country;
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
            return new SearchDto(keyword, area, country, userType, birthDateOne, birthDateTwo);
        }
    }
}

