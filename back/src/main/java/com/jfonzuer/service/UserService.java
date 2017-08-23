package com.jfonzuer.service;

import com.jfonzuer.dto.PasswordDto;
import com.jfonzuer.dto.ResetPasswordDto;
import com.jfonzuer.dto.SearchDto;
import com.jfonzuer.dto.UserDto;
import com.jfonzuer.dto.mapper.SearchMapper;
import com.jfonzuer.dto.mapper.UserMapper;
import com.jfonzuer.dto.response.InformationUpdateDto;
import com.jfonzuer.entities.*;
import com.jfonzuer.exception.UnauthorizedException;
import com.jfonzuer.repository.*;
import com.jfonzuer.security.JwtTokenUtil;
import com.jfonzuer.utils.MessengerUtils;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mobile.device.Device;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by pgm on 17/10/16.
 */
@Service
public class UserService {

    private final static Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Value("${jwt.header}")
    private String tokenHeader;

    @Value("${image.default.name}")
    private String defaultImage;

    private final JwtTokenUtil jwtTokenUtil;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final ImageRepository imageRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    private final MailService mailService;
    private final VisitRepository visitRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    public UserService(JwtTokenUtil jwtTokenUtil, UserRepository userRepository, UserRoleRepository userRoleRepository, ImageRepository imageRepository, PasswordEncoder passwordEncoder, TokenRepository tokenRepository, MailService mailService, VisitRepository visitRepository) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.imageRepository = imageRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenRepository = tokenRepository;
        this.mailService = mailService;
        this.visitRepository = visitRepository;
    }

    public void updateLastActivityDate(User user) {
        // si la derinère action de l'utilisateur remonte à plus de 30min on met à sa last activity date
        if (ChronoUnit.MINUTES.between(LocalDateTime.now(), user.getLastActivityDatetime()) > 30) {
            user.setLastActivityDatetime(LocalDateTime.now());
            userRepository.save(user);
        }
    }

    /**
     * Méthode permettant de mettre à jour le password de l'utilisateur
     * @param request
     * @param dto
     */
    public void updatePassword(HttpServletRequest request, PasswordDto dto) {
        User user = getUserFromToken(request);

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), dto.getCurrent()));
        } catch (BadCredentialsException e) {
            LOGGER.error("Error when trying to authenticate user", e);
            throw new IllegalArgumentException("Votre mot de passe actuel est incorrect");
        }

        if (!dto.getPassword().equals(dto.getConfirmation())) {
            throw new IllegalArgumentException("Les mots de passe ne correspondent pas.");
        }
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        userRepository.save(user);
    }

    /**
     * Méthode permettant de retourner l'utilisateur à partir du token dans le header
     * @param request
     * @return
     */
    public User getUserFromToken(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user = findByEmailOrThrowException(username);
        updateLastActivityDate(user);
        return user;
    }

    /**
     * Méthode permettant de bloquer un utilisateur
     * @param currentUser
     * @param userToBlock
     * @return
     */
    public Set<User> blockUser(User currentUser, User userToBlock) {
        Set<User> blockedUsers = currentUser.getBlockedUsers() == null ? new HashSet<>() : currentUser.getBlockedUsers();
        blockedUsers.add(userToBlock);
        currentUser.setBlockedUsers(blockedUsers);
        userRepository.save(currentUser);
        return blockedUsers;
    }

    /**
     * Méthode permettant de débloquer un utilisateur
     * @param currentUser
     * @param userToUnblock
     * @return
     */
    public Set<User> unblockUser(User currentUser, User userToUnblock) {
        Set<User> blockedUsers = currentUser.getBlockedUsers() == null ? new HashSet<>() : currentUser.getBlockedUsers();
        blockedUsers.remove(userToUnblock);
        currentUser.setBlockedUsers(blockedUsers);
        userRepository.save(currentUser);
        return blockedUsers;
    }

    /**
     * Méthode permettant de récupérer les utilisateurs par ordre décroissant d'inscription
     * @param request
     * @return
     */
    public Page<UserDto> getLastRegisteredUsers(HttpServletRequest request, Pageable p) {
        User user = userRepository.findByEmail(jwtTokenUtil.getUsernameFromToken(request.getHeader(tokenHeader)));
        UserType otherType = MessengerUtils.getOtherType(user);
        return  userRepository.findByIdNotAndTypeAndEnabledAndIsBlockedOrderByIdDesc(user.getId(), otherType, true, false, p).map(UserMapper::toLightDto);
    }

    /**
     * Méthode permettant de rechercher des utilisateurs selon certains critères
     * @param request
     * @param searchDto
     * @param p
     * @return
     */
    public Page<UserDto> search(HttpServletRequest request, SearchDto searchDto, Pageable p) {
        User user = userRepository.findByEmail(jwtTokenUtil.getUsernameFromToken(request.getHeader(tokenHeader)));
        Search search = SearchMapper.fromDto(searchDto);
        LOGGER.debug("search users with search params {}", search);
        Page<User> users = userRepository.search(user.getId(), search.getUserType(), search.getCountry(), search.getArea(), search.getKeyword(), search.getBirthDateOne(), search.getBirthDateTwo(),
                search.getHeightOne(), search.getHeightTwo(), search.getWeightOne(), search.getWeightTwo(), p);
        return users.map(UserMapper::toDto);
    }

    /**
     * Méthode permettant de créer un utilisateur et de set les champs par défaults
     * @param user
     */
    public void createUser(User user, String password) {
        throwExceptionIfUnderaged(user.getBirthDate());

        user.setUsername(user.getUsername().trim());
        user.setPassword(passwordEncoder.encode(password));
        user.setEnabled(true);
        user.setBlocked(false);
        user.setLastPasswordResetDate(new Date());
        user.setReportedAsFake(0L);
        user.setLastActivityDatetime(LocalDateTime.now());
        user.setLastReportDate(LocalDate.now().minusDays(1));
        user.setActivated(false);
        user.setNotifyVisit(true);
        user.setNotifyMessage(true);
        user.setNotifyOther(true);
        user = userRepository.save(user);

        // if user is domina
        // TODO when payment is active uncomment
        //if (MessengerUtils.isDomina(user)) {
            userRoleRepository.save(new UserRole(user, "ROLE_PREMIUM"));
        //}

        userRoleRepository.save(new UserRole(user, "ROLE_USER"));

        // creation d'une image par défaut
        imageRepository.save(Image.Builder.anImage().withOrderNumber(1).withUrl(defaultImage).withUser(user).build());
    }

    /**
     * Méthode pour renvoyer le profil demandé, mettre à jour les visites et envoyer un mail
     * @param request
     * @param visitedId
     * @return
     */
    public UserDto getUserAndUpdateVisit(HttpServletRequest request, Long visitedId) {
        User visitor = getUserFromToken(request);
        User visited = userRepository.findByIdAndEnabledAndIsBlocked(visitedId, true, false);

        if (!visited.equals(visitor) && !visitor.equals(visited.getLastVisitedBy())) {
            visitRepository.save(new Visit.VisitBuilder().setVisited(visited).setIsSeenByVisited(false).setVisitor(visitor).setVisitedDate(LocalDate.now()).createVisit());
            visited.setLastVisitedBy(visitor);
            userRepository.save(visited);
            mailService.sendVisitNotification(request.getLocale(), visited, visitor);
        }
        return UserMapper.toDto(visited);
    }

    /**
     * Méthode permettant de mettre à jour le profil d'un utilisateur
     * @param user
     * @param dto
     * @return
     */
    public UserDto updateProfile(User user, UserDto dto) {
        User updatedUser = UserMapper.fromDto(dto);
        user.setDescription(updatedUser.getDescription());
        user.setHeight(dto.getHeight());
        user.setWeight(dto.getWeight());
        user.setSearch(dto.getSearch());
        user.setArea(updatedUser.getArea());
        user.setCountry(updatedUser.getCountry());
        user.setFetishes(updatedUser.getFetishes());
        return UserMapper.toDto(userRepository.save(user));
    }

    /**
     * Méthode permettant à l'utilisateur de mettre à jour ses informations
     * @param user
     * @param dto
     * @return
     */
    public InformationUpdateDto updateInformations(User user, UserDto dto, Device device) {
        User updatedUser = UserMapper.fromDto(dto);
        String token = null;

        // si l'utilisateur a changé d'email, on génére un nouveau token
        if (!updatedUser.getEmail().equals(user.getEmail())) {
            token = jwtTokenUtil.generateToken(dto, device);
        }
        user.setUsername(updatedUser.getUsername());
        user.setEmail(updatedUser.getEmail());
        throwExceptionIfUnderaged(user.getBirthDate());
        user.setBirthDate(updatedUser.getBirthDate());

        user = userRepository.save(user);

        return InformationUpdateDto.Builder.anInformationUpdateDto().withUser(UserMapper.toDto(user)).withToken(token).build();
    }

    /**
     * méthode utilisée lorsque l'utilisateur a oublié son mdp et le reset
     * @param resetPasswordDto
     */
    public void resetPassword(ResetPasswordDto resetPasswordDto) {
        User user = userRepository.findOne(resetPasswordDto.getUserId());
        LOGGER.debug("Reset password for user id : {}", user.getId());
        Token token = tokenRepository.getByTokenAndUser(resetPasswordDto.getToken(), user);

        if (token == null) {
            throw new IllegalArgumentException("Aucun token ne correspond aux paramètres");
        }

        if (!resetPasswordDto.getPasswordConfirmation().getPassword().equals(resetPasswordDto.getPasswordConfirmation().getConfirmation())) {
            throw new IllegalArgumentException("Les mots de passes doivent être les mêmes.");
        }
        if (!token.getExpiryDate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Le token a expiré");
        }
        user.setPassword(passwordEncoder.encode(resetPasswordDto.getPasswordConfirmation().getPassword()));
        userRepository.save(user);
    }

    public void updateLastMessageBy(User target, User sender) {
        target.setLastMessageBy(sender);
        userRepository.save(target);
    }


    private void throwExceptionIfUnderaged(LocalDate birthdate) {
        if (ChronoUnit.YEARS.between(birthdate, LocalDate.now()) < 18) {
            throw new IllegalArgumentException("Vous devez avoir plus de 18 ans pour vous inscrire sur ce site");
        }
    }

    public void throwExceptionIfDesactivated(User user) {
        if (!user.getEnabled()) {
            LOGGER.debug("user with id {} is desactivated", user.getId());
            throw new UnauthorizedException("Votre compte a été désactivé. Contactez nous à l'adresse suivante : contact@dominapp.com");
        }
    }

    public void throwExceptionIfBlocked(User sender, User target) {

        if (target.getBlockedUsers() != null && target.getBlockedUsers().contains(sender)) {
            //long nb = target.getBlockedUsers().stream().filter(u -> u.equals(sender)).count();
            //LOGGER.debug("nb of blocked user = {}", nb);
            //if (nb == 1l) {
                throw new IllegalArgumentException("Vous ne pouvez pas envoyer de message à cet utilisateur");
            //}
        }
    }

    public void throwExceptionIfNull(User user) {
        if (user == null) {
            throw new UsernameNotFoundException("L'utilisateur n'a pas pu être trouvé");
        }
    }

    public User findByEmailOrThrowException(String email) {
        User user = userRepository.findByEmail(email);
        this.throwExceptionIfNull(user);
        return user;
    }

    public User findByIdOrThrowException(Long id) {
        User user = userRepository.findOne(id);
        this.throwExceptionIfNull(user);
        return user;
    }
}
