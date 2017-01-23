package com.jfonzuer.controllers;

import com.jfonzuer.dto.*;
import com.jfonzuer.dto.mapper.SearchMapper;
import com.jfonzuer.dto.mapper.UserMapper;
import com.jfonzuer.entities.Search;
import com.jfonzuer.entities.User;
import com.jfonzuer.entities.UserType;
import com.jfonzuer.entities.Visit;
import com.jfonzuer.repository.UserRepository;
import com.jfonzuer.repository.VisitRepository;
import com.jfonzuer.security.JwtTokenUtil;
import com.jfonzuer.security.JwtUser;
import com.jfonzuer.service.MailService;
import com.jfonzuer.service.UserService;
import com.jfonzuer.utils.MessengerUtils;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * Created by pgm on 19/09/16.
 */

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*", maxAge = 3600)
//@PreAuthorize("hasRole('USER')")
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final VisitRepository visitRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private final MailService mailService;
    private PasswordEncoder encoder = new BCryptPasswordEncoder();

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    public UserController(UserRepository userRepository, UserService userService, VisitRepository visitRepository, MailService mailService) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.visitRepository = visitRepository;
        this.mailService = mailService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Page<JwtUser> getAll(Pageable p, HttpServletRequest request) {
        User user = userRepository.findByEmail(jwtTokenUtil.getUsernameFromToken(request.getHeader(tokenHeader)));
        System.out.println("user.getType() = " + user.getType());
        UserType otherType = MessengerUtils.getOtherType(user);
        System.out.println("otherType = " + otherType);
        return  userRepository.findAllByIdNotAndTypeAndEnabledAndIsBlockedOrderByIdDesc(user.getId(), otherType, true, false, p).map(UserMapper::toLightDto);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Page<JwtUser> searchUsers(@RequestBody SearchDto searchDto, Pageable p, HttpServletRequest request) {
        User user = userRepository.findByEmail(jwtTokenUtil.getUsernameFromToken(request.getHeader(tokenHeader)));
        System.out.println("searchDto.getUserType() = " + searchDto.getUserType());

        Search search = SearchMapper.fromDto(searchDto);

        System.out.println("-----------------------------------");
        System.out.println("search = " + search);
        System.out.println("search.getUserType() = " + search.getUserType());

        Page<User> users = userRepository.search(user.getId(), search.getUserType(), search.getLocalization(), search.getKeyword(), search.getBirthDateOne(), search.getBirthDateTwo(), p);
        return users.map(UserMapper::toDto);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public JwtUser getById(HttpServletRequest request, @PathVariable Long id) {
        User visitor = userService.getUserFromToken(request);
        User visited = userRepository.findByIdAndEnabledAndIsBlocked(id, true, false);

        if (!visited.equals(visitor) && !visitor.equals(visited.getLastVisitedBy())) {
            visitRepository.save(new Visit.VisitBuilder().setVisited(visited).setIsSeenByVisited(false).setVisitor(visitor).setVisitedDate(LocalDate.now()).createVisit());
            visited.setLastVisitedBy(visitor);
            userRepository.save(visited);
            mailService.sendAsync(() -> mailService.sendVisitNotification(request.getLocale(), visited, visitor));
        }
        return UserMapper.toDto(visited);
    }

    @RequestMapping(value = "/profile", method = RequestMethod.PUT)
    public JwtUser updateProfile(HttpServletRequest request, @RequestBody JwtUser jwtUser) {
        User user = userService.getUserFromToken(request);
        User updatedUser = UserMapper.fromDto(jwtUser);

        //User user = userService.getUserFromToken(request);
        // TODO : validation via annotation and exception handling

        user.setDescription(updatedUser.getDescription());
        user.setLocalization(updatedUser.getLocalization());
        user.setFetishes(updatedUser.getFetishes());

        return UserMapper.toDto(userRepository.save(user));
    }

    @RequestMapping(value = "/informations", method = RequestMethod.PUT)
    public JwtUser updateInformation(HttpServletRequest request, @RequestBody JwtUser jwtUser) {
        User user = userService.getUserFromToken(request);
        User updatedUser = UserMapper.fromDto(jwtUser);

        // TODO : validation via annotation and exception handling
        user.setUsername(updatedUser.getUsername());
        user.setEmail(updatedUser.getEmail());
        user.setBirthDate(updatedUser.getBirthDate());

        return UserMapper.toDto(userRepository.save(user));
    }


    @RequestMapping(value = "/password/reset", method = RequestMethod.PUT)
    public void resetPassword(HttpServletRequest request, @RequestBody PasswordDto passwordDto) {

        if (!passwordDto.getPassword().equals(passwordDto.getConfirmation())) {
            throw new IllegalArgumentException();
        }

        User user = userService.getUserFromToken(request);
        user.setPassword(encoder.encode(passwordDto.getPassword()));
        userRepository.save(user);
    }

    @RequestMapping(value = "/report/{id}", method = RequestMethod.GET)
    public ResponseDto reportUser(HttpServletRequest request, @PathVariable Long id) {
        User user = userService.getUserFromToken(request);
        User reportedUser = userRepository.findOne(id);

        if (reportedUser == null) {
            throw new UsernameNotFoundException("User not found");
        }
        // if user has reported another user in the last 24 hours
        if (!user.getLastReportDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Vous avez déjà signalé un utilisateur lors des 24 dernières heures");
        }
        
        // increment number of reports, last report date and save both users
        reportedUser.setReportedAsFake(reportedUser.getReportedAsFake() + 1);
        user.setLastReportDate(LocalDate.now());
        Arrays.asList(reportedUser, user).stream().forEach(u -> userRepository.save(u));

        return new ResponseDto("L utilisateur a été signalé");
    }

    @RequestMapping(value = "/alerts", method = RequestMethod.PUT)
    public ResponseDto alerts(HttpServletRequest request, @RequestBody AlertsDto alerts) {
        User user = userService.getUserFromToken(request);
        user.setNotifyMessage(alerts.isNotifyMessage());
        user.setNotifyVisit(alerts.isNotifyVisit());
        user = userRepository.save(user);
        return new ResponseDto("Vos préférences ont été mises à jour");
    }

    @RequestMapping(value = "/desactivate", method = RequestMethod.PUT)
    public ResponseDto desactivate(HttpServletRequest request, @RequestBody DesactivateDto desactivate) {
        User user = userService.getUserFromToken(request);
        user.setEnabled(desactivate.isDesactivate());
        user = userRepository.save(user);
        return new ResponseDto("Votre compte a été désactivé");
    }

    @RequestMapping(value = "/block", method = RequestMethod.POST)
    public List<JwtUser> block(HttpServletRequest request, @RequestBody JwtUser dto) {
        User user = userService.getUserFromToken(request);
        User userToBlock = UserMapper.fromDto(dto);
        return userService.blockUser(user, userToBlock).stream().map(UserMapper::toLightDto).collect(Collectors.toList());
    }

    @RequestMapping(value = "/unblock", method = RequestMethod.POST)
    public List<JwtUser> unblock(HttpServletRequest request, @RequestBody JwtUser dto) {
        User user = userService.getUserFromToken(request);
        User userToBlock = UserMapper.fromDto(dto);
        userService.unblockUser(user, userToBlock);
        return user.getBlockedUsers().stream().map(UserMapper::toLightDto).collect(Collectors.toList());
    }
}
