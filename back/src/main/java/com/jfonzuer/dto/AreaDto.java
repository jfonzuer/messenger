package com.jfonzuer.dto;

/**
 * Created by pgm on 17/02/17.
 */
public class AreaDto {
    private Long id;
    private String name;

    public AreaDto() {
    }

    public AreaDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public static class AreaDtoBuilder {
        private Long id;
        private String name;

        public AreaDtoBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public AreaDtoBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public AreaDto createAreaDto() {
            return new AreaDto(id, name);
        }
    }
}
