package com.jfonzuer.security;

import com.jfonzuer.dto.mapper.UserMapper;
import com.jfonzuer.entities.User;
import com.jfonzuer.exception.UnauthorizedException;
import com.jfonzuer.repository.UserRepository;
import com.jfonzuer.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class JwtAuthenticationTokenFilter extends GenericFilterBean {

    @Autowired
    private UserRepository userRepository;

    private final static Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationTokenFilter.class);

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.header}")
    private String tokenHeader;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String authToken = httpRequest.getHeader(this.tokenHeader);

        String username = jwtTokenUtil.getUsernameFromToken(authToken);


        if (username != null && SecurityContextHolder.getContext().getAuthentication() != null) {

            User user = userRepository.findByEmail(username);
            LOGGER.debug("found user with id {}", user.getId());

            if (user.getBlocked()) {
                LOGGER.debug("user with id {} is desactivated", user.getId());
                throw new UnauthorizedException("Votre compte a été désactivé. Contactez nous à l'adresse suivante : contact@dominapp.com");
            }

            user.setAuthorities(UserMapper.mapToGrantedAuthorities(user.getUserRoles()));

            if (jwtTokenUtil.validateToken(authToken, user)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        chain.doFilter(request, response);
    }
}