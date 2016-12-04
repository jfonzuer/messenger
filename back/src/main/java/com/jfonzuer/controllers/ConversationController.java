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
import com.jfonzuer.service.UserService;
import com.jfonzuer.utils.MessengerUtils;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by pgm on 20/09/16.
 */
@RestController
@RequestMapping("/conversations")
@CrossOrigin(origins = "*", maxAge = 3600)
//@PreAuthorize("hasRole('USER')")
public class ConversationController {

    private final ConversationRepository conversationRepository;
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    private final UserService userService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ConversationController.class);

    @Autowired
    public ConversationController(ConversationRepository conversationRepository, UserRepository userRepository, MessageRepository messageRepository, UserService userService) {
        this.conversationRepository = conversationRepository;
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Page<ConversationDto> getAll(HttpServletRequest request, Pageable p) {
        User user = userService.getUserFromToken(request);
        return conversationRepository.findAllByUserOneAndIsDeletedByUserOneOrUserTwoAndIsDeletedByUserTwoOrderByLastModifiedDesc(user, false, user, false, p).map(c -> ConversationMapper.toDto(c, user));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ConversationDto add(HttpServletRequest request, @RequestBody UserMessageDto dto) {

        MessageDto messageDto = dto.getMessage();

        User userOne = userService.getUserFromToken(request);
        User userTwo = UserMapper.fromDto(dto.getUser());

        if (userOne.equals(userTwo)) {
            throw new IllegalArgumentException();
        }

        Conversation conversation = conversationRepository.save(new Conversation(MessengerUtils.getPreviewFromMessage(messageDto), true, false, LocalDateTime.now(), false, false, userOne, userTwo));

        // ajout du message
        Message message = MessageMapper.fromDto(messageDto);
        message.setSentDateTime(LocalDateTime.now());
        message.setConversation(conversation);
        messageRepository.save(message);

        return ConversationMapper.toDto(conversation);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ConversationDto getConversationBetweenCurrentUserAndSpecifiedUser(HttpServletRequest request, @PathVariable Long id) {
        LOGGER.info(" in getConversationBetweenCurrentUserAndSpecifiedUser ");

        User currentUser = userService.getUserFromToken(request);
        User specifiedUser = userRepository.findOne(id);

        List<Conversation> list = conversationRepository.findTop1ByUserOneAndUserTwoOrUserTwoAndUserOne(currentUser, specifiedUser, currentUser, specifiedUser);
        System.out.println("list = " + list);
        return (list.size() == 0) ? null : ConversationMapper.toDto(list.get(0), currentUser);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteConversation(HttpServletRequest request, @PathVariable Long id) {

        User currentUser = userService.getUserFromToken(request);
        Conversation conversation = conversationRepository.findTop1ByIdAndUserOneOrUserTwo(id, currentUser, currentUser).get(0);

        if (MessengerUtils.isUserOne(currentUser, conversation)) {
            conversation.setDeletedByUserOne(true);
        }
        else {
            conversation.setDeletedByUserTwo(true);
        }

        if (MessengerUtils.isDeletedByBothUsers(conversation)) {
            conversationRepository.delete(conversation);
        }
        else {
            conversationRepository.save(conversation);
        }
    }

    @RequestMapping(value = "/unread", method = RequestMethod.GET)
    public Long getUnreadNumerConversations(HttpServletRequest request) {

        User user = userService.getUserFromToken(request);

        //User user = this.userService.getUserFromToken(request);
        return this.conversationRepository.countByUserOneAndIsReadByUserOne(user, false) +  this.conversationRepository.countByUserTwoAndIsReadByUserTwo(user, false);
    }
}
