package com.jfonzuer.service;

import com.jfonzuer.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(MailService.class);

    @Value("${send.from.email}")
    private String fromEmail;

    @Value("${send.to.email}")
    private String sendTo;

    @Value("${app.url}")
    private String appUrl;

    @Value("${image.url}")
    private String imageUrl;

    private static String loginUrl;

    @Value("${spring.profiles.active}")
    private String profile;

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
            setTo(helper, visited.getEmail());
            helper.setSubject("[Dominapp] "+ visitor.getUsername() + " a visité votre profil");

            // Create the HTML body using Thymeleaf
            final String htmlContent = templateEngine.process("mail/visit", ctx);
            helper.setText(htmlContent, true); // true = isHtml
            LOGGER.debug("Send visit mail to : {}", visited.getEmail());

            javaMailSender.send(mimeMessage);

        } catch (MessagingException e) {
            LOGGER.error("Error when sending visit notification mail to : {}", visited.getEmail(), e);
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
            setTo(helper, user.getEmail());
            helper.setSubject("[Dominapp] "+ sender.getUsername() + " vous a envoyé un message");

            final String htmlContent = templateEngine.process("mail/message", ctx);
            helper.setText(htmlContent, true);
            LOGGER.debug("Send message notification mail to : {}", user.getEmail());

            javaMailSender.send(mimeMessage);

        } catch (MessagingException e) {
            LOGGER.error("Error when sending message notification to : {}", user.getEmail(), e);
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

            setTo(helper, user.getEmail());

            helper.setSubject("[Dominapp] Bienvenue sur Dominapp");
            final String htmlContent = templateEngine.process("mail/register", ctx);
            helper.setText(htmlContent, true);
            LOGGER.debug("Send register mail to : {}", user.getEmail());
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            LOGGER.error("Error when sending register notification mail to : {}", user.getEmail(), e);
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
            setTo(helper, user.getEmail());
            helper.setSubject("[Dominapp] Réinitialiser votre mot de passe");
            final String htmlContent = templateEngine.process("mail/reset-password", ctx);
            helper.setText(htmlContent, true);
            LOGGER.debug("Send reset mail to : {}", user.getEmail());
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            LOGGER.error("Error when sending reset token mail to : {}", user.getEmail(), e);
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
            setTo(helper, user.getEmail());
            helper.setSubject("[Dominapp] Activez votre compte");
            final String htmlContent = templateEngine.process("mail/activation", ctx);
            helper.setText(htmlContent, true);
            LOGGER.debug("Send activation mail to : {}", user.getEmail());

            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            LOGGER.error("Error when sending activation mail to : {}", user.getEmail(), e);
        }
    }

    public void sendDownTimeMail(Locale locale, User user) {
        try {

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();

            final Context ctx = new Context(locale);
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            ctx.setVariable("userName", user.getUsername());
            ctx.setVariable("loginUrl", loginUrl);
            helper.setFrom(this.fromEmail);
            setTo(helper, user.getEmail());
            helper.setSubject("[Dominapp] Dominapp est de nouveau en ligne");
            final String htmlContent = templateEngine.process("mail/downtime", ctx);
            helper.setText(htmlContent, true);
            LOGGER.debug("Send downtime mail to : {}", user.getEmail());

            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            LOGGER.error("Error when sending downtime mail to : {}", user.getEmail(), e);
        }
    }

    private void setTo(MimeMessageHelper helper, String email) throws MessagingException {
        if (profile.equals("dev")) {
            LOGGER.debug("dev profile, sending mail to {}", sendTo);
            helper.setTo(sendTo);
            return;
        }
        helper.setTo(email);
    }
}
