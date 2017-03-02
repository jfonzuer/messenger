package com.jfonzuer.controllers;

import com.jfonzuer.dto.MessageDto;
import com.jfonzuer.dto.mapper.ConversationMapper;
import com.jfonzuer.dto.mapper.MessageMapper;
import com.jfonzuer.entities.*;
import com.jfonzuer.repository.ConversationRepository;
import com.jfonzuer.repository.MessageRepository;
import com.jfonzuer.repository.UserRepository;
import com.jfonzuer.service.*;
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
import org.springframework.messaging.simp.SimpMessagingTemplate;
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
@PreAuthorize("hasRole('PREMIUM')")
public class MessageController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ConversationService conversationService;

    @Autowired
    private MailService mailService;

    @Autowired
    private UserService userService;

    @Autowired
    private WebSocketService webSocketService;

    @Autowired
    private AsyncService asyncService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private SubscriptionService subscriptionService;

    /**
     * endpoint de récupérer les messages par id de conversation
     * @param request
     * @param id
     * @param p
     * @return
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Page<MessageDto> getByConversation(HttpServletRequest request, @PathVariable Long id, Pageable p) {

        User currentUser = userService.getUserFromToken(request);
        subscriptionService.checkSubscriptionAsync(currentUser);

        // check if conversation exists and if user is part of this
        Conversation conversation = conversationService.getConversationByIdAndUser(id, currentUser);

        if (conversation != null) {
            conversationService.updateConversationIsRead(conversation, currentUser);
        }
        // on retourne null si la conversation n'existe pas
        return conversation == null ? null : messageService.getByConversationAndUser(conversation, currentUser, p);
    }

    /**
     * endpoint permettant de post un message de type image dans une conversation
     * @param request
     * @param file
     * @param id
     * @return
     */
    @RequestMapping(value = "/image", method = RequestMethod.POST)
    public MessageDto addImage(HttpServletRequest request, @RequestParam(value = "file") MultipartFile file, @RequestParam(value = "id") Long id) {

        User sender = userService.getUserFromToken(request);
        subscriptionService.checkSubscriptionAsync(sender);

        Conversation conversation = conversationService.returnConversationOrThrowException(id);

        User target = MessengerUtils.getOtherUser(conversation, sender);

        MessageDto dto = new MessageDto.MessageDtoBuilder().setContent("Image").createMessageDto();
        conversationService.updateConversation(conversation, sender, dto);
        webSocketService.sendToConversationsUsers(conversation);

        Message message = messageService.saveImage(dto, conversation, sender);

        String url = imageService.saveImageInConversation(conversation, message, file);

        message.setUrl(url);
        messageRepository.save(message);


        // send email if sender is not last sender
        if (!target.getLastMessageBy().equals(sender)) {
            asyncService.executeAsync(() -> mailService.sendMessageNotification(request.getLocale(), MessengerUtils.getOtherUser(conversation, sender), sender));
        }

        userService.updateLastMessageBy(target, sender);
        return  MessageMapper.toDto(message);
    }


}
