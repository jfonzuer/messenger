package com.jfonzuer.repository;

import com.jfonzuer.entities.Localization;
import com.jfonzuer.entities.User;
import com.jfonzuer.entities.UserType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by pgm on 19/09/16.
 */
@RepositoryRestResource
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    Page<User> findAllByTypeOrderByIdDesc(UserType t, Pageable page);
    List<User> findTop20ByTypeOrderByIdDesc(UserType type);

    Page<User> findAllByType(UserType type, Pageable pageable);

    @Query("select u from User u where u.type = :type and (:localization is null  or u.localization = :localization) " +
            "and (:keyword is null or lower(u.description) like concat('%', lower(:keyword), '%') or lower(u.username) like concat('%', lower(:keyword) ,'%')) " +
            "and ((:dateOne is null and :dateTwo is null) or (u.birthDate between :dateOne and :dateTwo)) " +
            "order by u.id desc ")
    Page<User> search(@Param("type") UserType type, @Param("localization") Localization localization, @Param("keyword") String keyword, @Param("dateOne") LocalDate dateOne, @Param("dateTwo") LocalDate dateTwo, Pageable p);


    Page<User> findAllByTypeAndLocalizationAndDescriptionIgnoreCaseContainingOrUsernameIgnoreCaseContaining(UserType type, Localization localization, String keyword, String username, Pageable pageable);
}
