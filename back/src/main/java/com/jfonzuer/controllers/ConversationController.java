package com.jfonzuer.controllers;

import com.jfonzuer.dto.ConversationDto;
import com.jfonzuer.dto.MessageDto;
import com.jfonzuer.dto.UserMessageDto;
import com.jfonzuer.dto.mapper.ConversationMapper;
import com.jfonzuer.dto.mapper.UserMapper;
import com.jfonzuer.entities.Conversation;
import com.jfonzuer.entities.User;
import com.jfonzuer.repository.ConversationRepository;
import com.jfonzuer.repository.UserRepository;
import com.jfonzuer.service.*;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Created by pgm on 20/09/16.
 */
@RestController
@RequestMapping("/conversations")
@PreAuthorize("hasRole('USER')")
public class ConversationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConversationController.class);

    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @Autowired
    private ConversationService conversationService;

    @Autowired
    private WebSocketService webSocketService;

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private AsyncService asyncService;

    /**
     * Endpoint permettant de retourner la liste des conversations
     * @param request
     * @param p
     * @return
     */
    //TODO : uncomment to activate premium
    //@PreAuthorize("hasRole('PREMIUM')")
    @RequestMapping(method = RequestMethod.GET)
    public Page<ConversationDto> getAll(HttpServletRequest request, Pageable p) {
        User user = userService.getUserFromToken(request);
        subscriptionService.checkSubscriptionAsync(user);
        return conversationRepository.findByUserOneAndIsDeletedByUserOneOrUserTwoAndIsDeletedByUserTwoOrderByLastModifiedDesc(user, false, user, false, p).map(c -> ConversationMapper.toDto(c, user));
    }

    /**
     * Endpoint permettant de créer une nouvelle conversation
     * @param request
     * @param dto
     * @return
     */
    //TODO : uncomment to activate premium
    //@PreAuthorize("hasRole('PREMIUM')")
    @RequestMapping(method = RequestMethod.POST)
    public ConversationDto add(HttpServletRequest request, @RequestBody UserMessageDto dto) {
        User userOne = userService.getUserFromToken(request);
        subscriptionService.checkSubscriptionAsync(userOne);

        MessageDto messageDto = dto.getMessage();
        User userTwo = UserMapper.fromDto(dto.getUser());
        userService.throwExceptionIfBlocked(userOne, userTwo);

        if (userOne.equals(userTwo)) {
            throw new IllegalArgumentException();
        }
        Conversation conversation = conversationService.createConversationAndAddMessage(userOne, userTwo, messageDto);
        webSocketService.sendToConversationsToOtherUser(conversation);
        asyncService.executeAsync(() -> mailService.sendMessageNotification(request.getLocale(), userTwo, userOne));
        return ConversationMapper.toDto(conversation, userOne);
    }

    /**
     * Endpoint permettant de récupérer une conversation particulière
     * @param request
     * @param id
     * @return
     */
    //TODO : uncomment to activate premium
    //@PreAuthorize("hasRole('PREMIUM')")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ConversationDto getConversationBetweenCurrentUserAndSpecifiedUser(HttpServletRequest request, @PathVariable Long id) {
        LOGGER.info(" in getConversationBetweenCurrentUserAndSpecifiedUser ");
        User currentUser = userService.getUserFromToken(request);
        LOGGER.debug("currentUser = {}", currentUser);
        subscriptionService.checkSubscriptionAsync(currentUser);
        User specifiedUser = userRepository.findOne(id);
        LOGGER.debug("specifiedUser = {}", specifiedUser);
        Conversation conversation = conversationService.getConversationOrCreateOne(currentUser, specifiedUser);
        return ConversationMapper.toDto(conversation, currentUser);
    }

    /**
     * Endpoint permettant de supprimer une conversation
     * @param request
     * @param id
     */
    //TODO : uncomment to activate premium
    //@PreAuthorize("hasRole('PREMIUM')")
    @DeleteMapping(value = "/{id}")
    public void deleteConversation(HttpServletRequest request, @PathVariable Long id) {
        User currentUser = userService.getUserFromToken(request);
        subscriptionService.checkSubscriptionAsync(currentUser);
        conversationService.deleteByIdAndUser(id, currentUser);
    }

    /**
     * Endpoint permettant de renvoyer le nombre de conversations non lues
     * @param request
     * @return
     */
    @GetMapping(value = "/unread")
    public Long getUnreadNumerConversations(HttpServletRequest request) {
        User user = userService.getUserFromToken(request);
        return this.conversationRepository.countByUserOneAndIsReadByUserOne(user, false) +  this.conversationRepository.countByUserTwoAndIsReadByUserTwo(user, false);
    }
}
