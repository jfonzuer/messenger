package com.jfonzuer.websocket;

import com.jfonzuer.dto.MessageDto;
import com.jfonzuer.dto.mapper.MessageMapper;
import com.jfonzuer.entities.Conversation;
import com.jfonzuer.entities.Message;
import com.jfonzuer.entities.MessageType;
import com.jfonzuer.entities.User;
import com.jfonzuer.repository.UserRepository;
import com.jfonzuer.service.*;
import com.jfonzuer.utils.MessengerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

/**
 * Created by pgm on 31/01/17.
 */
@Controller
public class MessageWebSocketController {

    private final static Logger LOGGER = LoggerFactory.getLogger(MessageWebSocketController.class);

    private final UserService userService;
    private final ConversationService conversationService;
    private final UserRepository userRepository;
    private final MessageService messageService;
    private final MailService mailService;
    private final WebSocketService webSocketService;
    private final SimpMessagingTemplate template;
    private final AsyncService asyncService;

    @Autowired
    public MessageWebSocketController(UserService userService, ConversationService conversationService, UserRepository userRepository, MessageService messageService, MailService mailService, WebSocketService webSocketService, SimpMessagingTemplate template, AsyncService asyncService) {
        this.userService = userService;
        this.conversationService = conversationService;
        this.userRepository = userRepository;
        this.messageService = messageService;
        this.mailService = mailService;
        this.webSocketService = webSocketService;
        this.template = template;
        this.asyncService = asyncService;
    }

    @Transactional
    @MessageMapping("/ws-conversation-endpoint/{id}")
    public void addMessage(@DestinationVariable String id, MessageDto dto, SimpMessageHeaderAccessor headerAccessor) throws Exception {
        LOGGER.debug("add message dto : {}" + dto);
        User sender = (User) headerAccessor.getSessionAttributes().get("connectedUser");
        Locale locale = (Locale) headerAccessor.getSessionAttributes().get("locale");

        // TODO : refactor endpoint
        Conversation c = conversationService.returnConversationOrThrowException(dto.getConversation().getId());
        User target = MessengerUtils.getOtherUser(c, sender);

        // si user est bloqué on renvoit l'erreur à la session correspondante dans le channel queue/errors
        try {
            userService.throwExceptionIfBlocked(sender, target);
        } catch (IllegalArgumentException e) {
            this.template.convertAndSend("/queue/errors/" + sender.getId(), e.getMessage());
            throw new IllegalArgumentException(e.getMessage());
        }

        target.setLastMessageBy(sender);
        userRepository.save(target);

        Conversation conversation = conversationService.updateConversation(c, sender, dto);
        LOGGER.debug("send conversation via websockets {}", conversation);
        this.webSocketService.sendToConversationsUsers(conversation);

        Message message = MessageMapper.fromDto(dto);
        message.setType(MessageType.TEXT);
        message = messageService.saveMessage(message, c.getUserOne(), c.getUserTwo());

        // send email if sender is not last sender
        if (!target.getLastMessageBy().equals(sender)) {
            asyncService.executeAsync(() -> mailService.sendMessageNotification(locale, MessengerUtils.getOtherUser(c, sender), sender));
        }
        this.template.convertAndSend("/ws-conversation-broker/conversation/" + id, MessageMapper.toDto(message));
    }
}
