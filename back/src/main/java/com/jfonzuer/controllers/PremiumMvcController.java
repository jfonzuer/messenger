package com.jfonzuer.controllers;

import com.jfonzuer.entities.User;
import com.jfonzuer.entities.UserRole;
import com.jfonzuer.repository.UserRepository;
import com.jfonzuer.repository.UserRoleRepository;
import com.jfonzuer.service.SubscriptionService;
import com.jfonzuer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;

/**
 * Created by pgm on 11/03/17.
 */
@Controller
@RequestMapping("/premium")
public class PremiumMvcController {

    @Value("${app.url}")
    private String appUrl;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubscriptionService subscriptionService;

    @GetMapping("/{subscriptionId}/{userId}")
    public String premium(@PathVariable String subscriptionId, @PathVariable Long userId) {

        if (subscriptionService.checkIfSubscriptionExists(subscriptionId)) {
            User user = userService.findByIdOrThrowException(userId);
            user.setLastSubscriptionCheck(Timestamp.from(Instant.now()));
            user.setSubscriptionId(subscriptionId);
            userRepository.save(user);

            UserRole premium = UserRole.Builder.anUserRole().withUser(user).withRole("ROLE_PREMIUM").build();

            if (!user.getUserRoles().contains(premium)) {
                userRoleRepository.save(premium);
            }
            return "redirect:" + appUrl + "app/premium/success";
        } else {
            return "redirect:" + appUrl + "app/premium/error";
        }
    }
}