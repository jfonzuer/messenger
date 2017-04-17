package com.jfonzuer.runner;

import com.jfonzuer.entities.*;
import com.jfonzuer.repository.*;
import com.jfonzuer.utils.MessengerUtils;
import org.ektorp.CouchDbConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Stream;

/**
 * Created by pgm on 19/09/16.
 */
@Component
@Profile("!prod")
@Order(1)
public class DatabaseCLR implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseCLR.class);

    private final FetishRepository fetishRepository;
    private final UserTypeRepository userTypeRepository;
    private final CountryRepository countryRepository;
    private final AreaRepository areaRepository;

    @Value("${image.default.name}")
    private String defaultImage;

    @Autowired
    public DatabaseCLR(FetishRepository fetishRepository, UserTypeRepository userTypeRepository, CountryRepository countryRepository, AreaRepository areaRepository) {
        this.fetishRepository = fetishRepository;
        this.userTypeRepository = userTypeRepository;
        this.countryRepository = countryRepository;
        this.areaRepository = areaRepository;
    }

    @Override
    @Transactional
    public void run(String... strings) throws Exception {

        PasswordEncoder encoder = new BCryptPasswordEncoder();
        Fetish f1 = new Fetish.FetishBuilder().setName("Ballbusting").createFetish();
        Fetish f2 = new Fetish.FetishBuilder().setName("Bondage").createFetish();
        Fetish f3 = new Fetish.FetishBuilder().setName("Chasteté").createFetish();
        Fetish f4 = new Fetish.FetishBuilder().setName("Cuckolding").createFetish();
        Fetish f5 = new Fetish.FetishBuilder().setName("Face-sitting").createFetish();
        Fetish f6 = new Fetish.FetishBuilder().setName("Féminisation").createFetish();
        Fetish f7 = new Fetish.FetishBuilder().setName("Foot-worship").createFetish();
        Fetish f8 = new Fetish.FetishBuilder().setName("Gode-ceinture").createFetish();
        Fetish f9 = new Fetish.FetishBuilder().setName("Humiliation").createFetish();
        Fetish f10 = new Fetish.FetishBuilder().setName("Scato").createFetish();
        Fetish f11 = new Fetish.FetishBuilder().setName("Uro").createFetish();
        Fetish f12 = new Fetish.FetishBuilder().setName("Whipping & Caning").createFetish();
        List<Fetish> fetishes = Arrays.asList(f1, f2, f3, f4, f5, f6, f7, f8, f9, f10, f11, f12);

        // on insére les fetishes dans la bdd
        fetishes.stream().forEach(f -> fetishRepository.save(f));

        Country c1 = Country.Builder.country().withId(MessengerUtils.FRANCE_ID).withFlag("fr").withName("France").build();
        Country c2 = Country.Builder.country().withId(MessengerUtils.BELGIUM_ID).withFlag("be").withName("Belgique").build();
        Country c3 = Country.Builder.country().withId(MessengerUtils.LUX_ID).withFlag("lu").withName("Luxembourg").build();
        Country c4 = Country.Builder.country().withId(MessengerUtils.SWISS_ID).withFlag("ch").withName("Suisse").build();
        Arrays.asList(c1, c2, c3, c4).stream().forEach(countryRepository::save);

        // régions françaises
        Area a1 = new Area.AreaBuilder().setId(1L).setName("Alsace").setCountry(c1).createArea();
        Area a2 = new Area.AreaBuilder().setId(2L).setName("Aquitaine").setCountry(c1).createArea();
        Area a3 = new Area.AreaBuilder().setId(3L).setName("Auvergne").setCountry(c1).createArea();
        Area a4 = new Area.AreaBuilder().setId(4L).setName("Basse Normandie").setCountry(c1).createArea();
        Area a5 = new Area.AreaBuilder().setId(5L).setName("Bourgogne").setCountry(c1).createArea();
        Area a6 = new Area.AreaBuilder().setId(6L).setName("Bretagne").setCountry(c1).createArea();
        Area a7 = new Area.AreaBuilder().setId(7L).setName("Centre").setCountry(c1).createArea();
        Area a8 = new Area.AreaBuilder().setId(8L).setName("Champagne-Ardenne").setCountry(c1).createArea();
        Area a9 = new Area.AreaBuilder().setId(9L).setName("Corse").setCountry(c1).createArea();
        Area a10 = new Area.AreaBuilder().setId(10L).setName("Franche-Comté").setCountry(c1).createArea();
        Area a11 = new Area.AreaBuilder().setId(11L).setName("Haute-Normandie").setCountry(c1).createArea();
        Area a12 = new Area.AreaBuilder().setId(12L).setName("Ile-de-France").setCountry(c1).createArea();
        Area a13 = new Area.AreaBuilder().setId(13L).setName("Languedoc-Roussilon").setCountry(c1).createArea();
        Area a14 = new Area.AreaBuilder().setId(14L).setName("Limousin").setCountry(c1).createArea();
        Area a15 = new Area.AreaBuilder().setId(15L).setName("Lorraine").setCountry(c1).createArea();
        Area a16 = new Area.AreaBuilder().setId(16L).setName("Midi-Pyrénées").setCountry(c1).createArea();
        Area a17 = new Area.AreaBuilder().setId(17L).setName("Nord-Pas-de-Calais").setCountry(c1).createArea();
        Area a18 = new Area.AreaBuilder().setId(18L).setName("Pays de la Loire").setCountry(c1).createArea();
        Area a19 = new Area.AreaBuilder().setId(19L).setName("Picardie").setCountry(c1).createArea();
        Area a20 = new Area.AreaBuilder().setId(20L).setName("Poitou-Charentes").setCountry(c1).createArea();
        Area a21 = new Area.AreaBuilder().setId(21L).setName("Provence-Alpes-Côtes-d'Azur").setCountry(c1).createArea();
        Area a22 = new Area.AreaBuilder().setId(22L).setName("Guadeloupe").setCountry(c1).createArea();
        Area a23 = new Area.AreaBuilder().setId(23L).setName("Martinique").setCountry(c1).createArea();
        Area a24 = new Area.AreaBuilder().setId(24L).setName("Guyane").setCountry(c1).createArea();
        Area a25 = new Area.AreaBuilder().setId(25L).setName("La Réunion").setCountry(c1).createArea();
        Area a26 = new Area.AreaBuilder().setId(26L).setName("Mayotte").setCountry(c1).createArea();

        // régions belges
        Area a27 = new Area.AreaBuilder().setName("Anvers").setCountry(c2).createArea();
        Area a28 = new Area.AreaBuilder().setName("Brabant").setCountry(c2).createArea();
        Area a29 = new Area.AreaBuilder().setName("Flamande").setCountry(c2).createArea();
        Area a30 = new Area.AreaBuilder().setName("Flandre Occidentale").setCountry(c2).createArea();
        Area a31 = new Area.AreaBuilder().setName("Flandre Orientale").setCountry(c2).createArea();
        Area a32 = new Area.AreaBuilder().setName("Hainaut").setCountry(c2).createArea();
        Area a33 = new Area.AreaBuilder().setName("Liège").setCountry(c2).createArea();
        Area a34 = new Area.AreaBuilder().setName("Limbourg").setCountry(c2).createArea();
        Area a35 = new Area.AreaBuilder().setName("Luxembourg").setCountry(c2).createArea();
        Area a36 = new Area.AreaBuilder().setName("Namur").setCountry(c2).createArea();

        // regions luxembourgeoises
        Area a37 = new Area.AreaBuilder().setName("Guttland").setCountry(c3).createArea();
        Area a38 = new Area.AreaBuilder().setName("Oesling").setCountry(c3).createArea();

        // regions suisses
        Area a39 = new Area.AreaBuilder().setName("Appenzell").setCountry(c4).createArea();
        Area a40 = new Area.AreaBuilder().setName("Argovie").setCountry(c4).createArea();
        Area a41 = new Area.AreaBuilder().setName("Bâle").setCountry(c4).createArea();
        Area a42 = new Area.AreaBuilder().setName("Berne").setCountry(c4).createArea();
        Area a43 = new Area.AreaBuilder().setName("Fribourg").setCountry(c4).createArea();
        Area a44 = new Area.AreaBuilder().setName("Genève").setCountry(c4).createArea();
        Area a45 = new Area.AreaBuilder().setName("Glaris").setCountry(c4).createArea();
        Area a46 = new Area.AreaBuilder().setName("Grison").setCountry(c4).createArea();
        Area a47 = new Area.AreaBuilder().setName("Jura").setCountry(c4).createArea();
        Area a48 = new Area.AreaBuilder().setName("Lucerne").setCountry(c4).createArea();
        Area a49 = new Area.AreaBuilder().setName("Neufchâtel").setCountry(c4).createArea();
        Area a50 = new Area.AreaBuilder().setName("Nidwald").setCountry(c4).createArea();
        Area a51 = new Area.AreaBuilder().setName("Obwald").setCountry(c4).createArea();
        Area a52 = new Area.AreaBuilder().setName("Saint-Gall").setCountry(c4).createArea();
        Area a53 = new Area.AreaBuilder().setName("Schaffhouse").setCountry(c4).createArea();
        Area a54 = new Area.AreaBuilder().setName("Schwytz").setCountry(c4).createArea();
        Area a55 = new Area.AreaBuilder().setName("Soleure").setCountry(c4).createArea();
        Area a56 = new Area.AreaBuilder().setName("Tessin").setCountry(c4).createArea();
        Area a57 = new Area.AreaBuilder().setName("Thurgovie").setCountry(c4).createArea();
        Area a58 = new Area.AreaBuilder().setName("Uri").setCountry(c4).createArea();
        Area a59 = new Area.AreaBuilder().setName("Valais").setCountry(c4).createArea();
        Area a60 = new Area.AreaBuilder().setName("Vaud").setCountry(c4).createArea();
        Area a61 = new Area.AreaBuilder().setName("Zoug").setCountry(c4).createArea();
        Area a62 = new Area.AreaBuilder().setName("Zurich").setCountry(c4).createArea();

        List<Area> areas = Arrays.asList(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, a20, a21, a22, a23, a24, a25, a26, a27, a28, a29, a30, a31, a32, a33, a34, a35, a36, a37, a38, a39, a40, a41, a42, a43, a44, a45, a46, a47, a48, a49, a50, a51, a52, a53, a54, a55, a56, a57, a58, a59, a60, a61, a62);
        areas.stream().forEach(areaRepository::save);

        UserType ut1 = UserType.Builder.anUserType().withId(MessengerUtils.DOMINA_ID).withName("Dominatrice").build();
        UserType ut2 = UserType.Builder.anUserType().withId(MessengerUtils.SUBMISSIVE_ID).withName("Soumis").build();
        Stream.of(ut1, ut2).forEach(userTypeRepository::save);
    }
}
