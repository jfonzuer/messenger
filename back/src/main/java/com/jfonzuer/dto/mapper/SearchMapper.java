package com.jfonzuer.dto.mapper;

import com.jfonzuer.dto.SearchDto;
import com.jfonzuer.entities.Search;

/**
 * Created by pgm on 18/11/16.
 */
public class SearchMapper {

    public static Search fromDto(SearchDto dto) {

        return dto != null ? Search.Builder.builder()
                .withKeyword(dto.getKeyword())
                .withUserType(UserTypeMapper.fromDto(dto.getUserType()))
                .withArea(dto.getArea() != null ? AreaMapper.fromDto(dto.getArea()) : null)
                .withCountry(dto.getCountry() != null ? CountryMapper.fromDto(dto.getCountry()) : null)
                .withBirthDateOne(dto.getBirthDateOne() != null ? DateMapper.toLocalDate(dto.getBirthDateOne()) : null)
                .withBirthDateTwo(dto.getBirthDateTwo() != null ? DateMapper.toLocalDate(dto.getBirthDateTwo()) : null)
                .withHeightOne(dto.getHeightOne() != null ? dto.getHeightOne() : null)
                .withHeightTwo(dto.getHeightTwo() != null ? dto.getHeightTwo() : null)
                .withWeightOne(dto.getWeightOne() != null ? dto.getWeightOne() : null)
                .withWeightTwo(dto.getWeightTwo() != null ? dto.getWeightTwo() : null)
                .build()
        : null;
    }
}
