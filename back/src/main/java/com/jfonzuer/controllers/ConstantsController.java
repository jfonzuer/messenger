package com.jfonzuer.controllers;

import com.jfonzuer.dto.AreaDto;
import com.jfonzuer.dto.mapper.AreaMapper;
import com.jfonzuer.dto.mapper.FetishMapper;
import com.jfonzuer.dto.mapper.UserTypeMapper;
import com.jfonzuer.dto.response.ConstantsResponse;
import com.jfonzuer.entities.Country;
import com.jfonzuer.repository.AreaRepository;
import com.jfonzuer.repository.CountryRepository;
import com.jfonzuer.repository.FetishRepository;
import com.jfonzuer.repository.UserTypeRepository;
import com.jfonzuer.utils.MessengerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

/**
 * Created by pgm on 21/02/17.
 */
@RestController
@RequestMapping("/constants")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ConstantsController {

    private final FetishRepository fetishRepository;
    private final UserTypeRepository userTypeRepository;
    private final CountryRepository countryRepository;
    private final AreaRepository areaRepository;

    private Country france = new Country.CountryBuilder().setId(MessengerUtils.FRANCE_ID).createCountry();
    private Country belgium = new Country.CountryBuilder().setId(MessengerUtils.BELGIUM_ID).createCountry();
    private Country lux = new Country.CountryBuilder().setId(MessengerUtils.LUX_ID).createCountry();
    private Country swiss = new Country.CountryBuilder().setId(MessengerUtils.SWISS_ID).createCountry();


    @Autowired
    public ConstantsController(FetishRepository fetishRepository, UserTypeRepository userTypeRepository, CountryRepository countryRepository, AreaRepository areaRepository) {
        this.fetishRepository = fetishRepository;
        this.userTypeRepository = userTypeRepository;
        this.countryRepository = countryRepository;
        this.areaRepository = areaRepository;
    }

    @GetMapping
    public ConstantsResponse getConstants() {
        ConstantsResponse constantsResponse = new ConstantsResponse();
        constantsResponse.setUserTypes(userTypeRepository.findAll().stream().map(UserTypeMapper::toDto).collect(Collectors.toList()));
        constantsResponse.setFetishes(fetishRepository.findAll().stream().map(FetishMapper::toDto).collect(Collectors.toList()));
        constantsResponse.setFranceAreas(areaRepository.findByCountry(france).stream().map(AreaMapper::toDto).collect(Collectors.toList()));
        constantsResponse.setBelgiumAreas(areaRepository.findByCountry(belgium).stream().map(AreaMapper::toDto).collect(Collectors.toList()));
        constantsResponse.setLuxembourgAreas(areaRepository.findByCountry(lux).stream().map(AreaMapper::toDto).collect(Collectors.toList()));
        constantsResponse.setSwissAreas(areaRepository.findByCountry(swiss).stream().map(AreaMapper::toDto).collect(Collectors.toList()));
        return constantsResponse;
    }
}
