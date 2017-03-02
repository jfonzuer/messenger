package com.jfonzuer.controllers;

import com.jfonzuer.dto.UserDto;
import com.jfonzuer.dto.mapper.UserMapper;
import com.jfonzuer.entities.UserType;
import com.jfonzuer.repository.UserRepository;
import com.jfonzuer.utils.MessengerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by pgm on 04/03/17.
 */

@RestController
@RequestMapping(value = "/unauth")
public class UnauthController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = "/dominas")
    public List<UserDto> getLastRegisteredDominas() {
        return userRepository.findTop20ByTypeOrderByIdDesc(UserType.Builder.anUserType().withId(MessengerUtils.DOMINA_ID).build()).stream().map(UserMapper::toDto).collect(Collectors.toList());
    }
}
