package com.jfonzuer.dto.mapper;

import com.jfonzuer.dto.SearchDto;
import com.jfonzuer.entities.Search;

/**
 * Created by pgm on 18/11/16.
 */
public class SearchMapper {

    public static Search fromDto(SearchDto dto) {
        return dto != null ? new Search.SearchBuilder()
                .setKeyword(dto.getKeyword())
                .setUserType(UserTypeMapper.fromDto(dto.getUserType()))
                .setLocalization(dto.getLocalization() != null ? LocalizationMapper.fromDto(dto.getLocalization()) : null)
                .setBirthDateOne(dto.getBirthDateOne() != null ? DateMapper.toLocalDate(dto.getBirthDateOne()) : null)
                .setBirthDateTwo(dto.getBirthDateTwo() != null ? DateMapper.toLocalDate(dto.getBirthDateTwo()) : null)
                .createSearch()
        : null;

    }
}
