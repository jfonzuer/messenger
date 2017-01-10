package com.jfonzuer.controllers;

import com.jfonzuer.dto.MessageDto;
import com.jfonzuer.dto.mapper.MessageMapper;
import com.jfonzuer.entities.*;
import com.jfonzuer.repository.ConversationRepository;
import com.jfonzuer.repository.MessageRepository;
import com.jfonzuer.repository.UserRepository;
import com.jfonzuer.service.ConversationService;
import com.jfonzuer.service.MailService;
import com.jfonzuer.service.MessageService;
import com.jfonzuer.service.UserService;
import com.jfonzuer.storage.StorageService;
import com.jfonzuer.utils.MessengerUtils;
import com.jfonzuer.validator.MediaValidator;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    private final MessageService messageService;
    private final UserRepository userRepository;
    private final ConversationRepository conversationRepository;
    private final ConversationService conversationService;
    private final MailService mailService;
    private final UserService userService;
    private MediaValidator mediaValidator;
    private StorageService storageService;

    @Value("${upload.conversation.directory}")
    private String conversationLocation;

    @Autowired
    public MessageController(MessageRepository messageRepository, MessageService messageService, UserRepository userRepository, ConversationRepository conversationRepository, ConversationService conversationService, MailService mailService, UserService userService, MediaValidator mediaValidator, StorageService storageService) {
        this.messageRepository = messageRepository;
        this.messageService = messageService;
        this.userRepository = userRepository;
        this.conversationRepository = conversationRepository;
        this.conversationService = conversationService;
        this.mailService = mailService;
        this.userService = userService;
        this.mediaValidator = mediaValidator;
        this.storageService = storageService;
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Page<MessageDto> getByConversation(HttpServletRequest request, @PathVariable Long id, Pageable p) {

        User currentUser = userService.getUserFromToken(request);

        // check if conversation exists and if user is part of this
        Conversation conversation = conversationService.getConversationByIdAndUser(id, currentUser);

        if (conversation != null) {
            conversationService.updateConversationIsRead(conversation, currentUser);
        }
        // on retourne null si la conversation n'existe pas
        return conversation == null ? null : messageService.getByConversationAndUser(conversation, currentUser, p);
    }

    @RequestMapping(value = "/newer/{userId}/{messageId}", method = RequestMethod.GET)
    public List<MessageDto> getNewerMessages(HttpServletRequest request, @PathVariable("userId") Long userId, @PathVariable("messageId") Long messageId) {
        User currentUser = userService.getUserFromToken(request);

        // check if conversation exists and if user is part of this
        Conversation conversation = conversationService.getConversationByIdAndUser(userId, currentUser);
        if (conversation != null) {
            conversationService.updateConversationIsRead(conversation, currentUser);
        }
        return conversation == null ? null : messageRepository.findByConversationAndIdGreaterThanOrderByIdDesc(conversation, messageId).stream().map(MessageMapper::toDto).collect(Collectors.toList());
    }


    @RequestMapping(method = RequestMethod.POST)
    public MessageDto addToConversation(HttpServletRequest request, @RequestBody MessageDto dto) {

        User sender = userService.getUserFromToken(request);
        Conversation conversation = conversationService.returnConversationOrThrowException(dto.getConversation().getId());
        User target = MessengerUtils.getOtherUser(conversation, sender);
        target.setLastMessageBy(sender);
        userRepository.save(target);

        conversationService.updateConversation(conversation, sender, dto);

        Message message = MessageMapper.fromDto(dto);
        message.setType(MessageType.TEXT);
        message = messageService.saveMessage(message, conversation.getUserOne(), conversation.getUserTwo());

        // send email if sender is not last sender
        if (!target.getLastMessageBy().equals(sender)) {
            mailService.sendAsync(() -> mailService.sendMessageNotification(request.getLocale(), MessengerUtils.getOtherUser(conversation, sender), sender));
        }
        return  MessageMapper.toDto(message);
    }

    @RequestMapping(value = "/image", method = RequestMethod.POST)
    public MessageDto addImage(HttpServletRequest request, @RequestParam(value = "file") MultipartFile file, @RequestParam(value = "id") Long id) {

        User sender = userService.getUserFromToken(request);
        Conversation conversation = conversationService.returnConversationOrThrowException(id);
        User target = MessengerUtils.getOtherUser(conversation, sender);
        target.setLastMessageBy(sender);
        userRepository.save(target);

        MessageDto dto = new MessageDto.MessageDtoBuilder().setContent("Image").createMessageDto();
        conversationService.updateConversation(conversation, sender, dto);

        Message message = MessageMapper.fromDto(dto);
        message.setType(MessageType.IMAGE);
        message.setConversation(conversation);
        message.setSource(sender);
        message = messageService.saveMessage(message, conversation.getUserOne(), conversation.getUserTwo());

        String filename = conversation.getId() + "_" + message.getId() + mediaValidator.getExtension(file.getContentType());
        String location = conversationLocation + conversation.getId() + "/";
        String url = location + filename;
        message.setUrl(url);
        messageRepository.save(message);
        storageService.createDirectoriesAndStore(file, location, filename);

        // send email if sender is not last sender
        if (!target.getLastMessageBy().equals(sender)) {
            mailService.sendAsync(() -> mailService.sendMessageNotification(request.getLocale(), MessengerUtils.getOtherUser(conversation, sender), sender));
        }
        return  MessageMapper.toDto(message);
    }


}
