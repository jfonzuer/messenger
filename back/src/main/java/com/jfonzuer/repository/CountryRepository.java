package com.jfonzuer.repository;

import com.jfonzuer.entities.Country;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by pgm on 20/02/17.
 */
public interface CountryRepository extends JpaRepository<Country, Long> {
}
