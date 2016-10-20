package com.jfonzuer.dto;

/**
 * Created by pgm on 17/10/16.
 */
public class LocalizationDto {
    private Long id;
    private String name;

    public LocalizationDto() {
    }

    public LocalizationDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static class LocalizationDtoBuilder {
        private Long id;
        private String name;

        public LocalizationDtoBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public LocalizationDtoBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public LocalizationDto createLocalizationDto() {
            return new LocalizationDto(id, name);
        }
    }
}
