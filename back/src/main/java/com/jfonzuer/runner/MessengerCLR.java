package com.jfonzuer.runner;

import com.jfonzuer.entities.User;
import com.jfonzuer.entities.Message;
import com.jfonzuer.entities.Conversation;
import com.jfonzuer.entities.UserRole;
import com.jfonzuer.repository.UserRepository;
import com.jfonzuer.repository.MessageRepository;
import com.jfonzuer.repository.ConversationRepository;
import com.jfonzuer.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by pgm on 19/09/16.
 */
@Component
public class MessengerCLR implements CommandLineRunner {

    private final UserRepository userRepository;
    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;
    private final UserRoleRepository userRoleRepository;

    @Autowired
    public MessengerCLR(UserRepository userRepository, ConversationRepository conversationRepository, MessageRepository messageRepository, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.conversationRepository = conversationRepository;
        this.messageRepository = messageRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    @Transactional
    public void run(String... strings) throws Exception {

        PasswordEncoder encoder = new BCryptPasswordEncoder();

        User u1 = new User.UserBuilder()
                .setEmail("member1@gmail.com")
                .setUsername("member1")
                //.setPassword("password1")
                .setPassword(encoder.encode("password1"))
                .setLastPasswordResetDate(new Date(System.currentTimeMillis() - 100000000))
                .setEnabled(true)
                .setDescription("je suis le membre 1")
                .createUser();
        User u2 = new User.UserBuilder()
                .setEmail("member13@gmail.com")
                .setUsername("member2")
                .setPassword(encoder.encode("password2"))
                //.setPassword("password2")
                .setLastPasswordResetDate(new Date(System.currentTimeMillis() - 100000000))
                .setEnabled(true)
                .setDescription("je suis le membre 2")
                .createUser();
        User u3 = new User.UserBuilder()
                .setEmail("member3@gmail.com")
                .setUsername("member3")
                //.setPassword("password3")
                .setLastPasswordResetDate(new Date(System.currentTimeMillis() - 100000000))
                .setPassword(encoder.encode("password3"))
                .setEnabled(true)
                .setDescription("je suis le membre 3")
                .createUser();
        // save members
        Stream.of(u1, u2, u3).forEach( m -> userRepository.save(m));


        UserRole us1 = new UserRole(u1, "ROLE_USER");
        UserRole us2 = new UserRole(u1, "ROLE_ADMIN");
        UserRole us3 = new UserRole(u2, "ROLE_USER");

        Stream.of(us1, us2, us3).forEach(us -> userRoleRepository.save(us));

        Conversation c1 = new Conversation.ConversationBuilder()
                .setId(1L)
                .setPreview("conversation 1")
                .setLastModified(LocalDateTime.now())
                .setUserOne(u1)
                .setUserTwo(u2)
                .setIsReadByUserOne(false)
                .setIsReadByUserTwo(false)
                .setIsDeletedByUserOne(false)
                .setIsDeletedByUserTwo(false)
                .createConversation();
        conversationRepository.save(c1);

        Conversation c2 = new Conversation.ConversationBuilder()
                .setId(2L)
                .setPreview("conversation 2")
                .setLastModified(LocalDateTime.of(2000, Month.APRIL, 2, 9, 30))
                .setUserOne(u1)
                .setUserTwo(u3)
                .setIsReadByUserOne(false)
                .setIsReadByUserTwo(false)
                .setIsDeletedByUserOne(false)
                .setIsDeletedByUserTwo(false)
                .createConversation();
        conversationRepository.save(c2);

        //memberRepository.flush();

        List<User> listMemberst1 = Arrays.asList(u1, u2);

        //Conversation t1 = new Conversation(Arrays.asList(u1,u2), Arrays.asList(ms1, ms2, ms3, ms4));


        Message ms1 = new Message.MessageBuilder().setId(1L).setSource(u1).setConversation(c1).setContent("Salut").setSentDateTime(LocalDateTime.now()).createMessage();
        Message ms2 = new Message.MessageBuilder().setId(2L).setSource(u2).setConversation(c1).setContent("Salut ça va ?").setSentDateTime(LocalDateTime.now()).createMessage();
        Message ms3 = new Message.MessageBuilder().setId(3L).setSource(u1).setConversation(c1).setContent("Parfait et toi gros frère ?").setSentDateTime(LocalDateTime.now()).createMessage();
        Message ms4 = new Message.MessageBuilder().setId(4L).setSource(u2).setConversation(c1).setContent("Parfaitement oklm bro ").setSentDateTime(LocalDateTime.now()).createMessage();
        Stream.of(ms1, ms2, ms3, ms4).forEach(ms -> messageRepository.save(ms));

        //messageRepository.findTop1ByOrderIdDesc().stream().forEach(System.out::println);
        //conversationRepository.flush();

        /*
        conversationRepository.findAll().forEach(s -> System.out.println(s.getMessages()));

        //conversationRepository.flush();
        User u11 = memberRepository.findOne(1L);
        u11.setEmail("member 11111");
        memberRepository.save(u11);

        Message ms5 = new Message(u2,u1, "sisi la famille ", LocalDateTime.now());


        Conversation t2 = conversationRepository.findOne(1L);

        System.out.println(Stream.of(t2.getMessages(), Arrays.asList(ms5)).flatMap(s -> s.stream()).collect(Collectors.toList()));

        t2.setMessages(Stream.concat(t2.getMessages().stream(), Arrays.asList(ms5).stream()).collect(Collectors.toList()));
        //t2.setMessages(Stream.of(t2.getMessages(), Arrays.asList(ms5)).flatMap(s -> s.stream()).collect(Collectors.toList()));
        //t2.getMessages().add(ms5);

        //System.out.println(t2);
        //conversationRepository.flush();
        //conversationRepository.save(t2);


        //conversationRepository.findAll().forEach(s -> System.out.println(s.getUsers()));

        //messageRepository.findByThread(t1).forEach(System.out::println);

        //messageRepository.findByThread(t1).forEach(s -> System.out.println("ok"));

        //conversationRepository.findAll().forEach(s -> s.getMessages().forEach(System.out::println));

        /*
        t1.setMessages(Arrays.asList(ms1, ms2, ms3, ms3, ms4));
        conversationRepository.save(t1);
        conversationRepository.flush();
        */

        //Stream.of(ms1, ms2, ms3, ms4).forEach(ms -> messageRepository.save(ms));

        //t1.setMessages(Arrays.asList(ms1, ms2, ms3, ms3, ms4));

        /*
        List<Message> u1u2 = Arrays.asList(
                new Message(u1,u2, "Salut", LocalDateTime.now()),
                new Message(u2,u1, "Salut ça va ?", LocalDateTime.now()),
                new Message(u1,u2, "Parfait et toi gros frère ?", LocalDateTime.now()),
                new Message(u2,u1, "Parfaitement oklm bro ", LocalDateTime.now())
        );


        Conversation t1 = new Conversation(listMemberst1, u1u2);
        conversationRepository.save(t1);



        memberRepository.findAll().forEach(System.out::println);
        */
    }
}
