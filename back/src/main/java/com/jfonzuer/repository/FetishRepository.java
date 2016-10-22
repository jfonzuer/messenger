package com.jfonzuer.repository;

import com.jfonzuer.entities.Fetish;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Created by pgm on 17/10/16.
 */
public interface FetishRepository extends JpaRepository<Fetish, Long> {
    List<Fetish> findAll();
}
