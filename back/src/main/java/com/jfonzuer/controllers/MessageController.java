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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

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

    @RequestMapping(value = "/newer/{userId}/{messageId}", method = RequestMethod.GET)
    public List<MessageDto> getNewerMessages(HttpServletRequest request, @PathVariable("userId") Long userId, @PathVariable("messageId") Long messageId) {
        User currentUser = userService.getUserFromToken(request);
        System.out.println("userId = " + userId);
        System.out.println("messageId = " + messageId);

        // check if conversation exists and if user is part of this
        Conversation conversation = getConversationByIdAndUser(userId, currentUser);
        if (conversation != null) {
            updateConversationIsRead(conversation, currentUser);
        }
        return conversation == null ? null : messageRepository.findByConversationAndIdGreaterThanOrderByIdDesc(conversation, messageId).stream().map(MessageMapper::toDto).collect(Collectors.toList());
    }


    @RequestMapping(method = RequestMethod.POST)
    public MessageDto addToConversation(HttpServletRequest request, @RequestBody MessageDto dto) {

        User sender = userService.getUserFromToken(request);
        Conversation conversation = returnConversationOrThrowException(dto.getConversation().getId());
        User target = MessengerUtils.getOtherUser(conversation, sender);

        target.setLastMessageBy(sender);
        userRepository.save(target);

        Message message = MessageMapper.fromDto(dto);
        message.setSource(sender);
        message.setSentDateTime(LocalDateTime.now());
        message = messageRepository.save(message);

        conversation.setPreview(MessengerUtils.getPreviewFromMessage(dto));
        conversation.setLastMessageId(message.getId());
        MessengerUtils.setConversationUnread(conversation, sender);
        conversationRepository.save(conversation);

        // send email if sender is not last sender
        if (!target.getLastMessageBy().equals(sender)) {
            mailService.sendAsync(() -> mailService.sendMessageNotification(request.getLocale(), MessengerUtils.getOtherUser(conversation, sender), sender));
        }
        return  MessageMapper.toDto(message);
    }

    private Conversation getConversationByIdAndUser(Long id, User currentUser) {
        User specifiedUser = userRepository.findOne(id);

        if (specifiedUser == null) {
            throw  new ResourceNotFoundException();
        }
        List<Conversation> conversations = conversationRepository.getConversation(currentUser, specifiedUser);
        return conversations.size() == 0 ? null : conversations.get(0);
    }

    private Conversation returnConversationOrThrowException(Long id) {
        Conversation conversation = conversationRepository.findOne(id);
        if (conversation == null) {
            throw new ResourceNotFoundException("La conversation n'a pu être trouvée");
        }
        return conversation;
    }

    private void updateConversationIsRead(Conversation c, User user) {
        if (MessengerUtils.isUserOne(user, c) && c.getReadByUserOne().equals(Boolean.FALSE)) {
            c.setReadByUserOne(true);
            conversationRepository.save(c);
        }
        else if (MessengerUtils.isUserTwo(user, c) && c.getReadByUserTwo().equals(Boolean.FALSE)) {
            c.setReadByUserTwo(true);
            conversationRepository.save(c);
        }
    }
}
