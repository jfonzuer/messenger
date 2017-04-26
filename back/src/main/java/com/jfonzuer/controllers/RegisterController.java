package com.jfonzuer.controllers;

import com.jfonzuer.dto.RegisterDto;
import com.jfonzuer.dto.ResetPasswordDto;
import com.jfonzuer.dto.mapper.UserMapper;
import com.jfonzuer.entities.Token;
import com.jfonzuer.entities.User;
import com.jfonzuer.repository.TokenRepository;
import com.jfonzuer.repository.UserRepository;
import com.jfonzuer.service.*;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Created by pgm on 23/10/16.
 */

@RestController
public class RegisterController {

    private final static Logger LOGGER = LoggerFactory.getLogger(RegisterController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private MailService mailService;

    @Autowired
    private AsyncService asyncService;

    @Autowired
    private TokenService tokenService;

    /**
     * endpoint permettant à un utilisateur de se register
     * @param register
     * @param request
     */
    @RequestMapping(value = "register", method = RequestMethod.POST)
    public void add(@Valid @RequestBody RegisterDto register, HttpServletRequest request) {
        User user = UserMapper.fromDto(register.getUser());
        LOGGER.debug("user.getTypes() = {}", user.getType());

        if (userRepository.findByEmail(register.getUser().getEmail()) != null) {
            throw new IllegalArgumentException("L'adrcesse email est déjà utilisée");
        }
        if (userRepository.findByEmail(register.getUser().getEmail()) != null) {
            throw new IllegalArgumentException("Le nom d'utilisateur est déjà utilisée");
        }
        LOGGER.debug("user.getArea() = {}", user.getArea());

        userService.createUser(user, register.getPassword());
        String token = UUID.randomUUID().toString();
        tokenRepository.save(new Token(token, user, LocalDate.now().plusDays(1L)));
        mailService.sendRegisterNotification(request.getLocale(), user, token);
    }

    /**
     * endpoint permettant l'envoi d'un mail et la création d'un token afin que celui-ci puisse ensuite changer son mdp
     * @param request
     * @param email
     */
    @RequestMapping(value = "password/reset/mail", method = RequestMethod.POST)
    public void resetPassword(HttpServletRequest request, @NotNull @RequestBody String email) {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("L'utilisateur n'a pas pu être trouvé");
        }
        String token = UUID.randomUUID().toString();
        tokenService.save(user, token);
        asyncService.executeAsync(() -> mailService.sendResetTokenEmail(request.getLocale(), token, user));
    }

    @PostMapping(value = "resend/activation/email")
    public void sendActivationMail(HttpServletRequest request, @RequestBody String email) {
        User user = userService.findByEmailOrThrowException(email);
        if (user.getActivated()) {
            throw new IllegalArgumentException("Votre compte est déjà activé, veuillez contacter dominapp.contact@gmail.com");
        }
        String token = UUID.randomUUID().toString();
        tokenService.save(user, token);
        mailService.sendActivationMail(request.getLocale(), token, user);
    }


    @RequestMapping(value = "validate/account", method = RequestMethod.POST)
    public void activateAccount(@RequestBody String token) {
        Token t = tokenRepository.getByToken(token);
        if (t == null) {
            throw new ResourceAccessException("Le token n'existe pas.");
        }
        User user = t.getUser();
        user.setActivated(true);
        userRepository.save(user);
    }

    /**
     * Méthode utilisée lors du reset de password lorsque l'utilisateur a oublié son mdp
     * @param resetPasswordDto
     */
    @RequestMapping(value = "/password/reset", method = RequestMethod.POST)
    public void resetPassword(@Valid @RequestBody ResetPasswordDto resetPasswordDto) {
        userService.resetPassword(resetPasswordDto);
    }
}
