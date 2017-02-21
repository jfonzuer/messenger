package com.jfonzuer.repository;

import com.jfonzuer.entities.Area;
import com.jfonzuer.entities.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by pgm on 20/02/17.
 */
public interface AreaRepository extends JpaRepository<Area, Long> {
    List<Area> findByCountry(Country country);
}
