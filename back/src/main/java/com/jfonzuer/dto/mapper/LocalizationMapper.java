package com.jfonzuer.dto.mapper;

import com.jfonzuer.dto.LocalizationDto;
import com.jfonzuer.entities.Localization;

/**
 * Created by pgm on 17/10/16.
 */
public class LocalizationMapper {

    public static LocalizationDto toDto(Localization localization) {
        return localization != null ? new LocalizationDto.LocalizationDtoBuilder().setId(localization.getId()).setName(localization.getName()).createLocalizationDto() : null;
    }

    public static Localization fromDto(LocalizationDto dto) {
        return dto != null ? new Localization.LocalizationBuilder().setId(dto.getId()).setName(dto.getName()).createLocalization() : null;
    }

}
