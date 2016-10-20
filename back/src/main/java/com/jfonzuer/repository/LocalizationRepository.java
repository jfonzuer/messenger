package com.jfonzuer.repository;

import com.jfonzuer.entities.Localization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by pgm on 17/10/16.
 */
public interface LocalizationRepository extends JpaRepository<Localization, Long> {
    List<Localization> findAll();
}
