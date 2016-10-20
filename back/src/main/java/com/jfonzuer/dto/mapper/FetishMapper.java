package com.jfonzuer.dto.mapper;

import com.jfonzuer.dto.FetishDto;
import com.jfonzuer.entities.Fetish;

/**
 * Created by pgm on 17/10/16.
 */
public class FetishMapper {

    public static FetishDto toDto(Fetish f) {
        return f != null ? new FetishDto.FetishDtoBuilder().setId(f.getId()).setName(f.getName()).createFetishDto() : null;
    }

}
