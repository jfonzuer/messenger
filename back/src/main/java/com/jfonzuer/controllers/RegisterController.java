package com.jfonzuer.controllers;

import com.jfonzuer.dto.RegisterDto;
import com.jfonzuer.dto.ResetPasswordDto;
import com.jfonzuer.dto.mapper.UserMapper;
import com.jfonzuer.entities.Image;
import com.jfonzuer.entities.Token;
import com.jfonzuer.entities.User;
import com.jfonzuer.entities.UserRole;
import com.jfonzuer.repository.ImageRepository;
import com.jfonzuer.repository.TokenRepository;
import com.jfonzuer.repository.UserRepository;
import com.jfonzuer.repository.UserRoleRepository;
import com.jfonzuer.service.MailService;
import com.jfonzuer.service.UserService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Stream;

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

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public void add(@RequestBody RegisterDto register) {

        // TODO : validation via annotation and exception handling
        System.out.println("register = " + register);

        if (userRepository.findByEmail(register.getUser().getEmail()) != null) {
            throw new IllegalArgumentException("L'adresse email est déjà utilisée");
        }
        userService.createUser(register);
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
            throw new UsernameNotFoundException("User not found");
        }
        String token = UUID.randomUUID().toString();
        //Token t = new Token(token, user, LocalDate.now().plusDays(1L));

        // on supprime les tokens déjà créés pour cet utilisateur
        tokenRepository.getAllByUser(user).stream().forEach(t -> tokenRepository.delete(t));

        tokenRepository.save(new Token(token, user, LocalDate.now().plusDays(1L)));
        mailService.sendAsync(() -> mailService.sendResetTokenEmail(request.getLocale(), token, user));
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
