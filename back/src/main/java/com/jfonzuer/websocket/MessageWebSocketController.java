package com.jfonzuer.websocket;

import com.jfonzuer.dto.ConversationDto;
import com.jfonzuer.dto.MessageDto;
import com.jfonzuer.dto.mapper.ConversationMapper;
import com.jfonzuer.dto.mapper.MessageMapper;
import com.jfonzuer.entities.Conversation;
import com.jfonzuer.entities.Message;
import com.jfonzuer.entities.MessageType;
import com.jfonzuer.entities.User;
import com.jfonzuer.repository.UserRepository;
import com.jfonzuer.service.ConversationService;
import com.jfonzuer.service.MailService;
import com.jfonzuer.service.MessageService;
import com.jfonzuer.service.UserService;
import com.jfonzuer.utils.MessengerUtils;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.Locale;

/**
 * Created by pgm on 31/01/17.
 */
@Controller
public class MessageWebSocketController {

    @Autowired
    private UserService userService;

    @Autowired
    private ConversationService conversationService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageService messageService;

    @Autowired
    private MailService mailService;

    @Autowired
    private SimpMessagingTemplate template;




    /*
    @MessageMapping("/conversation/{id}")
    @SendTo("conversation/{id}")
    public MessageDto addMessage(@Payload MessageDto dto, Principal user) {
        return  dto;
    }
    */

    @Transactional
    @MessageMapping("/ws-conversation-endpoint/{id}")
    public void addMessage(@DestinationVariable String id, MessageDto dto, SimpMessageHeaderAccessor headerAccessor) throws Exception {

        User sender = (User) headerAccessor.getSessionAttributes().get("connectedUser");
        Locale locale = (Locale) headerAccessor.getSessionAttributes().get("locale");


        Conversation c = conversationService.returnConversationOrThrowException(dto.getConversation().getId());
        User target = MessengerUtils.getOtherUser(c, sender);
        //TODO : see how handle exceptions in websocket
        userService.throwExceptionIfBlocked(sender, target);

        target.setLastMessageBy(sender);
        userRepository.save(target);

        Conversation conversation = conversationService.updateConversation(c, sender, dto);
        System.err.println("ws : " + dto.getSendDate());
        // on renvoit la conversation aux deux utilisateurs qui ont souscrit à la websocket.
        this.template.convertAndSend("/ws-user-broker/conversations/"+ conversation.getUserOne().getId(), ConversationMapper.toDto(conversation, conversation.getUserOne()));
        this.template.convertAndSend("/ws-user-broker/conversations/"+ conversation.getUserTwo().getId(), ConversationMapper.toDto(conversation, conversation.getUserTwo()));


        Message message = MessageMapper.fromDto(dto);
        message.setType(MessageType.TEXT);
        message = messageService.saveMessage(message, c.getUserOne(), c.getUserTwo());

        // send email if sender is not last sender
        if (!target.getLastMessageBy().equals(sender)) {
            mailService.sendAsync(() -> mailService.sendMessageNotification(locale, MessengerUtils.getOtherUser(c, sender), sender));
        }
        System.out.println("message = " + message);
        this.template.convertAndSend("/ws-conversation-broker/conversation/" + id, MessageMapper.toDto(message));
    }

    @MessageMapping("/hello/{id}")
    @SendTo("/topic/greetings/{id}")
    public Greeting greeting(@DestinationVariable String id, HelloMessage message, SimpMessageHeaderAccessor headerAccessor) throws Exception {
        //System.out.println("messageHeaders = " + messageHeaders.get("ok"));
        System.out.println("headerAccessor.getSessionAttributes().get(\"handshake\"); = " + headerAccessor.getSessionAttributes().get("handshake"));
        return new Greeting("Hello, " + message.getName() + "!");
    }
}
