package com.jfonzuer.repository;

import com.jfonzuer.entities.User;
import com.jfonzuer.entities.Visit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by pgm on 17/10/16.
 */
public interface VisitRepository extends JpaRepository<Visit, Long> {
    Page<Visit> findAllByVisited(User visited, Pageable pageable);
    List<Visit> findAllByVisitedAndIsSeenByVisitedOrderByIdDesc(User visited, Boolean isSeenByVisited);
    Long countByVisitedAndIsSeenByVisited(User visited, Boolean isSeenByVisited);
}
