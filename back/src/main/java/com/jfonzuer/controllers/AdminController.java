package com.jfonzuer.controllers;

import com.jfonzuer.dto.UserDto;
import com.jfonzuer.dto.mapper.UserMapper;
import com.jfonzuer.entities.User;
import com.jfonzuer.repository.UserRepository;
import com.jfonzuer.service.UserService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by pgm on 23/01/17.
 */

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final UserRepository userRepository;
    private final UserService userService;

    @Autowired
    public AdminController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @RequestMapping(value = "/reported")
    public List<UserDto> getReportedUser(HttpServletRequest request) {
        return userRepository.findByReportedAsFakeGreaterThanOrderByReportedAsFakeDesc(0L).stream().map(UserMapper::toDto).collect(Collectors.toList());
    }

    @RequestMapping(value = "/block/{id}", method = RequestMethod.PUT)
    public void disable(HttpServletRequest request, @PathVariable Long id) {
        User user = getUserOrThrowException(id);
        user.setBlocked(true);
        userRepository.save(user);
    }

    @RequestMapping(value = "/unblock/{id}", method = RequestMethod.PUT)
    public void enable(HttpServletRequest request, @PathVariable Long id) {
        User user = getUserOrThrowException(id);
        user.setBlocked(false);
        userRepository.save(user);
    }

    private User getUserOrThrowException(Long id) {
        User user = userRepository.getOne(id);
        if (user == null) {
            throw new ResourceNotFoundException("L'utilisateur n'existe pas");
        }
        return user;
    }
}
