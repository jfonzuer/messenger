package com.jfonzuer.dto.mapper;

import com.jfonzuer.entities.User;
import com.jfonzuer.security.JwtUser;

/**
 * Created by pgm on 19/09/16.
 */
public class UserMapper {

    public static User fromDto(JwtUser dto) {
        return dto != null ? new User.UserBuilder()
                .setId((dto.getId() == null) ? null : dto.getId())
                .setEmail(dto.getEmail())
                .setUsername(dto.getUsername())
                .setDescription(dto.getDescription())
                .createUser() : null;
    }

    public static JwtUser toDto(User m) {
        return m != null ? new JwtUser.JwtUserBuilder()
                .setId(m.getId())
                .setEmail(m.getEmail())
                .setUsername(m.getUsername())
                .setDescription(m.getDescription())
                .createJwtUser() : null;
    }
}
