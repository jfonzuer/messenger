package com.jfonzuer.service;

import com.chargebee.Environment;
import com.chargebee.Result;
import com.chargebee.exceptions.InvalidRequestException;
import com.chargebee.models.Subscription;
import com.jfonzuer.entities.User;
import com.jfonzuer.entities.UserRole;
import com.jfonzuer.repository.UserRepository;
import com.jfonzuer.repository.UserRoleRepository;
import com.jfonzuer.utils.MessengerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

/**
 * Created by pgm on 08/03/17.
 */
@Service
public class SubscriptionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubscriptionService.class);

    @Value("${payment.sitename}")
    private String paymentSitename;

    @Value("${payment.api.key}")
    private String apiKey;

    private final static String RESOURCE_NOT_FOUND = "resource_not_found";
    private final static Integer HOURS_INTERVAL = 12;

    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;

    @Autowired
    public SubscriptionService(UserRoleRepository userRoleRepository, UserRepository userRepository) {
        this.userRoleRepository = userRoleRepository;
        this.userRepository = userRepository;
    }

    @Async
    public void checkSubscription(User user) {

        // if user is submissive, has subscription and was last time checked more that 12 hours
        Optional<UserRole> optionalPremium = user.getUserRoles().stream().filter(us -> us.getRole().equals("ROLE_PREMIUM")).findFirst();
        Optional<UserRole> optionalAdmin = user.getUserRoles().stream().filter(us -> us.getRole().equals("ROLE_ADMIN")).findFirst();

        Timestamp lastCheck = user.getLastSubscriptionCheck();
        Timestamp TwelveHoursBeforeNow = Timestamp.from(Instant.now().minusSeconds(12*3600));

        if (!optionalAdmin.isPresent() && MessengerUtils.isSub(user) && user.getSubscriptionId() != null && lastCheck.before(TwelveHoursBeforeNow)) {
            Environment.configure(paymentSitename, apiKey);
            try {
                Result result = Subscription.retrieve(user.getSubscriptionId()).request();

                Subscription subscription = result.subscription();
                // on met le last subscription check
                user.setLastSubscriptionCheck(Timestamp.from(Instant.now()));

                // subscription has expired
                if (subscription.nextBillingAt() == null || subscription.nextBillingAt().before(Timestamp.from(Instant.now()))) {
                    //userRoleRepository.deleteByRoleAndUser("ROLE_PREMIUM", user);
                    //UserRole premium = UserRole.Builder.anUserRole().withUser(user).withRole("ROLE_PREMIUM").build();
                    if (optionalPremium.isPresent()) {
                        user.getUserRoles().remove(optionalPremium.get());
                        userRoleRepository.delete(optionalPremium.get());
                    }
                    user.setSubscriptionId(null);
                }
                userRepository.save(user);
            } catch (IOException e) {
                LOGGER.error("Error when requesting payment api ", e);
            } catch (InvalidRequestException e) {
                LOGGER.error("Error when requesting billing plan to payment api", e);
                if (RESOURCE_NOT_FOUND.equals(e.apiErrorCode)) {
                    throw new IllegalArgumentException("Votre abonnement est expiré ou inexistant");
                }
            }
        }
    }

    public boolean checkIfSubscriptionExists(String id) {
        boolean exists = true;
        Environment.configure(paymentSitename, apiKey);
        Subscription subscription;
        try {
            Result result = Subscription.retrieve(id).request();

        } catch (IOException e) {
            LOGGER.error("Error when requesting payment api ", e);
        } catch (InvalidRequestException e) {
            if (RESOURCE_NOT_FOUND.equals(e.apiErrorCode)) {
                exists = false;
            }
        }
        return exists;
    }
}
