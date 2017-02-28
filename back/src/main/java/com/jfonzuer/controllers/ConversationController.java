package com.jfonzuer.controllers;

import com.jfonzuer.dto.ConversationDto;
import com.jfonzuer.dto.MessageDto;
import com.jfonzuer.dto.UserMessageDto;
import com.jfonzuer.dto.mapper.ConversationMapper;
import com.jfonzuer.dto.mapper.MessageMapper;
import com.jfonzuer.dto.mapper.UserMapper;
import com.jfonzuer.entities.Conversation;
import com.jfonzuer.entities.Message;
import com.jfonzuer.entities.User;
import com.jfonzuer.repository.ConversationRepository;
import com.jfonzuer.repository.MessageRepository;
import com.jfonzuer.repository.UserRepository;
import com.jfonzuer.service.ConversationService;
import com.jfonzuer.service.MailService;
import com.jfonzuer.service.UserService;
import com.jfonzuer.service.WebSocketService;
import com.jfonzuer.utils.MessengerUtils;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by pgm on 20/09/16.
 */
@RestController
@RequestMapping("/conversations")
@PreAuthorize("hasRole('USER')")
public class ConversationController {

    private final ConversationRepository conversationRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final MailService mailService;
    private final ConversationService conversationService;
    private final WebSocketService webSocketService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ConversationController.class);

    @Autowired
    public ConversationController(ConversationRepository conversationRepository, UserRepository userRepository, UserService userService, MailService mailService, ConversationService conversationService, WebSocketService webSocketService) {
        this.conversationRepository = conversationRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.mailService = mailService;
        this.conversationService = conversationService;
        this.webSocketService = webSocketService;
    }

    /**
     * Endpoint permettant de retourner la liste des conversations
     * @param request
     * @param p
     * @return
     */
    @PreAuthorize("hasRole('PREMIUM')")
    @RequestMapping(method = RequestMethod.GET)
    public Page<ConversationDto> getAll(HttpServletRequest request, Pageable p) {
        User user = userService.getUserFromToken(request);
        return conversationRepository.findAllByUserOneAndIsDeletedByUserOneOrUserTwoAndIsDeletedByUserTwoOrderByLastModifiedDesc(user, false, user, false, p).map(c -> ConversationMapper.toDto(c, user));
    }

    /**
     * Endpoint permettant de créer une nouvelle conversation
     * @param request
     * @param dto
     * @return
     */
    @PreAuthorize("hasRole('PREMIUM')")
    @RequestMapping(method = RequestMethod.POST)
    public ConversationDto add(HttpServletRequest request, @RequestBody UserMessageDto dto) {
        MessageDto messageDto = dto.getMessage();
        User userOne = userService.getUserFromToken(request);
        User userTwo = UserMapper.fromDto(dto.getUser());
        userService.throwExceptionIfBlocked(userOne, userTwo);

        if (userOne.equals(userTwo)) {
            throw new IllegalArgumentException();
        }
        Conversation conversation = conversationService.createConversationAndAddMessage(userOne, userTwo, messageDto);
        webSocketService.sendToConversationsUsers(conversation);
        mailService.sendAsync(() -> mailService.sendMessageNotification(request.getLocale(), userTwo, userOne));
        return ConversationMapper.toDto(conversation, userOne);
    }

    /**
     * Endpoint permettant de récupérer une conversation particulière
     * @param request
     * @param id
     * @return
     */
    @PreAuthorize("hasRole('PREMIUM')")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ConversationDto getConversationBetweenCurrentUserAndSpecifiedUser(HttpServletRequest request, @PathVariable Long id) {
        LOGGER.info(" in getConversationBetweenCurrentUserAndSpecifiedUser ");
        User currentUser = userService.getUserFromToken(request);
        User specifiedUser = userRepository.findOne(id);
        Conversation conversation = conversationService.getConversationOrCreateOne(currentUser, specifiedUser);
        return ConversationMapper.toDto(conversation, currentUser);
    }

    /**
     * Endpoint permettant de supprimer une conversation
     * @param request
     * @param id
     */
    @PreAuthorize("hasRole('PREMIUM')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteConversation(HttpServletRequest request, @PathVariable Long id) {
        User currentUser = userService.getUserFromToken(request);
        conversationService.deleteByIdAndUser(id, currentUser);
    }

    /**
     * Endpoint permettant de renvoyer le nombre de conversations non lues
     * @param request
     * @return
     */
    @RequestMapping(value = "/unread", method = RequestMethod.GET)
    public Long getUnreadNumerConversations(HttpServletRequest request) {
        User user = userService.getUserFromToken(request);
        return this.conversationRepository.countByUserOneAndIsReadByUserOne(user, false) +  this.conversationRepository.countByUserTwoAndIsReadByUserTwo(user, false);
    }
}
