package com.jfonzuer.service;

import com.jfonzuer.dto.*;
import com.jfonzuer.dto.mapper.SearchMapper;
import com.jfonzuer.dto.mapper.UserMapper;
import com.jfonzuer.entities.*;
import com.jfonzuer.repository.*;
import com.jfonzuer.security.JwtTokenUtil;
import com.jfonzuer.utils.MessengerUtils;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Created by pgm on 17/10/16.
 */
@Service
public class UserService {

    @Value("${jwt.header}")
    private String tokenHeader;

    private final JwtTokenUtil jwtTokenUtil;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final ImageRepository imageRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    private final MailService mailService;
    private final VisitRepository visitRepository;

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

    /**
     * Méthode permettant de mettre à jour le password de l'utilisateur
     * @param request
     * @param dto
     */
    public void updatePassword(HttpServletRequest request, PasswordDto dto) {
        if (!dto.getPassword().equals(dto.getConfirmation())) {
            throw new IllegalArgumentException();
        }
        User user = getUserFromToken(request);
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
        return this.userRepository.findByEmail(username);
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
        Page<User> users = userRepository.search(user.getId(), search.getUserType(), search.getCountry(), search.getArea(), search.getKeyword(), search.getBirthDateOne(), search.getBirthDateTwo(), p);
        return users.map(UserMapper::toDto);
    }

    /**
     * Méthode permettant de créer un utilisateur et de set les champs par défaults
     * @param register
     */
    public void createUser(RegisterDto register) {
        User user = UserMapper.fromDto(register.getUser());
        user.setPassword(passwordEncoder.encode(register.getPassword()));
        user.setEnabled(true);
        user.setBlocked(false);
        user.setLastPasswordResetDate(new Date());
        user.setReportedAsFake(0L);
        user.setLastActivityDate(LocalDate.now());
        user.setLastReportDate(LocalDate.now().minusDays(1));
        user.setNotifyVisit(true);
        user.setNotifyMessage(true);
        user = userRepository.save(user);
        userRoleRepository.save(new UserRole(user, "ROLE_USER"));

        // creation d'une image par défaut
        Stream.of(new Image.ImageBuilder().setOrderNumber(1).setUrl("profile.png").setUser(user).createImage()).forEach(i -> imageRepository.save(i));
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
            mailService.sendAsync(() -> mailService.sendVisitNotification(request.getLocale(), visited, visitor));
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
    public UserDto updateInformations(User user, UserDto dto) {
        User updatedUser = UserMapper.fromDto(dto);
        user.setUsername(updatedUser.getUsername());
        user.setEmail(updatedUser.getEmail());
        user.setBirthDate(updatedUser.getBirthDate());
        return UserMapper.toDto(userRepository.save(user));
    }


    /**
     * méthode utilisée lorsque l'utilisateur a oublié son mdp et le reset
     * @param resetPasswordDto
     */
    public void resetPassword(ResetPasswordDto resetPasswordDto) {
        User user = userRepository.findOne(resetPasswordDto.getUserId());
        System.out.println("userId = " + user.getId());
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


    public void throwExceptionIfBlocked(User sender, User target) {

        if (target.getBlockedUsers() != null /* && target.getBlockedUsers().contains(sender) */) {
            long nb = target.getBlockedUsers().stream().filter(u -> u.equals(sender)).count();
            System.err.println("err = " + nb);
            if (nb == 1l) {
                throw new IllegalArgumentException("Vous ne pouvez pas envoyer de message à cet utilisateur");
            }
        }
    }
}
