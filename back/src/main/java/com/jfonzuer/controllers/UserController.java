package com.jfonzuer.controllers;

import com.jfonzuer.dto.*;
import com.jfonzuer.dto.mapper.UserMapper;
import com.jfonzuer.dto.response.InformationUpdateDto;
import com.jfonzuer.entities.User;
import com.jfonzuer.repository.UserRepository;
import com.jfonzuer.service.UserService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mobile.device.Device;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by pgm on 19/09/16.
 */

@RestController
@RequestMapping("/users")
@PreAuthorize("hasRole('USER')")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    /**
     * endpoint permettant de rechercher les derniers utilisateurs inscrits
     * @param request
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Page<UserDto> getLastRegisteredUsers( HttpServletRequest request, Pageable p) {
        return userService.getLastRegisteredUsers(request, p);
    }

    /**
     * endpoint permettant la recherche d'utilisateurs
     * @param searchDto
     * @param p
     * @param request
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Page<UserDto> searchUsers(@RequestBody SearchDto searchDto, Pageable p, HttpServletRequest request) {
        return userService.search(request, searchDto, p);
    }

    /**
     * endpoint pour renvoyer le profil demandé, mettre à jour les visites et envoyer un mail
     * @param request
     * @param visitedId
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public UserDto getById(HttpServletRequest request, @PathVariable("id") Long visitedId) {
        return userService.getUserAndUpdateVisit(request, visitedId);
    }

    /**
     * Méthode permettant à l'utilisateur de mettre à jour son profil
     * @param request
     * @param dto
     * @return
     */
    @RequestMapping(value = "/profile", method = RequestMethod.PUT)
    public UserDto updateProfile(HttpServletRequest request, @Valid @RequestBody UserDto dto) {
        User user = userService.getUserFromToken(request);
        return userService.updateProfile(user, dto);
    }

    /**
     * Méthode permettant à l'utilisateur de mettre à jour ses informations
     * @param request
     * @param userDto
     * @return
     */
    @RequestMapping(value = "/informations", method = RequestMethod.PUT)
    public InformationUpdateDto updateInformation(HttpServletRequest request, Device device, @Valid @RequestBody UserDto userDto) {
        User user = userService.getUserFromToken(request);
        return userService.updateInformations(user, userDto, device);
    }


    /**
     * endpoint permettant à l'utilisateur connecté de mettre à jour son mdp.
     * @param request
     * @param passwordDto
     */
    @RequestMapping(value = "/password/reset", method = RequestMethod.PUT)
    public void updatePassword(HttpServletRequest request, @Valid @RequestBody PasswordDto passwordDto) {
        userService.updatePassword(request, passwordDto);
    }


    /**
     * endpoint permettant de signaler un utilisateur
     * @param request
     * @param id
     * @return
     */
    @RequestMapping(value = "/report/{id}", method = RequestMethod.GET)
    public ResponseDto reportUser(HttpServletRequest request, @PathVariable Long id) {
        User user = userService.getUserFromToken(request);
        User reportedUser = userService.findByIdOrThrowException(id);

        // if user has reported another user in the last 24 hours
        if (!user.getLastReportDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Vous avez déjà signalé un utilisateur lors des 24 dernières heures");
        }
        
        // increment number of reports, last report date and save both users
        reportedUser.setReportedAsFake(reportedUser.getReportedAsFake() + 1);
        user.setLastReportDate(LocalDate.now());
        Arrays.asList(reportedUser, user).stream().forEach(u -> userRepository.save(u));

        return new ResponseDto("L utilisateur a été signalé");
    }

    /**
     * endpoint permettant à l'utilisateur de modifier les paramètres de ses alertes
     * @param request
     * @param alerts
     * @return
     */
    @RequestMapping(value = "/alerts", method = RequestMethod.PUT)
    public ResponseDto alerts(HttpServletRequest request, @RequestBody AlertsDto alerts) {
        User user = userService.getUserFromToken(request);
        user.setNotifyMessage(alerts.isNotifyMessage());
        user.setNotifyVisit(alerts.isNotifyVisit());
        user = userRepository.save(user);
        return new ResponseDto("Vos préférences ont été mises à jour");
    }

    /**
     * endpoint permettant à l'utilisateur de désactiver son compte
     * @param request
     * @param desactivate
     * @return
     */
    @RequestMapping(value = "/desactivate", method = RequestMethod.PUT)
    public ResponseDto desactivate(HttpServletRequest request, @RequestBody DesactivateDto desactivate) {
        User user = userService.getUserFromToken(request);
        user.setEnabled(desactivate.isDesactivate());
        user = userRepository.save(user);
        return new ResponseDto("Votre compte a été désactivé");
    }

    /**
     * Méthode permettant de bloquer un utilisateur
     * @param request
     * @param dto
     * @return
     */
    @RequestMapping(value = "/block", method = RequestMethod.POST)
    public List<UserDto> block(HttpServletRequest request, @RequestBody UserDto dto) {
        User user = userService.getUserFromToken(request);
        User userToBlock = UserMapper.fromDto(dto);
        return userService.blockUser(user, userToBlock).stream().map(UserMapper::toLightDto).collect(Collectors.toList());
    }

    /**
     * Méthode permmettant de débloquer un utilisateur
     * @param request
     * @param dto
     * @return
     */
    @RequestMapping(value = "/unblock", method = RequestMethod.POST)
    public List<UserDto> unblock(HttpServletRequest request, @RequestBody UserDto dto) {
        User user = userService.getUserFromToken(request);
        User userToUnblock = UserMapper.fromDto(dto);
        userService.unblockUser(user, userToUnblock);
        return user.getBlockedUsers().stream().map(UserMapper::toLightDto).collect(Collectors.toList());
    }
}
