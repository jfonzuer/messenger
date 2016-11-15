package com.jfonzuer.repository;

import com.jfonzuer.entities.User;
import com.jfonzuer.entities.UserType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Created by pgm on 19/09/16.
 */
@RepositoryRestResource
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    Page<User> findAllByOrderByIdDesc(Pageable page);
    List<User> findTop20ByTypeOrderByIdDesc(UserType type);
}
