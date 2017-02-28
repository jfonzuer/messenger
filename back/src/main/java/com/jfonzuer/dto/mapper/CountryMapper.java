package com.jfonzuer.dto.mapper;

import com.jfonzuer.dto.CountryDto;
import com.jfonzuer.entities.Country;

/**
 * Created by pgm on 17/02/17.
 */
public class CountryMapper {

    public static CountryDto toDto(Country country) {
        return country != null ?  CountryDto.Builder.country()
                .withId(country.getId())
                .withName(country.getName())
                .withFlag(country.getFlag())
                .build() : null;
    }

    public static Country fromDto(CountryDto dto) {
        return dto != null ? Country.Builder.country()
                .withId(dto.getId())
                .withName(dto.getName())
                .withFlag(dto.getFlag())
                .build() : null;
    }
}
