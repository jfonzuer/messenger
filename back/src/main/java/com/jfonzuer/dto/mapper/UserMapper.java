package com.jfonzuer.dto.mapper;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfonzuer.entities.Localization;
import com.jfonzuer.entities.User;
import com.jfonzuer.entities.UserRole;
import com.jfonzuer.entities.UserType;
import com.jfonzuer.exception.JsonMalformedException;
import com.jfonzuer.security.JwtUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by pgm on 17/10/16.
 */
public class UserMapper {

    // methode ne mappant pas les entitès liés
    public static JwtUser toLightDto(User user) {

        return new JwtUser.JwtUserBuilder()
                .setId(user.getId())
                .setUsername(user.getUsername())
                .setEmail(user.getEmail())
                .setDescription(user.getDescription())
                .setPassword(user.getPassword())
                .setBirthDate(user.getBirthDate() == null ? null : user.getBirthDate().toString())
                .setLocalization(user.getLocalization() == null ? null : LocalizationMapper.toDto(user.getLocalization()))
                .setLastPasswordResetDate(user.getLastPasswordResetDate())
                .setEnabled(user.getEnabled() == null ? true : user.getEnabled())
                .setImages(user.getImages().stream().map(ImageMapper::toDto).collect(Collectors.toList()))
                .setUserType(UserTypeMapper.toDto(user.getType()))
                .createJwtUser();
    }

    public static JwtUser toDto(User user) {
        return new JwtUser.JwtUserBuilder()
                .setId(user.getId())
                .setUsername(user.getUsername())
                .setEmail(user.getEmail())
                .setDescription(user.getDescription())
                .setPassword(user.getPassword())
                .setBirthDate(user.getBirthDate().toString())
                .setFetishes(user.getFetishes().stream().map(f -> FetishMapper.toDto(f)).collect(Collectors.toList()))
                .setLocalization(LocalizationMapper.toDto(user.getLocalization()))
                .setLastPasswordResetDate(user.getLastPasswordResetDate())
                .setEnabled(user.getEnabled())
                .setImages(user.getImages().stream().map(ImageMapper::toDto).collect(Collectors.toList()))
                .setUserType(UserTypeMapper.toDto(user.getType()))
                .setLastActivityDate(user.getLastActivityDate().toString())
                .setReportedAsFake(user.getReportedAsFake())
                .createJwtUser();
    }

    // methode qui mappe selon les besoin de l'authentification
    public static JwtUser toDtoWithAuthorities(User user) {
        return new JwtUser.JwtUserBuilder()
                .setUsername(user.getUsername())
                .setEmail(user.getEmail())
                .setPassword(user.getPassword())
                .setLastPasswordResetDate(user.getLastPasswordResetDate())
                .setEnabled(user.getEnabled() == null ? true : user.getEnabled())
                .setAuthorities(mapToGrantedAuthorities(user.getUserRoles()))
                .createJwtUser();
    }

    public static User fromDto(JwtUser dto) {
        return dto != null ? new User.UserBuilder()
                .setId((dto.getId() == null) ? null : dto.getId())
                .setEmail(dto.getEmail())
                .setUsername(dto.getUsername())
                .setBirthDate(dto.getBirthDate() == null ? null : DateMapper.toLocalDate(dto.getBirthDate()))
                .setDescription(dto.getDescription())
                .setLocalization(dto.getLocalization() == null ? null : LocalizationMapper.fromDto(dto.getLocalization()))
                .setFetishes(dto.getFetishes() == null ? null : dto.getFetishes().stream().map(FetishMapper::fromDto).collect(Collectors.toList()))
                .setType(dto.getUserType() == null ? null : UserTypeMapper.fromDto(dto.getUserType()))
                .createUser() : null;
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(Set<UserRole> userRoles) {
        return userRoles.stream()
                .map(userRole -> new SimpleGrantedAuthority(userRole.getRole()))
                .collect(Collectors.toList());
    }
}
