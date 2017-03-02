package com.jfonzuer.repository;

import com.jfonzuer.entities.User;
import com.jfonzuer.entities.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Created by pgm on 06/10/16.
 */
@RepositoryRestResource
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    public List<String> findRoleByUser(User user);
    public List<UserRole> deleteByRoleAndUser(String role, User user);
}
