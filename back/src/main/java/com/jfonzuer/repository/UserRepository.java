package com.jfonzuer.repository;

import com.jfonzuer.entities.*;
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
    User findByIdAndEnabledAndIsBlocked(Long id, Boolean enabled, Boolean isBlocked);
    Page<User> findByIdNotAndTypeAndEnabledAndIsBlockedOrderByIdDesc(Long id, UserType t, Boolean enabled, Boolean isBlocked, Pageable page);

    @Query("select u from User u where u.type = :type and u.enabled = true and u.isBlocked = false and u.id <> :id and (:country is null  or u.country = :country) " +
            "and (:area is null  or u.area = :area) " +
            "and (:keyword is null or lower(u.description) like concat('%', lower(:keyword), '%') or lower(u.username) like concat('%', lower(:keyword) ,'%')) " +
            "and ((:dateOne is null and :dateTwo is null) or (u.birthDate between :dateOne and :dateTwo)) " +
            "order by u.lastActivityDate desc ")
    Page<User> search(@Param("id") Long id, @Param("type") UserType type, @Param("country") Country country, @Param("area") Area area, @Param("keyword") String keyword, @Param("dateOne") LocalDate dateOne, @Param("dateTwo") LocalDate dateTwo, Pageable p);
    List<User> findByReportedAsFakeGreaterThanOrderByReportedAsFakeDesc(Long reportedTime);
}
