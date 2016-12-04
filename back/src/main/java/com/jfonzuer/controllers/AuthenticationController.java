package com.jfonzuer.controllers;

import com.jfonzuer.dto.AuthenticationRequestDto;
import com.jfonzuer.dto.RegisterDto;
import com.jfonzuer.dto.mapper.UserMapper;
import com.jfonzuer.entities.User;
import com.jfonzuer.entities.UserRole;
import com.jfonzuer.repository.UserRepository;
import com.jfonzuer.repository.UserRoleRepository;
import com.jfonzuer.security.JwtTokenUtil;
import com.jfonzuer.security.JwtUser;
import com.jfonzuer.dto.JwtAuthenticationResponse;
import com.jfonzuer.service.JwtUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.Date;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthenticationController {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequestDto authenticationRequest, Device device) throws AuthenticationException {

        // Perform the security
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        System.out.println("userRepository.findByEmail(authenticationRequest.getEmail()).getImages() = " + userRepository.findByEmail(authenticationRequest.getEmail()).getImages());
        System.out.println("userRepository.findByEmail(authenticationRequest.getEmail()).getImages() = " + userRepository.findByEmail(authenticationRequest.getEmail()).getFetishes());

        User user = userRepository.findByEmail(authenticationRequest.getEmail());

        // update last activity date
        user.setLastActivityDate(LocalDate.now());
        // set active
        user.setEnabled(true);
        userRepository.save(user);

        // Reload password post-security so we can generate token
        JwtUser jwtUser = UserMapper.toDto(user);
        System.out.println("jwtUser------------------------------------------- = " + jwtUser.getImages());
        System.out.println("jwtUser.getFetishes() = " + jwtUser.getFetishes());
        System.out.println("jwtUser.getAuthorities() = " + jwtUser.getAuthorities());

        final String token = jwtTokenUtil.generateToken(jwtUser, device);

        // Return the token
        return ResponseEntity.ok(new JwtAuthenticationResponse(token, jwtUser));
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "user", method = RequestMethod.GET)
    public JwtUser getCurrentUser(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        return UserMapper.toDto(this.userRepository.findByEmail(username));
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "refresh", method = RequestMethod.GET)
    public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);

        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user = userRepository.findByEmail(username);
        JwtUser jwtUser = UserMapper.toDto(userRepository.findByEmail(username));

        if (jwtTokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) {
            String refreshedToken = jwtTokenUtil.refreshToken(token);
            return ResponseEntity.ok(new JwtAuthenticationResponse(refreshedToken, jwtUser));
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
