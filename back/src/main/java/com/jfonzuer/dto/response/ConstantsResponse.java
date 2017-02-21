package com.jfonzuer.dto.response;

import com.jfonzuer.dto.AreaDto;
import com.jfonzuer.dto.CountryDto;
import com.jfonzuer.dto.FetishDto;
import com.jfonzuer.dto.UserTypeDto;
import com.jfonzuer.entities.Area;
import com.jfonzuer.entities.Fetish;

import java.util.List;

/**
 * Created by pgm on 21/02/17.
 */
public class ConstantsResponse {
    private List<FetishDto> fetishes;
    private List<UserTypeDto> userTypes;
    private List<CountryDto> countryDtos;
    private List<AreaDto> franceAreas;
    private List<AreaDto> belgiumAreas;
    private List<AreaDto> luxembourgAreas;
    private List<AreaDto> swissAreas;

    public ConstantsResponse() {
    }

    public List<FetishDto> getFetishes() {
        return fetishes;
    }

    public void setFetishes(List<FetishDto> fetishes) {
        this.fetishes = fetishes;
    }

    public List<UserTypeDto> getUserTypes() {
        return userTypes;
    }

    public void setUserTypes(List<UserTypeDto> userTypes) {
        this.userTypes = userTypes;
    }

    public List<CountryDto> getCountryDtos() {
        return countryDtos;
    }

    public void setCountryDtos(List<CountryDto> countryDtos) {
        this.countryDtos = countryDtos;
    }

    public List<AreaDto> getFranceAreas() {
        return franceAreas;
    }

    public void setFranceAreas(List<AreaDto> franceAreas) {
        this.franceAreas = franceAreas;
    }

    public List<AreaDto> getBelgiumAreas() {
        return belgiumAreas;
    }

    public void setBelgiumAreas(List<AreaDto> belgiumAreas) {
        this.belgiumAreas = belgiumAreas;
    }

    public List<AreaDto> getLuxembourgAreas() {
        return luxembourgAreas;
    }

    public void setLuxembourgAreas(List<AreaDto> luxembourgAreas) {
        this.luxembourgAreas = luxembourgAreas;
    }

    public List<AreaDto> getSwissAreas() {
        return swissAreas;
    }

    public void setSwissAreas(List<AreaDto> swissAreas) {
        this.swissAreas = swissAreas;
    }
}
