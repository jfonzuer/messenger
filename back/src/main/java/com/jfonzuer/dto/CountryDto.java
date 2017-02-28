package com.jfonzuer.dto;

/**
 * Created by pgm on 17/02/17.
 */
public class CountryDto {
    private Long id;
    private String name;
    private String flag;

    public CountryDto() {
    }

    public CountryDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFlag() {
        return flag;
    }


    public static final class Builder {
        private Long id;
        private String name;
        private String flag;

        private Builder() {
        }

        public static Builder country() {
            return new Builder();
        }

        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withFlag(String flag) {
            this.flag = flag;
            return this;
        }

        public CountryDto build() {
            CountryDto countryDto = new CountryDto(id, name);
            countryDto.flag = this.flag;
            return countryDto;
        }
    }
}
