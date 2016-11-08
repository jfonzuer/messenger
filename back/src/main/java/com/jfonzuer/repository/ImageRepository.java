package com.jfonzuer.repository;

import com.jfonzuer.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by pgm on 07/11/16.
 */
@RepositoryRestResource
public interface ImageRepository extends JpaRepository<Image, Long> {
}
