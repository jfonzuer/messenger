package com.jfonzuer.service;

import com.jfonzuer.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

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

    @Value("${send.from.email}")
    private String fromEmail;

    @Value("${app.url}")
    private String appUrl;

    @Value("${image.url}")
    private String imageUrl;

    private static String loginUrl;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    TemplateEngine templateEngine;


    @Value("${app.url}")
    public void setLoginUrl(String appUrl) {
        String loginUrl = appUrl + "unauth/login";
    }

    public void sendVisitNotification(Locale locale, User visited, User visitor) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            String visitorProfile = appUrl + "app/profile/" + visitor.getId();
            String imageSrc = imageUrl + visitor.getImages().stream().findFirst().get().getUrl() + "/image";

            // Prepare the evaluation context
            final Context ctx = new Context(locale);
            ctx.setVariable("visitedName", visited.getUsername());
            ctx.setVariable("visitorName", visitor.getUsername());
            ctx.setVariable("visitorProfile", visitorProfile);
            ctx.setVariable("imageSrc", imageSrc);
            ctx.setVariable("loginUrl", loginUrl);

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(this.fromEmail);
            //helper.setTo(user.getEmail());
            helper.setTo("pgiraultmatz@gmail.com");
            helper.setSubject("[Dominapp] "+ visitor.getUsername() + " a visité votre profil");

            // Create the HTML body using Thymeleaf
            final String htmlContent = templateEngine.process("visit", ctx);
            helper.setText(htmlContent, true); // true = isHtml
            javaMailSender.send(mimeMessage);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void sendMessageNotification(Locale locale, User user, User sender) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            String messengerUrl = appUrl + "app/conversation";
            String imageSrc = imageUrl + sender.getImages().stream().findFirst().get().getUrl() + "/image";

            // Prepare the evaluation context
            final Context ctx = new Context(locale);
            ctx.setVariable("userName", user.getUsername());
            ctx.setVariable("senderName", sender.getUsername());
            ctx.setVariable("messengerUrl", messengerUrl);
            ctx.setVariable("imageSrc", imageSrc);
            ctx.setVariable("loginUrl", loginUrl);

            helper.setFrom(this.fromEmail);
            //helper.setTo(user.getEmail());
            helper.setTo("pgiraultmatz@gmail.com");
            helper.setSubject("[Dominapp] "+ sender.getUsername() + " vous a envoyé un message");

            final String htmlContent = templateEngine.process("message", ctx);
            helper.setText(htmlContent, true);
            javaMailSender.send(mimeMessage);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void sendRegisterNotification(Locale locale, User user, String token) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            String validationUrl = appUrl + "unauth/home/" + token;

            // Prepare the evaluation context
            final Context ctx = new Context(locale);
            ctx.setVariable("userName", user.getUsername());
            ctx.setVariable("validationUrl", validationUrl);

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(this.fromEmail);
            //helper.setTo(user.getEmail());
            helper.setTo("pgiraultmatz@gmail.com");
            helper.setSubject("[Dominapp] Bienvenue sur Dominapp");
            final String htmlContent = templateEngine.process("register", ctx);
            helper.setText(htmlContent, true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void sendResetTokenEmail(Locale locale, String token, User user) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            String resetLink = appUrl + "unauth/password/reset/" + user.getId() + "/" +token;

            final Context ctx = new Context(locale);
            ctx.setVariable("resetLink", resetLink);
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(this.fromEmail);
            //helper.setTo(user.getEmail());
            helper.setTo("pgiraultmatz@gmail.com");
            helper.setSubject("[Dominapp] Réinitialiser votre mot de passe");
            final String htmlContent = templateEngine.process("reset-password", ctx);
            helper.setText(htmlContent, true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    public void sendActivationMail(Locale locale, String token, User user) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            String activationLink = appUrl + "unauth/home/"  + token;

            final Context ctx = new Context(locale);
            ctx.setVariable("activationLink", activationLink);
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(this.fromEmail);
            //helper.setTo(user.getEmail());
            helper.setTo("pgiraultmatz@gmail.com");
            helper.setSubject("[Dominapp] Activez votre compte");
            final String htmlContent = templateEngine.process("activation", ctx);
            helper.setText(htmlContent, true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
