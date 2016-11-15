package com.jfonzuer.repository;

import com.jfonzuer.entities.UserType;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by pgm on 14/11/16.
 */
public interface UserTypeRepository extends JpaRepository<UserType, Long> {
}
