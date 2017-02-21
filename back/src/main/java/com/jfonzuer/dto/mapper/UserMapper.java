package com.jfonzuer.dto.mapper;

import com.jfonzuer.dto.UserDto;
import com.jfonzuer.entities.Country;
import com.jfonzuer.entities.User;
import com.jfonzuer.entities.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by pgm on 17/10/16.
 */
public class UserMapper {

    // methode ne mappant pas les entitès liés
    public static UserDto toLightDto(User user) {

        return new UserDto.UserDtoBuilder()
                .setId(user.getId())
                .setUsername(user.getUsername())
                .setEmail(user.getEmail())
                .setDescription(user.getDescription())
                .setBirthDate(user.getBirthDate() == null ? null : user.getBirthDate().toString())
                .setArea(user.getArea() == null ? null : AreaMapper.toDto(user.getArea()))
                .setImages(user.getImages() == null ? null : user.getImages().stream().map(ImageMapper::toDto).collect(Collectors.toList()))
                .setCountry(user.getCountry() ==  null ? null : CountryMapper.toDto(user.getCountry()))
                .setIsPremium(user.getPremium())
                .setUserType(UserTypeMapper.toDto(user.getType()))
                .setEnabled(user.getEnabled())
                .setIsBlocked(user.getBlocked())
                .setReportedAsFake(user.getReportedAsFake())
                .createJwtUser();
    }

    public static UserDto toDto(User user) {
        return new UserDto.UserDtoBuilder()
                .setId(user.getId())
                .setUsername(user.getUsername())
                .setEmail(user.getEmail())
                .setDescription(user.getDescription())
                .setBirthDate(user.getBirthDate().toString())
                .setFetishes(user.getFetishes().stream().map(f -> FetishMapper.toDto(f)).collect(Collectors.toList()))
                .setArea(AreaMapper.toDto(user.getArea()))
                .setCountry(CountryMapper.toDto(user.getCountry()))
                .setImages(user.getImages().stream().map(ImageMapper::toDto).collect(Collectors.toList()))
                .setUserType(UserTypeMapper.toDto(user.getType()))
                .setLastActivityDate(user.getLastActivityDate().toString())
                .setAuthorities(mapAuthorities(user.getUserRoles()))
                .setEnabled(user.getEnabled())
                .setIsBlocked(user.getBlocked())
                .setReportedAsFake(user.getReportedAsFake())
                .setNotifyVisit(user.getNotifyVisit())
                .setNotifyMessage(user.getNotifyMessage())
                .setBlockedUsers(user.getBlockedUsers() != null ? user.getBlockedUsers().stream().map(UserMapper::toDto).collect(Collectors.toList()) : null)
                .createJwtUser();
    }

    public static User fromDto(UserDto dto) {
        return dto != null ? new User.UserBuilder()
                .setId((dto.getId() == null) ? null : dto.getId())
                .setEmail(dto.getEmail())
                .setUsername(dto.getUsername())
                .setBirthDate(dto.getBirthDate() == null ? null : DateMapper.toLocalDate(dto.getBirthDate()))
                .setDescription(dto.getDescription())
                .setCountry(dto.getCountry() == null ? null : CountryMapper.fromDto(dto.getCountry()))
                .setArea(dto.getArea() == null ? null : AreaMapper.fromDto(dto.getArea()))
                .setIsPremium(dto.getPremium())
                .setFetishes(dto.getFetishes() == null ? null : dto.getFetishes().stream().map(FetishMapper::fromDto).collect(Collectors.toList()))
                .setType(dto.getUserType() == null ? null : UserTypeMapper.fromDto(dto.getUserType()))
                .createUser() : null;
    }

    public static List<GrantedAuthority> mapToGrantedAuthorities(Set<UserRole> userRoles) {
        return userRoles.stream()
                .map(userRole -> new SimpleGrantedAuthority(userRole.getRole()))
                .collect(Collectors.toList());
    }

    private static List<String> mapAuthorities(Set<UserRole> userRoles) {
        return userRoles.stream()
                .map(userRole -> userRole.getRole())
                .collect(Collectors.toList());
    }
}
