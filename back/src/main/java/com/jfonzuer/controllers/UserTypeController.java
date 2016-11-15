package com.jfonzuer.controllers;

import com.jfonzuer.dto.UserTypeDto;
import com.jfonzuer.dto.mapper.UserTypeMapper;
import com.jfonzuer.entities.UserType;
import com.jfonzuer.repository.UserTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by pgm on 14/11/16.
 */

@RestController
@RequestMapping("/user/types")
@CrossOrigin(origins = "*", maxAge = 3600)
//@PreAuthorize("hasRole('USER')")
public class UserTypeController {

    private final UserTypeRepository userTypeRepository;

    @Autowired
    public UserTypeController(UserTypeRepository userTypeRepository) {
        this.userTypeRepository = userTypeRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<UserTypeDto> getAll() {
        return userTypeRepository.findAll().stream().map(UserTypeMapper::toDto).collect(Collectors.toList());
    }
}
