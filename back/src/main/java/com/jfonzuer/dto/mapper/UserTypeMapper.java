package com.jfonzuer.dto.mapper;

import com.jfonzuer.dto.UserTypeDto;
import com.jfonzuer.entities.UserType;

/**
 * Created by pgm on 14/11/16.
 */
public class UserTypeMapper {
    public static UserTypeDto toDto(UserType userType) {
        return userType != null ? new UserTypeDto.UserTypeDtoBuilder()
                .setId(userType.getId())
                .setLabel(userType.getName())
                .createUserTypeDto() : null;
    }

    public static UserType fromDto(UserTypeDto dto) {
        return dto != null ? new UserType.UserTypeBuilder()
                .setId(dto.getId())
                .setLabel(dto.getName())
                .createUserType() : null;
    }
}
