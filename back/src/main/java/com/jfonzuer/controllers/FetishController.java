package com.jfonzuer.controllers;

import com.jfonzuer.dto.FetishDto;
import com.jfonzuer.dto.mapper.FetishMapper;
import com.jfonzuer.entities.Fetish;
import com.jfonzuer.repository.FetishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by pgm on 21/10/16.
 */
@RestController
@RequestMapping("/fetishes")
@CrossOrigin(origins = "*", maxAge = 3600)
//@PreAuthorize("hasRole('USER')")
public class FetishController {

    private final FetishRepository fetishRepository;

    @Autowired
    public FetishController(FetishRepository fetishRepository) {
        this.fetishRepository = fetishRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<FetishDto> getAll() {
        return fetishRepository.findAll().stream().map(FetishMapper::toDto).collect(Collectors.toList());
    }
}

