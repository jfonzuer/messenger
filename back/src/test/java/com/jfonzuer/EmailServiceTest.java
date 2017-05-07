package com.jfonzuer;

import com.jfonzuer.entities.User;
import com.jfonzuer.service.MailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;

import java.util.Locale;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmailServiceTest {

	@InjectMocks
	MailService mailService;

	@InjectMocks
	JavaMailSender javaMailSender;

	@InjectMocks
	TemplateEngine templateEngine;

	@Test
	public void test_sendVisitNotificationEmail() {
		mailService.sendVisitNotification(Locale.FRANCE, User.Builder.anUser().withId(1L).build(), User.Builder.anUser().withId(2L).build());
	}

}
