package com.jfonzuer.controllers;

import com.jfonzuer.dto.LocalizationDto;
import com.jfonzuer.dto.mapper.LocalizationMapper;
import com.jfonzuer.repository.LocalizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by pgm on 20/10/16.
 */
@RestController
@RequestMapping("/localizations")
@CrossOrigin(origins = "*", maxAge = 3600)
//@PreAuthorize("hasRole('USER')")
public class LocalizationController {

    private final LocalizationRepository localizationRepository;

    @Autowired
    public LocalizationController(LocalizationRepository localizationRepository) {
        this.localizationRepository = localizationRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<LocalizationDto> getAll() {
        return localizationRepository.findAll().stream().map(LocalizationMapper::toDto).collect(Collectors.toList());
    }
}
