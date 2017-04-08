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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RunnableFuture;

/**
 * Created by pgm on 04/12/16.
 */
@Service
public class MailService {

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
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(this.fromEmail);
            //helper.setTo(user.getEmail());
            helper.setTo("pgiraultmatz@gmail.com");
            helper.setSubject("[Dominapp] "+ visitor.getUsername() + " a visité votre profil");
            helper.setText(
                    "Bonjour " + visited.getUsername() + ",<br><br>" +
                            visited.getUsername() + " a visité votre profil <br><br> " +
                            "Connectez vous à l'application pour consulter vos visites : <a href=" + loginUrl + ">Connexion</a> <br><br> " +
                            "L'équipe Dominapp.", true);
            javaMailSender.send(mimeMessage);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void sendMessageNotification(Locale locale, User user, User sender) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        String loginUrl = appUrl + "unauth/login";
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(this.fromEmail);
            //helper.setTo(user.getEmail());
            helper.setTo("pgiraultmatz@gmail.com");
            helper.setSubject("[Dominapp] "+ sender.getUsername() + " vous a envoyé un message");
            helper.setText(
                    "Bonjour " + user.getUsername() + ",<br><br>" +
                            sender.getUsername() + " vous a envoyé un message <br><br> " +
                            "Connectez vous à l'application pour consulter vos messages : <a href=" + loginUrl + ">Connexion</a> <br><br> " +
                            "L'équipe Dominapp.", true);
            javaMailSender.send(mimeMessage);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void sendRegisterNotification(Locale locale, User user, String token) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        String validationUrl = appUrl + "unauth/home/" + token;
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(this.fromEmail);
            //helper.setTo(user.getEmail());
            helper.setTo("pgiraultmatz@gmail.com");
            helper.setSubject("[Dominapp] Bienvenue sur Dominapp");
            helper.setText(
                    "Bonjour " + user.getUsername() + ",<br><br>" +
                            "Merci de vous être inscris sur Dominapp. <br><br> " +
                            "Validez votre compte en cliquant sur ce lien : <a href=" + validationUrl + ">Je valide mon compte</a> <br><br> " +
                            "L'équipe Dominapp.", true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void sendResetTokenEmail(Locale locale, String token, User user) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        String resetLink = appUrl + "unauth/password/reset/" + user.getId() + "/" +token;
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(this.fromEmail);
            //helper.setTo(user.getEmail());
            helper.setTo("pgiraultmatz@gmail.com");
            helper.setSubject("[Dominapp] Réinitialiser votre mot de passe");
            helper.setText(
                    "Bonjour " + user.getUsername() + ",<br><br>" +
                            "Merci de vous être inscris sur Dominapp. <br><br> " +
                            "Pour réinitialiser votre mot de passer, cliquer sur le lien suivant : <a href=" + resetLink + ">Réinitialiser</a> <br><br> " +
                            "L'équipe Dominapp.", true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    public void sendActivationMail(Locale locale, String token, User user) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        String activationLink = appUrl + "unauth/home/"  + token;
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(this.fromEmail);
            //helper.setTo(user.getEmail());
            helper.setTo("pgiraultmatz@gmail.com");
            helper.setSubject("[Dominapp] Activez votre compte");
            helper.setText(
                    "Bonjour " + user.getUsername() + ",<br><br>" +
                            "Cliquez sur le lien suivant pour activer votre compte : <a href=" + activationLink + ">Activer</a> <br><br>" +
                            "L'équipe Dominapp.", true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
