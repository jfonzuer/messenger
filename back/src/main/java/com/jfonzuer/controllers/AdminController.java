package com.jfonzuer.controllers;

import com.jfonzuer.dto.UserDto;
import com.jfonzuer.dto.mapper.UserMapper;
import com.jfonzuer.entities.User;
import com.jfonzuer.repository.UserRepository;
import com.jfonzuer.service.MailService;
import com.jfonzuer.service.UserService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Created by pgm on 23/01/17.
 */

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailService mailService;

    @GetMapping(value ="/reported")
    public List<UserDto> getReportedUser(HttpServletRequest request) {
        return userRepository.findByReportedAsFakeGreaterThanOrderByReportedAsFakeDesc(0L).stream().map(UserMapper::toDto).collect(Collectors.toList());
    }
    @GetMapping(value = "/email")
    public void sendEmail(HttpServletRequest request) {
        mailService.sendInformationToAll();
    }

    @PutMapping(value = "/block/{id}")
    public void disable(HttpServletRequest request, @PathVariable Long id) {
        User user = getUserOrThrowException(id);
        user.setBlocked(true);
        userRepository.save(user);
    }

    @PutMapping(value = "/unblock/{id}")
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
