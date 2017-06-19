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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Stream;

/**
 * Created by pgm on 05/06/17.
 */
@Component
@Profile("prod")
@Order(2)
public class ProdDatabaseCLR implements CommandLineRunner {

    @Value("${image.default.name}")
    private String defaultImage;

    @Value("${admin.password}")
    private String adminPassword;

    private final PasswordEncoder encoder;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;
    private final UserRoleRepository userRoleRepository;

    @Autowired
    public ProdDatabaseCLR(PasswordEncoder encoder, UserRepository userRepository, ImageRepository imageRepository, UserRoleRepository userRoleRepository) {
        this.encoder = encoder;
        this.userRepository = userRepository;
        this.imageRepository = imageRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    @Transactional
    public void run(String... strings) throws Exception {

        Collection<Fetish> fetishes = Arrays.asList(new Fetish.FetishBuilder().setId(1L).createFetish(), new Fetish.FetishBuilder().setId(1L).createFetish());
        Country c1 = Country.Builder.country().withId(1L).build();
        Area a1 = new Area.AreaBuilder().setId(1L).createArea();
        UserType ut2 = UserType.Builder.anUserType().withId(2L).build();

        if (userRepository.findByEmail("contact@dominapp.com") == null) {

            User u1 = User.Builder.builder()
                    .withEmail("contact@dominapp.com")
                    .withBirthDate(LocalDate.of(1990, 1, 1))
                    .withUsername("Jack")
                    .withPassword(encoder.encode(adminPassword))
                    .withLastPasswordResetDate(new Date(System.currentTimeMillis() - 100000000))
                    .withEnabled(true)
                    .withIsBlocked(false)
                    .withDescription("")
                    .withFetishes(fetishes)
                    .withArea(a1)
                    .withCountry(c1)
                    .withType(ut2)
                    .withReportedAsFake(0L)
                    .withLastActivityDatetime(LocalDateTime.now())
                    .withLastReportDate(LocalDate.now().minusDays(1L))
                    .withNotifyMessage(true)
                    .withNotifyVisit(true)
                    .withActivated(true)
                    .build();

            // save users
            Stream.of(u1).forEach(m -> userRepository.save(m));

            Image u1i1 = Image.Builder.anImage().withOrderNumber(1).withUrl(defaultImage).withUser(u1).build();
            Stream.of(u1i1).forEach(i -> imageRepository.save(i));

            UserRole us1 = new UserRole(u1, "ROLE_USER");
            UserRole us2 = new UserRole(u1, "ROLE_ADMIN");
            UserRole us3 = new UserRole(u1, "ROLE_PREMIUM");
            Stream.of(us1, us2, us3).forEach(us -> userRoleRepository.save(us));
        }
    }
}
