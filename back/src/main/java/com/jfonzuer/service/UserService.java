package com.jfonzuer.service;

import com.jfonzuer.repository.UserRepository;
import com.jfonzuer.security.JwtTokenUtil;
import com.jfonzuer.security.JwtUser;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.jfonzuer.entities.User;

/**
 * Created by pgm on 17/10/16.
 */
@Service
public class UserService {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserRepository userRepository;

    public User getUserFromToken(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        return this.userRepository.findByEmail(username);
    }
}
