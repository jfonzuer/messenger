package com.jfonzuer.controllers;

import com.jfonzuer.dto.AuthenticationRequestDto;
import com.jfonzuer.dto.JwtAuthenticationResponse;
import com.jfonzuer.dto.UserDto;
import com.jfonzuer.dto.mapper.UserMapper;
import com.jfonzuer.entities.User;
import com.jfonzuer.exception.AccountNotActivatedException;
import com.jfonzuer.exception.UnauthorizedException;
import com.jfonzuer.repository.UserRepository;
import com.jfonzuer.security.JwtTokenUtil;
import com.jfonzuer.service.SubscriptionService;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@RestController
public class AuthenticationController {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubscriptionService subscriptionService;

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequestDto authenticationRequest, Device device) throws AuthenticationException {

        // Perform the security
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()
                )
        );

        User user = userRepository.findByEmail(authenticationRequest.getEmail());

        // update last activity date
        user.setLastActivityDatetime(LocalDateTime.now());
        // set active
        user.setEnabled(true);
        userRepository.save(user);

        //subscriptionService.checkSubscription(user);
        subscriptionService.checkSubscriptionAsync(user);

        if (user.getBlocked()) {
            throw new UnauthorizedException("Votre compte a été suspendu, contactez l'administration de l'application : contact@dominapp.com");
        }
        System.out.println("user.getActivated() = " + user.getActivated());
        if (!user.getActivated()) {
            throw new AccountNotActivatedException("Vous n'avez pas encore activé votre compte");
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Reload password post-security so we can generate token
        UserDto userDto = UserMapper.toDto(user);
        final String token = jwtTokenUtil.generateToken(userDto, device);

        // Return the token
        return ResponseEntity.ok(new JwtAuthenticationResponse(token, userDto));
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "user", method = RequestMethod.GET)
    public UserDto getCurrentUser(HttpServletRequest request) {
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
        UserDto userDto = UserMapper.toDto(userRepository.findByEmail(username));

        if (jwtTokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) {
            String refreshedToken = jwtTokenUtil.refreshToken(token);
            return ResponseEntity.ok(new JwtAuthenticationResponse(refreshedToken, userDto));
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
