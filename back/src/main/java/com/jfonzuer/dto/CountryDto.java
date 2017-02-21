package com.jfonzuer.dto;

/**
 * Created by pgm on 17/02/17.
 */
public class CountryDto {
    private Long id;
    private String name;

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


    public static class CountryDtoBuilder {
        private Long id;
        private String name;

        public CountryDtoBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public CountryDtoBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public CountryDto createCountryDto() {
            return new CountryDto(id, name);
        }
    }
}
