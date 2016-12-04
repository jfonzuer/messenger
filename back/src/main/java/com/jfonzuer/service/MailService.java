package com.jfonzuer.service;

import com.jfonzuer.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Locale;

/**
 * Created by pgm on 04/12/16.
 */
@Service
public class MailService {

    @Autowired
    private MailSender mailSender;

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${send.from.email}")
    private String fromEmail;

    @Value("${app.url}")
    private String appUrl;


    public void sendVisitNotification(Locale locale, User visited, User visitor) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        String loginUrl = appUrl + "unauth/login";
        try {
            //mimeMessage.setContent("Connectez vous à l'application pour consulter vos visites : <a href=" + loginUrl + ">Connexion</a>", "text/html; charset=utf-8");
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(this.fromEmail);
            helper.setTo("pgiraultmatz@gmail.com");
            //helper.setTo(visited.getEmail());
            helper.setSubject(visitor.getUsername() + " a visité votre profil");
            helper.setText("Connectez vous à l'application pour consulter vos visites : <a href=" + loginUrl + ">Connexion</a>", true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void sendMessageNotification(Locale locale, User user, User sender) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        String loginUrl = appUrl + "unauth/login";
        try {
            mimeMessage.setContent("Connectez vous à l'application pour consulter vos messages : <a href=" + loginUrl + ">Connexion</a>", "text/html; charset=utf-8");
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(this.fromEmail);
            //helper.setTo(user.getEmail());
            helper.setTo("pgiraultmatz@gmail.com");
            helper.setSubject(sender.getUsername() + " vous a envoyé un message votre profil");
            helper.setText("Connectez vous à l'application pour consulter vos messages : <a href=" + loginUrl + ">Connexion</a>", true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void sendResetTokenEmail(Locale locale, String token, User user) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        String resetLink = appUrl + "/password/reset/" + user.getId() + "/" +token;
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(this.fromEmail);
            //helper.setTo(user.getEmail());
            helper.setTo("pgiraultmatz@gmail.com");
            helper.setSubject("Réinitialiser le mot de passe");
            helper.setText("Cliquer sur le lien suivant pour réinitialiser le mot de passe : <a href=" + resetLink + ">Réinitialiser</a>", true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
