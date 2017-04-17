package com.jfonzuer.controllers;

import com.jfonzuer.dto.VisitDto;
import com.jfonzuer.entities.User;
import com.jfonzuer.repository.VisitRepository;
import com.jfonzuer.service.SubscriptionService;
import com.jfonzuer.service.UserService;
import com.jfonzuer.service.VisitService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Created by pgm on 18/10/16.
 */

@RestController
@RequestMapping("/visits")
@PreAuthorize("hasRole('USER')")
public class VisitController {

    private static final Logger LOGGER = LoggerFactory.getLogger(VisitController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private VisitRepository visitRepository;

    @Autowired
    private VisitService visitService;

    @Autowired
    private SubscriptionService subscriptionService;

    /**
     * Endpoint permettant de renvoyer la liste des visites reçues par un utilisateur
     * @param request
     * @param p
     * @return
     */
    @PreAuthorize("hasRole('PREMIUM')")
    @RequestMapping(method = RequestMethod.GET)
    public Page<VisitDto> getAllVisitsByUser(HttpServletRequest request, Pageable p) {
        User visited = userService.getUserFromToken(request);
        subscriptionService.checkSubscriptionAsync(visited);
        return visitService.getAll(visited, p);
    }

    /**
     * Endpoint permettant de renvoyer le nombre de visites reçues
     * @param request
     * @return
     */
    @RequestMapping(value = "/number", method = RequestMethod.GET)
    public Long getUnseenVisits(HttpServletRequest request) {
        User visited = userService.getUserFromToken(request);
        return this.visitRepository.countByVisitedAndIsSeenByVisited(visited, false);
    }
}
