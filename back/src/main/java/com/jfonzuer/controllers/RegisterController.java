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
@CrossOrigin(origins = "*", maxAge = 3600)
public class RegisterController {

    private PasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private MailService mailService;

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public void add(@RequestBody RegisterDto register) {
        // TODO : validation via annotation and exception handling
        System.out.println("register = " + register);

        if (!register.getPasswordConfirmation().getPassword().equals(register.getPasswordConfirmation().getConfirmation())) {
            throw new IllegalArgumentException();
        }
        if (userRepository.findByEmail(register.getUser().getEmail()) != null) {
            throw new IllegalArgumentException("L'adresse email est déjà utilisée");
        }

        User user = UserMapper.fromDto(register.getUser());
        user.setPassword(encoder.encode(register.getPasswordConfirmation().getPassword()));
        user.setEnabled(true);
        user.setBlocked(false);
        user.setLastPasswordResetDate(new Date());
        user.setReportedAsFake(0L);
        user.setLastActivityDate(LocalDate.now());
        user.setLastReportDate(LocalDate.now().minusDays(1));
        user.setNotifyVisit(true);
        user.setNotifyMessage(true);

        user = userRepository.save(user);
        userRoleRepository.save(new UserRole(user, "ROLE_USER"));

        // creation d'une image par défaut
        Stream.of(new Image.ImageBuilder().setOrderNumber(1).setUrl("profile.png").setUser(user).createImage()).forEach(i -> imageRepository.save(i));
    }

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

    @RequestMapping(value = "/password/reset", method = RequestMethod.POST)
    public void resetPassword(@RequestBody ResetPasswordDto resetPasswordDto) {

        User user = userRepository.findOne(resetPasswordDto.getUserId());
        System.out.println("userId = " + user.getId());
        Token token = tokenRepository.getByTokenAndUser(resetPasswordDto.getToken(), user);

        if (token == null) {
            throw new IllegalArgumentException("Aucun token ne correspond aux paramètres");
        }

        if (!resetPasswordDto.getPasswordConfirmation().getPassword().equals(resetPasswordDto.getPasswordConfirmation().getConfirmation())) {
            throw new IllegalArgumentException("Les mots de passes doivent être les mêmes.");
        }
        if (!token.getExpiryDate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Le token a expiré");
        }
        user.setPassword(encoder.encode(resetPasswordDto.getPasswordConfirmation().getPassword()));
        userRepository.save(user);
    }
}
