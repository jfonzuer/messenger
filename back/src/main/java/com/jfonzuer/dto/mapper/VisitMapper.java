package com.jfonzuer.dto.mapper;

import com.jfonzuer.dto.VisitDto;
import com.jfonzuer.entities.Visit;

/**
 * Created by pgm on 17/10/16.
 */
public class VisitMapper {

    public static VisitDto toDto(Visit v) {
        return new VisitDto.VisitDtoBuilder()
                .setId(v.getId())
                .setVisited(UserMapper.toLightDto(v.getVisited())).setVisitor(UserMapper.toLightDto(v.getVisitor()))
                .setVisitedDate(v.getVisitedDate().toString())
                .createVisitDto();
    }
}
