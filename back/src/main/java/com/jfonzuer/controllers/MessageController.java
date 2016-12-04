package com.jfonzuer.controllers;

import com.jfonzuer.dto.MessageDto;
import com.jfonzuer.dto.mapper.MessageMapper;
import com.jfonzuer.entities.Conversation;
import com.jfonzuer.entities.Message;
import com.jfonzuer.entities.User;
import com.jfonzuer.repository.ConversationRepository;
import com.jfonzuer.repository.MessageRepository;
import com.jfonzuer.repository.UserRepository;
import com.jfonzuer.service.MailService;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pgm on 21/09/16.
 */

@RestController
@RequestMapping("/messages")
//@PreAuthorize("hasRole('USER')")
@CrossOrigin(origins = "*", maxAge = 3600)
public class MessageController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageController.class);

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final ConversationRepository conversationRepository;
    private final MailService mailService;
    private final UserService userService;

    @Autowired
    public MessageController(MessageRepository messageRepository, UserRepository userRepository, ConversationRepository conversationRepository, MailService mailService, UserService userService) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.conversationRepository = conversationRepository;
        this.mailService = mailService;
        this.userService = userService;
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Page<MessageDto> getByConversation(HttpServletRequest request, @PathVariable Long id, Pageable p) {

        User currentUser = userService.getUserFromToken(request);

        // check if conversation exists and if user is part of this
        Conversation conversation = getConversationByIdAndUser(id, currentUser);

        if (conversation != null) {
            updateConversationIsRead(conversation, currentUser);
        }

        // on retourne null si la conversation n'existe pas
        return conversation == null ? null : messageRepository.findByConversationOrderByIdDesc(conversation, p).map(m -> MessageMapper.toDto(m));
    }

    @RequestMapping(method = RequestMethod.POST)
    public MessageDto addToConversation(HttpServletRequest request, @RequestBody MessageDto dto) {

        User sender = userService.getUserFromToken(request);

        // check if conversation exists
        Conversation conversation = returnConversationOrThrowException(dto.getConversation().getId());
        conversation.setPreview(MessengerUtils.getPreviewFromMessage(dto));
        conversationRepository.save(conversation);

        Message message = MessageMapper.fromDto(dto);
        message.setSource(sender);
        message.setSentDateTime(LocalDateTime.now());
        message = messageRepository.save(message);
        mailService.sendMessageNotification(request.getLocale(), MessengerUtils.getOtherUser(conversation, sender), sender);
        return  MessageMapper.toDto(message);
    }

    private Conversation getConversationByIdAndUser(Long id, User currentUser) {
        User specifiedUser = userRepository.findOne(id);

        if (specifiedUser == null) {
            throw  new ResourceNotFoundException();
        }
        return conversationRepository.findByUserOneAndUserTwoOrUserTwoAndUserOne(currentUser, specifiedUser, currentUser, specifiedUser);
    }

    private Conversation returnConversationOrThrowException(Long id) {
        Conversation conversation = conversationRepository.findOne(id);
        if (conversation == null) {
            throw new ResourceNotFoundException("La conversation n'a pu être trouvée");
        }
        return conversation;
    }

    private void updateConversationIsRead(Conversation c, User user) {
        if (MessengerUtils.isUserOne(user, c) && c.getReadByUserOne() == false) {
            c.setReadByUserOne(true);
            conversationRepository.save(c);
        }
        else if (MessengerUtils.isUserTwo(user, c) && c.getReadByUserTwo() == false) {
            c.setReadByUserTwo(true);
            conversationRepository.save(c);
        }
    }
}
