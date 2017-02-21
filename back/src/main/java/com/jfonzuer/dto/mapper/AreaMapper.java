package com.jfonzuer.dto.mapper;

import com.jfonzuer.dto.AreaDto;
import com.jfonzuer.dto.CountryDto;
import com.jfonzuer.entities.Area;
import com.jfonzuer.entities.Country;

/**
 * Created by pgm on 17/02/17.
 */
public class AreaMapper {

    public static AreaDto toDto(Area area) {
        return area != null ?  new AreaDto.AreaDtoBuilder()
                .setId(area.getId())
                .setName(area.getName())
                .createAreaDto() : null;
    }

    public static Area fromDto(AreaDto dto) {
        return dto != null ? new Area.AreaBuilder()
                .setId(dto.getId())
                .setName(dto.getName())
                .createArea() : null;
    }
}
