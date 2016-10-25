package com.jfonzuer.controllers;

import com.jfonzuer.dto.PasswordDto;
import com.jfonzuer.dto.RegisterDto;
import com.jfonzuer.dto.ResetPasswordDto;
import com.jfonzuer.dto.mapper.UserMapper;
import com.jfonzuer.entities.Token;
import com.jfonzuer.entities.User;
import com.jfonzuer.entities.UserRole;
import com.jfonzuer.repository.TokenRepository;
import com.jfonzuer.repository.UserRepository;
import com.jfonzuer.repository.UserRoleRepository;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
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
    private MailSender mailSender;

    @Autowired
    private JavaMailSender javaMailSender;

    private SimpleMailMessage templateMessage;

    @Value("${send.from.email}")
    private String fromEmail;


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
        user.setLastPasswordResetDate(new Date());
        user = userRepository.save(user);
        userRoleRepository.save(new UserRole(user, "ROLE_USER"));
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

        //String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();

        String appUrl = "http://localhost:4200";

        System.out.println("appUrl = " + appUrl);

        constructResetTokenEmail(appUrl, request.getLocale(), token, user);
    }

    private void constructResetTokenEmail(String appUrl, Locale locale, String token, User user) {
        this.templateMessage = new SimpleMailMessage();
        this.templateMessage.setSubject("App - Réinitialiser le mot de passe");
        this.templateMessage.setFrom(this.fromEmail);
        this.templateMessage.setTo(user.getEmail());
        SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
        msg.setText("Cliquer sur le lien suivant pour réinitialiser le mot de passe : " + appUrl + "/password/reset/" + user.getId() + "/" + token);
        System.out.println("Sending email");
        this.mailSender.send(msg);
    }

    private void constructHtmlResetTokenEmailHtml(String appUrl, Locale locale, String token, User user) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        String resetLink = appUrl + "/password/reset/" + user.getId() + "/" +token;
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(this.fromEmail);
            helper.setTo(user.getEmail());
            helper.setSubject("Réinitialiser le mot de passe");
            helper.setText("Cliquer sur le lien suivant pour réinitialiser le mot de passe : <a href=" + resetLink + ">" + resetLink + "</a>");
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
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
