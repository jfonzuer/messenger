package com.jfonzuer.dto.mapper;

import com.jfonzuer.dto.CountryDto;
import com.jfonzuer.entities.Country;

/**
 * Created by pgm on 17/02/17.
 */
public class CountryMapper {

    public static CountryDto toDto(Country country) {
        return country != null ?  new CountryDto.CountryDtoBuilder()
                .setId(country.getId())
                .setName(country.getName())
                .createCountryDto() : null;
    }

    public static Country fromDto(CountryDto dto) {
        return dto != null ? new Country.CountryBuilder()
                .setId(dto.getId())
                .setName(dto.getName())
                .createCountry() : null;
    }
}
