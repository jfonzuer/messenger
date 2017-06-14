package com.jfonzuer.runner;

import com.jfonzuer.entities.User;
import com.jfonzuer.repository.UserRepository;
import com.jfonzuer.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Created by pgm on 12/06/17.
 */
@Profile("downtime")
@Component
public class MailCLR implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(MailCLR.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailService mailService;

    @Override
    public void run(String... strings) throws Exception {

        List<User> userList = userRepository.findAll();
        List<User> users = userList.stream().skip(121).collect(Collectors.toList());

//        for (User user: users) {
//            try {
//                mailService.sendDownTimeMail(Locale.FRANCE, user);
//                LOGGER.debug("Sending downtime email to {}", user.getEmail());
//                Thread.sleep(15000);
//            } catch (Exception e) {
//                LOGGER.error("Exception ", e);
//            }
//
//        }
    }
}
