package com.jfonzuer.runner;

import com.jfonzuer.entities.*;
import com.jfonzuer.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Stream;

/**
 * Created by pgm on 16/04/17.
 */
@Component
@Profile({"dev", "preprod"})
@Order(2)
public class FillDatabaseCLR implements CommandLineRunner {

    @Value("${image.default.name}")
    private String defaultImage;

    private final PasswordEncoder encoder;
    private final UserRepository userRepository;
    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;
    private final ImageRepository imageRepository;
    private final UserRoleRepository userRoleRepository;
    private final VisitRepository visitRepository;

    @Autowired
    public FillDatabaseCLR(PasswordEncoder encoder, UserRepository userRepository, ConversationRepository conversationRepository, MessageRepository messageRepository, ImageRepository imageRepository, UserRoleRepository userRoleRepository, VisitRepository visitRepository) {
        this.encoder = encoder;
        this.userRepository = userRepository;
        this.conversationRepository = conversationRepository;
        this.messageRepository = messageRepository;
        this.imageRepository = imageRepository;
        this.userRoleRepository = userRoleRepository;
        this.visitRepository = visitRepository;
    }

    @Override
    @Transactional
    public void run(String... strings) throws Exception {

        Collection<Fetish> fetishes = Arrays.asList(new Fetish.FetishBuilder().setId(1L).createFetish(), new Fetish.FetishBuilder().setId(1L).createFetish());
        Country c1 = Country.Builder.country().withId(1L).build();
        Country c2 = Country.Builder.country().withId(2L).build();
        Country c3 = Country.Builder.country().withId(3L).build();
        Area a1 = new Area.AreaBuilder().setId(1L).createArea();
        Area a2 = new Area.AreaBuilder().setId(28L).createArea();
        Area a3 = new Area.AreaBuilder().setId(37L).createArea();
        UserType ut1 = UserType.Builder.anUserType().withId(1L).build();
        UserType ut2 = UserType.Builder.anUserType().withId(2L).build();

        User u1 = User.Builder.anUser()
                .withEmail("contact@dominapp.com")
                .withBirthDate(LocalDate.of(1990, 1, 1))
                .withUsername("Jack")
                .withPassword(encoder.encode("test"))
                .withLastPasswordResetDate(new Date(System.currentTimeMillis() - 100000000))
                .withEnabled(true)
                .withIsBlocked(false)
                .withDescription("")
                .withFetishes(fetishes)
                .withArea(a1)
                .withCountry(c1)
                .withType(ut1)
                .withReportedAsFake(0L)
                .withLastActivityDatetime(LocalDateTime.now())
                .withLastReportDate(LocalDate.now().minusDays(1L))
                .withNotifyMessage(true)
                .withNotifyVisit(true)
                .withActivated(true)
                .build();
        User u2 = User.Builder.anUser()
                .withEmail("u2@gmail.com")
                .withUsername("member2")
                .withPassword(encoder.encode("test"))
                .withLastPasswordResetDate(new Date(System.currentTimeMillis() - 100000000))
                .withEnabled(true)
                .withIsBlocked(false)
                .withDescription("je suis le membre 2")
                .withBirthDate(LocalDate.of(1988, 3, 29))
                .withArea(a2)
                .withCountry(c2)
                .withType(ut2)
                .withReportedAsFake(10L)
                .withLastActivityDatetime(LocalDateTime.now())
                .withLastReportDate(LocalDate.now().minusDays(1L))
                .withNotifyMessage(true)
                .withNotifyVisit(true)
                .withActivated(true)
                .build();
        User u3 = User.Builder.anUser()
                .withEmail("u3@gmail.com")
                .withUsername("member3")
                .withLastPasswordResetDate(new Date(System.currentTimeMillis() - 100000000))
                .withPassword(encoder.encode("test"))
                .withEnabled(true)
                .withIsBlocked(false)
                .withDescription("je suis le membre 3")
                .withBirthDate(LocalDate.of(1988, 1, 1))
                .withArea(a3)
                .withCountry(c3)
                .withType(ut1)
                .withReportedAsFake(15L)
                .withLastActivityDatetime(LocalDateTime.now().minusHours(1))
                .withLastReportDate(LocalDate.now().minusDays(1L))
                .withNotifyMessage(true)
                .withNotifyVisit(true)
                .withActivated(true)
                .build();

        User u4 = User.Builder.anUser()
                .withEmail("u4@gmail.com")
                .withUsername("member4")
                .withLastPasswordResetDate(new Date(System.currentTimeMillis() - 100000000))
                .withPassword(encoder.encode("test"))
                .withEnabled(true)
                .withIsBlocked(false)
                .withDescription("je suis le membre 4")
                .withBirthDate(LocalDate.of(1988, 1, 1))
                .withArea(a1)
                .withCountry(c1)
                .withType(ut2)
                .withReportedAsFake(20L)
                .withLastActivityDatetime(LocalDateTime.now().minusHours(7))
                .withLastReportDate(LocalDate.now().minusDays(1L))
                .withNotifyMessage(true)
                .withNotifyVisit(true)
                .withActivated(true)
                // last subscription 1 day ago
                .withLastSubscriptionCheck(Timestamp.from(Instant.now().minusSeconds(24 * 3600)))
                .withSubscriptionId("Hr551C2QDhJT4baIQ")
                .build();

        u1.setBlockedUsers(new HashSet<>(Arrays.asList(u2)));

        // save users
        Stream.of(u1, u2, u3, u4).forEach(m -> userRepository.save(m));

        Image u1i1 = Image.Builder.anImage().withOrderNumber(1).withUrl(defaultImage).withUser(u1).build();
        Image u2i1 = Image.Builder.anImage().withOrderNumber(1).withUrl(defaultImage).withUser(u2).build();
        Image u3i1 = Image.Builder.anImage().withOrderNumber(1).withUrl(defaultImage).withUser(u3).build();
        Image u4i1 = Image.Builder.anImage().withOrderNumber(1).withUrl(defaultImage).withUser(u4).build();
        Stream.of(u1i1, u2i1, u3i1, u4i1).forEach(i -> imageRepository.save(i));

        UserRole us1 = new UserRole(u1, "ROLE_USER");
        UserRole us2 = new UserRole(u1, "ROLE_ADMIN");
        UserRole us3 = new UserRole(u1, "ROLE_PREMIUM");
        UserRole us4 = new UserRole(u2, "ROLE_USER");
        UserRole us5 = new UserRole(u3, "ROLE_USER");
        UserRole us6 = new UserRole(u4, "ROLE_USER");
        UserRole us7 = new UserRole(u4, "ROLE_PREMIUM");
        UserRole us8 = new UserRole(u3, "ROLE_PREMIUM");
        Stream.of(us1, us2, us3, us4, us5, us6, us7, us8).forEach(us -> userRoleRepository.save(us));

        // visits
        Visit v1 = new Visit(LocalDate.now(), u2, u1, false);
        Visit v2 = new Visit(LocalDate.now(), u3, u1, false);
        Visit v3 = new Visit(LocalDate.now(), u2, u1, false);
        Stream.of(v1, v2, v3).forEach(v -> this.visitRepository.save(v));

        Conversation conv1 = new Conversation.ConversationBuilder()
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
        conversationRepository.save(conv1);

        Conversation conv2 = new Conversation.ConversationBuilder()
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
        conversationRepository.save(conv2);

        //memberRepository.flush();

        List<User> listMemberst1 = Arrays.asList(u1, u2);

        Message ms1 = new Message.MessageBuilder().setId(1L).setSource(u1).setConversation(conv1).setContent("Salut").setSentDateTime(LocalDateTime.now()).setUserOne(u1).setUserTwo(u2).setIsDeletedByUserOne(false).setIsDeletedByUserTwo(false).setType(MessageType.TEXT).createMessage();
        Message ms2 = new Message.MessageBuilder().setId(2L).setSource(u2).setConversation(conv1).setContent("Salut ça va ?").setSentDateTime(LocalDateTime.now()).setUserOne(u1).setUserTwo(u2).setIsDeletedByUserOne(false).setIsDeletedByUserTwo(false).setType(MessageType.TEXT).createMessage();
        Message ms3 = new Message.MessageBuilder().setId(3L).setSource(u1).setConversation(conv1).setContent("Parfait et toi gros frère ?").setSentDateTime(LocalDateTime.now()).setUserOne(u1).setUserTwo(u2).setIsDeletedByUserOne(false).setIsDeletedByUserTwo(false).setType(MessageType.TEXT).createMessage();
        Message ms4 = new Message.MessageBuilder().setId(4L).setSource(u2).setConversation(conv1).setContent("Parfaitement oklm bro ").setSentDateTime(LocalDateTime.now()).setUserOne(u1).setUserTwo(u2).setIsDeletedByUserOne(false).setIsDeletedByUserTwo(false).setType(MessageType.TEXT).createMessage();
        Stream.of(ms1, ms2, ms3, ms4).forEach(ms -> messageRepository.save(ms));

    }
}
