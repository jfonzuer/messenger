package com.jfonzuer.service;

import com.jfonzuer.dto.VisitDto;
import com.jfonzuer.dto.mapper.VisitMapper;
import com.jfonzuer.entities.User;
import com.jfonzuer.entities.Visit;
import com.jfonzuer.repository.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by pgm on 12/03/17.
 */
@Service
public class VisitService {

    private final VisitRepository visitRepository;

    @Autowired
    public VisitService(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    public Page<VisitDto> getAll(User user, Pageable p) {
        Page<Visit> visits = this.visitRepository.findAllByVisited(user, p);

        // on récupére les visits unseen et on les set à seen avant de les save
        List<Visit> unseenVisits = this.visitRepository.findAllByVisitedAndIsSeenByVisitedOrderByIdDesc(user, false);
        unseenVisits.stream().forEach(v -> { v.setSeenByVisited(true); this.visitRepository.save(v); });
        return visits.map(VisitMapper::toDto);
    }
}
