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

        return UserDto.Builder.anUserDto()
                .withId(user.getId())
                .withUsername(user.getUsername())
                .withEmail(user.getEmail())
                .withDescription(user.getDescription())
                .withBirthDate(user.getBirthDate() == null ? null : user.getBirthDate().toString())
                .withArea(user.getArea() == null ? null : AreaMapper.toDto(user.getArea()))
                .withImages(user.getImages() == null ? null : user.getImages().stream().map(ImageMapper::toDto).collect(Collectors.toList()))
                .withCountry(user.getCountry() ==  null ? null : CountryMapper.toDto(user.getCountry()))
                .withUserType(UserTypeMapper.toDto(user.getType()))
                .withEnabled(user.getEnabled())
                .withIsBlocked(user.getBlocked())
                .withReportedAsFake(user.getReportedAsFake())
                .withLastActivityDatetime(user.getLastActivityDatetime().toString())
                .build();
    }

    public static UserDto toDto(User user) {
        return UserDto.Builder.anUserDto()
                .withId(user.getId())
                .withUsername(user.getUsername())
                .withEmail(user.getEmail())
                .withDescription(user.getDescription())
                .withBirthDate(user.getBirthDate().toString())
                .withFetishes(user.getFetishes().stream().map(f -> FetishMapper.toDto(f)).collect(Collectors.toList()))
                .withArea(AreaMapper.toDto(user.getArea()))
                .withCountry(CountryMapper.toDto(user.getCountry()))
                .withImages(user.getImages().stream().map(ImageMapper::toDto).collect(Collectors.toList()))
                .withUserType(UserTypeMapper.toDto(user.getType()))
                .withLastActivityDatetime(user.getLastActivityDatetime().toString())
                .withAuthorities(mapAuthorities(user.getUserRoles()))
                .withEnabled(user.getEnabled())
                .withIsBlocked(user.getBlocked())
                .withReportedAsFake(user.getReportedAsFake())
                .withNotifyVisit(user.getNotifyVisit())
                .withNotifyMessage(user.getNotifyMessage())
                .withBlockedUsers(user.getBlockedUsers() != null ? user.getBlockedUsers().stream().map(UserMapper::toDto).collect(Collectors.toList()) : null)
                .build();
    }

    public static User fromDto(UserDto dto) {
        return dto != null ? User.Builder.anUser()
                .withId((dto.getId() == null) ? null : dto.getId())
                .withEmail(dto.getEmail())
                .withUsername(dto.getUsername())
                .withBirthDate(dto.getBirthDate() == null ? null : DateMapper.toLocalDate(dto.getBirthDate()))
                .withDescription(dto.getDescription())
                .withCountry(dto.getCountry() == null ? null : CountryMapper.fromDto(dto.getCountry()))
                .withArea(dto.getArea() == null ? null : AreaMapper.fromDto(dto.getArea()))
                .withFetishes(dto.getFetishes() == null ? null : dto.getFetishes().stream().map(FetishMapper::fromDto).collect(Collectors.toList()))
                .withType(dto.getUserType() == null ? null : UserTypeMapper.fromDto(dto.getUserType()))
                .build() : null;
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
