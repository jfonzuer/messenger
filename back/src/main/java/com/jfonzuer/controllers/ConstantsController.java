package com.jfonzuer.controllers;

import com.jfonzuer.dto.mapper.AreaMapper;
import com.jfonzuer.dto.mapper.CountryMapper;
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
public class ConstantsController {

    private final FetishRepository fetishRepository;
    private final UserTypeRepository userTypeRepository;
    private final CountryRepository countryRepository;
    private final AreaRepository areaRepository;

    private Country france = Country.Builder.country().withId(MessengerUtils.FRANCE_ID).build();
    private Country belgium = Country.Builder.country().withId(MessengerUtils.BELGIUM_ID).build();
    private Country lux = Country.Builder.country().withId(MessengerUtils.LUX_ID).build();
    private Country swiss = Country.Builder.country().withId(MessengerUtils.SWISS_ID).build();

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
        constantsResponse.setCountries(countryRepository.findAll().stream().map(CountryMapper::toDto).collect(Collectors.toList()));
        constantsResponse.setUserTypes(userTypeRepository.findAll().stream().map(UserTypeMapper::toDto).collect(Collectors.toList()));
        constantsResponse.setFetishes(fetishRepository.findAll().stream().map(FetishMapper::toDto).collect(Collectors.toList()));
        constantsResponse.setFranceAreas(areaRepository.findByCountry(france).stream().map(AreaMapper::toDto).collect(Collectors.toList()));
        constantsResponse.setBelgiumAreas(areaRepository.findByCountry(belgium).stream().map(AreaMapper::toDto).collect(Collectors.toList()));
        constantsResponse.setLuxemburgAreas(areaRepository.findByCountry(lux).stream().map(AreaMapper::toDto).collect(Collectors.toList()));
        constantsResponse.setSwissAreas(areaRepository.findByCountry(swiss).stream().map(AreaMapper::toDto).collect(Collectors.toList()));
        return constantsResponse;
    }
}
