package com.jfonzuer.controllers;

import com.jfonzuer.dto.PasswordDto;
import com.jfonzuer.dto.ProfileDto;
import com.jfonzuer.dto.RegisterDto;
import com.jfonzuer.dto.mapper.UserMapper;
import com.jfonzuer.entities.User;
import com.jfonzuer.entities.UserType;
import com.jfonzuer.entities.Visit;
import com.jfonzuer.repository.UserRepository;
import com.jfonzuer.repository.VisitRepository;
import com.jfonzuer.security.JwtTokenUtil;
import com.jfonzuer.security.JwtUser;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
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
    private PasswordEncoder encoder = new BCryptPasswordEncoder();

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    public UserController(UserRepository userRepository, UserService userService, VisitRepository visitRepository) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.visitRepository = visitRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<JwtUser> getAll(HttpServletRequest request) {
        User user = this.userRepository.findByEmail(jwtTokenUtil.getUsernameFromToken(request.getHeader(tokenHeader)));
        System.out.println("current user type = " + user.getType());
        UserType otherType = MessengerUtils.getOtherType(user);
        System.out.println("other user type = " + user.getType());

        List<JwtUser> users =  userRepository.findTop20ByTypeOrderByIdDesc(otherType).stream().map(UserMapper::toLightDto).collect(Collectors.toList());
        System.out.println("users = " + users);
        System.out.println("user type ------------ " + users.get(0).getUserType());
        return users;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public JwtUser getById(@PathVariable Long id) {

        //TODO : set current user manually
        User visitor = userRepository.findOne(1L);
        User visited = userRepository.findOne(id);
        if (!visited.equals(visitor)) {
            visitRepository.save(new Visit.VisitBuilder().setVisited(visited).setIsSeenByVisited(false).setVisitor(visitor).setVisitedDate(LocalDate.now()).createVisit());
        }
        return UserMapper.toDto(userRepository.findOne(id));
    }

    @RequestMapping(value = "/profile", method = RequestMethod.PUT)
    public JwtUser updateProfile(HttpServletRequest request, @RequestBody JwtUser jwtUser) {
        //TODO : set current user manually
        User user = userRepository.findOne(1L);

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
        //TODO : set current user manually
        User user = userRepository.findOne(1L);
        User updatedUser = UserMapper.fromDto(jwtUser);

        //User user = userService.getUserFromToken(request);
        // TODO : validation via annotation and exception handling
        user.setUsername(updatedUser.getUsername());
        user.setEmail(updatedUser.getEmail());
        user.setBirthDate(updatedUser.getBirthDate());

        return UserMapper.toDto(userRepository.save(user));
    }


    @RequestMapping(value = "/password/reset", method = RequestMethod.PUT)
    public void resetPassword(@RequestBody PasswordDto passwordDto) {

        if (!passwordDto.getPassword().equals(passwordDto.getConfirmation())) {
            throw new IllegalArgumentException();
        }

        //TODO : set current user manually
        User user = userRepository.findOne(1L);
        user.setPassword(encoder.encode(passwordDto.getPassword()));
        userRepository.save(user);
    }
}
