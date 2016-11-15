package com.jfonzuer.runner;

import com.jfonzuer.entities.*;
import com.jfonzuer.repository.*;
import com.jfonzuer.utils.MessengerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDate;
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
    private final FetishRepository fetishRepository;
    private final LocalizationRepository localizationRepository;
    private final VisitRepository visitRepository;
    private final ImageRepository imageRepository;
    private final UserTypeRepository userTypeRepository;

    @Autowired
    public MessengerCLR(UserRepository userRepository, ConversationRepository conversationRepository, MessageRepository messageRepository, UserRoleRepository userRoleRepository, FetishRepository fetishRepository, LocalizationRepository localizationRepository, VisitRepository visitRepository, ImageRepository imageRepository, UserTypeRepository userTypeRepository) {
        this.userRepository = userRepository;
        this.conversationRepository = conversationRepository;
        this.messageRepository = messageRepository;
        this.userRoleRepository = userRoleRepository;
        this.fetishRepository = fetishRepository;
        this.localizationRepository = localizationRepository;
        this.visitRepository = visitRepository;
        this.imageRepository = imageRepository;
        this.userTypeRepository = userTypeRepository;
    }

    @Override
    @Transactional
    public void run(String... strings) throws Exception {

        PasswordEncoder encoder = new BCryptPasswordEncoder();

        Fetish f1 = new Fetish.FetishBuilder().setId(1L).setName("fetish1").createFetish();
        Fetish f2 = new Fetish.FetishBuilder().setId(2L).setName("fetish2").createFetish();
        Fetish f3 = new Fetish.FetishBuilder().setId(3L).setName("fetish3").createFetish();
        Fetish f4 = new Fetish.FetishBuilder().setId(4L).setName("fetish4").createFetish();

        List<Fetish> fetishes = Arrays.asList(f1, f2, f3, f4);

        // on insére les fetishes dans la bdd
        fetishes.stream().forEach(f -> fetishRepository.save(f));

        Localization l1 = new Localization.LocalizationBuilder().setId(1L).setName("Alsace").createLocalization();
        Localization l2 = new Localization.LocalizationBuilder().setId(2L).setName("Aquitaine").createLocalization();
        Localization l3 = new Localization.LocalizationBuilder().setId(3L).setName("Auvergne").createLocalization();
        Localization l4 = new Localization.LocalizationBuilder().setId(4L).setName("Basse Normandie").createLocalization();
        Localization l5 = new Localization.LocalizationBuilder().setId(5L).setName("Bourgogne").createLocalization();
        Localization l6 = new Localization.LocalizationBuilder().setId(6L).setName("Bretagne").createLocalization();
        Localization l7 = new Localization.LocalizationBuilder().setId(7L).setName("Centre").createLocalization();
        Localization l8 = new Localization.LocalizationBuilder().setId(8L).setName("Champagne-Ardenne").createLocalization();
        Localization l9 = new Localization.LocalizationBuilder().setId(9L).setName("Corse").createLocalization();
        Localization l10 = new Localization.LocalizationBuilder().setId(10L).setName("Franche-Comté").createLocalization();
        Localization l11 = new Localization.LocalizationBuilder().setId(11L).setName("Haute-Normandie").createLocalization();
        Localization l12 = new Localization.LocalizationBuilder().setId(12L).setName("Ile-de-France").createLocalization();
        Localization l13 = new Localization.LocalizationBuilder().setId(13L).setName("Languedoc-Roussilon").createLocalization();
        Localization l14 = new Localization.LocalizationBuilder().setId(14L).setName("Limousin").createLocalization();
        Localization l15 = new Localization.LocalizationBuilder().setId(15L).setName("Lorraine").createLocalization();
        Localization l16 = new Localization.LocalizationBuilder().setId(16L).setName("Midi-Pyrénées").createLocalization();
        Localization l17 = new Localization.LocalizationBuilder().setId(17L).setName("Nord-Pas-de-Calais").createLocalization();
        Localization l18 = new Localization.LocalizationBuilder().setId(18L).setName("Pays de la Loire").createLocalization();
        Localization l19 = new Localization.LocalizationBuilder().setId(19L).setName("Picardie").createLocalization();
        Localization l20 = new Localization.LocalizationBuilder().setId(20L).setName("Poitou-Charentes").createLocalization();
        Localization l21 = new Localization.LocalizationBuilder().setId(21L).setName("Provence-Alpes-Côtes-d'Azur").createLocalization();
        Localization l22 = new Localization.LocalizationBuilder().setId(22L).setName("Guadeloupe").createLocalization();
        Localization l23 = new Localization.LocalizationBuilder().setId(23L).setName("Martinique").createLocalization();
        Localization l24 = new Localization.LocalizationBuilder().setId(24L).setName("Guyane").createLocalization();
        Localization l25 = new Localization.LocalizationBuilder().setId(25L).setName("La Réunion").createLocalization();
        Localization l26 = new Localization.LocalizationBuilder().setId(26L).setName("Mayotte").createLocalization();

        List<Localization> localizations = Arrays.asList(l1, l2, l3, l4, l5, l6, l7, l8, l9, l10, l11, l12, l13, l14, l15, l16, l17, l18, l19, l20, l21, l22, l23, l24, l25, l26);
        localizations.stream().forEach(l -> localizationRepository.save(l));


        UserType ut1 = new UserType.UserTypeBuilder().setId(MessengerUtils.DOMINA_ID).setLabel("Dominatrice").createUserType();
        UserType ut2 = new UserType.UserTypeBuilder().setId(MessengerUtils.SUBMISSIVE_ID).setLabel("Soumis").createUserType();
        Stream.of(ut1, ut2).forEach(userTypeRepository::save);

        User u1 = new User.UserBuilder()
                .setEmail("pgiraultmatz@gmail.com")
                .setBirthDate(LocalDate.of(1992, 3, 29))
                .setUsername("pgm")
                .setPassword(encoder.encode("test"))
                .setLastPasswordResetDate(new Date(System.currentTimeMillis() - 100000000))
                .setEnabled(true)
                .setDescription("je suis le membre 1")
                .setFetishes(fetishes)
                .setLocalization(l1)
                .setUserType(ut2)
                .createUser();
        User u2 = new User.UserBuilder()
                .setEmail("member13@gmail.com")
                .setUsername("member2")
                .setPassword(encoder.encode("password2"))
                .setLastPasswordResetDate(new Date(System.currentTimeMillis() - 100000000))
                .setEnabled(true)
                .setDescription("je suis le membre 2")
                .setBirthDate(LocalDate.of(1988, 3, 29))
                .setLocalization(l2)
                .setUserType(ut1)
                .createUser();
        User u3 = new User.UserBuilder()
                .setEmail("member3@gmail.com")
                .setUsername("member3")
                .setLastPasswordResetDate(new Date(System.currentTimeMillis() - 100000000))
                .setPassword(encoder.encode("password3"))
                .setEnabled(true)
                .setDescription("je suis le membre 3")
                .setBirthDate(LocalDate.of(1988, 3, 29))
                .setLocalization(l2)
                .setUserType(ut1)
                .createUser();

        // save members
        Stream.of(u1, u2, u3).forEach( m -> userRepository.save(m));

        Image u1i1 = new Image.ImageBuilder().setOrderNumber(1).setUrl("profile.png").setUser(u1).createImage();

        Image u2i1 = new Image.ImageBuilder().setOrderNumber(1).setUrl("profile.png").setUser(u2).createImage();

        Image u3i1 = new Image.ImageBuilder().setOrderNumber(1).setUrl("profile.png").setUser(u3).createImage();
        Stream.of(u1i1, u2i1, u3i1).forEach( i -> imageRepository.save(i));

        UserRole us1 = new UserRole(u1, "ROLE_USER");
        UserRole us2 = new UserRole(u1, "ROLE_ADMIN");
        UserRole us3 = new UserRole(u2, "ROLE_USER");

        Stream.of(us1, us2, us3).forEach(us -> userRoleRepository.save(us));

        // visits
        Visit v1 = new Visit(LocalDate.now(), u2, u1, false);
        Visit v2 = new Visit(LocalDate.now(), u3, u1, false);
        Visit v3 = new Visit(LocalDate.now(), u2, u1, false);
        Stream.of(v1, v2, v3).forEach(v -> this.visitRepository.save(v));

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

    }
}
