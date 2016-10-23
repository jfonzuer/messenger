package com.jfonzuer.dto;

/**
 * Created by pgm on 17/10/16.
 */
public class FetishDto {
    private Long id;
    private String name;

    public FetishDto() {
    }

    public FetishDto(Long id, String name) {
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

    public static class FetishDtoBuilder {
        private Long id;
        private String name;

        public FetishDtoBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public FetishDtoBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public FetishDto createFetishDto() {
            return new FetishDto(id, name);
        }
    }

    @Override
    public String toString() {
        return "FetishDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
