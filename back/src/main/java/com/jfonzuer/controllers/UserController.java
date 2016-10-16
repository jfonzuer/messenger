package com.jfonzuer.controllers;

import com.jfonzuer.dto.mapper.UserMapper;
import com.jfonzuer.entities.User;
import com.jfonzuer.repository.UserRepository;
import com.jfonzuer.security.JwtUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Created by pgm on 19/09/16.
 */

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*", maxAge = 3600)
@PreAuthorize("hasRole('USER')")
public class UserController {

    private final UserRepository userRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(method = RequestMethod.POST)
    public JwtUser add(@RequestBody JwtUser JwtUser) {
        // TODO : validation via annotation and exception handling
        return saveOrUpdate(JwtUser);
    }


    @RequestMapping(method = RequestMethod.GET)
    public Page<JwtUser> getAll(Pageable p) {
        //return memberRepository.findAll(p).map(UserMapper::toDto);
        return userRepository.findAllByOrderByIdDesc(p).map(UserMapper::toDto);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public JwtUser getById(@PathVariable Long id) {
        return UserMapper.toDto(userRepository.findOne(id));
    }

    @RequestMapping(method = RequestMethod.PUT)
    public JwtUser update(@RequestBody JwtUser JwtUser) {
        // TODO : validation via annotation and exception handling
        return saveOrUpdate(JwtUser);
    }

    private JwtUser saveOrUpdate(JwtUser JwtUser) {
        User user = UserMapper.fromDto(JwtUser);
        userRepository.save(user);
        return UserMapper.toDto(user);
    }
}
