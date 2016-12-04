package com.jfonzuer.controllers;

import com.jfonzuer.dto.VisitDto;
import com.jfonzuer.dto.mapper.VisitMapper;
import com.jfonzuer.entities.User;
import com.jfonzuer.entities.Visit;
import com.jfonzuer.repository.UserRepository;
import com.jfonzuer.repository.VisitRepository;
import com.jfonzuer.service.UserService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by pgm on 18/10/16.
 */

@RestController
@RequestMapping("/visits")
@CrossOrigin(origins = "*", maxAge = 3600)
//@PreAuthorize("hasRole('USER')")
public class VisitController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final VisitRepository visitRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(VisitController.class);

    @Autowired
    public VisitController(UserRepository userRepository, UserService userService, VisitRepository visitRepository) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.visitRepository = visitRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Page<VisitDto> getAllVisitsByUser(HttpServletRequest request, Pageable p) {

        User visited = userService.getUserFromToken(request);

        Page<Visit> visits = this.visitRepository.findAllByVisited(visited, p);

        // on récupére les visits unseen et on les set à seen avant de les save
        List<Visit> unseenVisits = this.visitRepository.findAllByVisitedAndIsSeenByVisitedOrderByIdDesc(visited, false);
        unseenVisits.stream().forEach(v -> { v.setSeenByVisited(true); this.visitRepository.save(v); });
        return visits.map(VisitMapper::toDto);
    }

    @RequestMapping(value = "/number", method = RequestMethod.GET)
    public Long getUnseenVisits(HttpServletRequest request) {
        User visited = userService.getUserFromToken(request);
        return this.visitRepository.countByVisitedAndIsSeenByVisited(visited, false);
    }
}
