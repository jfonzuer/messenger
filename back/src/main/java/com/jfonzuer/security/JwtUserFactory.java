package com.jfonzuer.security;

import com.jfonzuer.entities.User;
import com.jfonzuer.entities.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class JwtUserFactory {

    private JwtUserFactory() {
    }

    public static JwtUser create(User user) {
        return new JwtUser.JwtUserBuilder()
                .setId(user.getId())
                .setUsername(user.getUsername())
                .setEmail(user.getEmail())
                .setDescription(user.getDescription())
                .setPassword(user.getPassword())
                .setLastPasswordResetDate(user.getLastPasswordResetDate())
                .setAuthorities(mapToGrantedAuthorities(user.getUserRoles()))
                .setEnabled(user.getEnabled())
                .createJwtUser();
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(Set<UserRole> userRoles) {
        return userRoles.stream()
                .map(userRole -> new SimpleGrantedAuthority(userRole.getRole()))
                .collect(Collectors.toList());
    }
}
