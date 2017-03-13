package com.jfonzuer.controllers;

import com.jfonzuer.dto.RegisterDto;
import com.jfonzuer.dto.ResetPasswordDto;
import com.jfonzuer.dto.mapper.UserMapper;
import com.jfonzuer.entities.Token;
import com.jfonzuer.entities.User;
import com.jfonzuer.repository.TokenRepository;
import com.jfonzuer.repository.UserRepository;
import com.jfonzuer.service.AsyncService;
import com.jfonzuer.service.MailService;
import com.jfonzuer.service.TokenService;
import com.jfonzuer.service.UserService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Created by pgm on 23/10/16.
 */

@RestController
public class RegisterController {

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

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public void add(@RequestBody RegisterDto register, HttpServletRequest request) {

        // TODO : validation via annotation and exception handling

        System.out.println("register = " + register);
        User user = UserMapper.fromDto(register.getUser());

        if (userRepository.findByEmail(register.getUser().getEmail()) != null) {
            throw new IllegalArgumentException("L'adresse email est déjà utilisée");
        }
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
    public void resetPassword(HttpServletRequest request, @RequestBody String email) {
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
    public void resetPassword(@RequestBody ResetPasswordDto resetPasswordDto) {
        userService.resetPassword(resetPasswordDto);
    }
}
