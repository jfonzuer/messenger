package com.jfonzuer.websocket;

import com.jfonzuer.entities.Conversation;
import com.jfonzuer.entities.User;
import com.jfonzuer.exception.UnauthorizedException;
import com.jfonzuer.repository.ConversationRepository;
import com.jfonzuer.service.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;

import java.security.Principal;

/**
 * Created by pgm on 05/02/17.
 */
public class ConversationSubscribeInterceptor extends ChannelInterceptorAdapter {

    @Autowired
    private ConversationService conversationService;

    @Autowired
    private ConversationRepository conversationRepository;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);

        if (StompCommand.SUBSCRIBE.equals(headerAccessor.getCommand()) || StompCommand.MESSAGE.equals(headerAccessor.getCommand())) {
            String destination = (String) headerAccessor.getHeader("simpDestination");

            // on récupère le paramètre conversationId
            Long conversationId = Long.parseLong(String.valueOf(destination.charAt(destination.length() - 1)));
            System.out.println("conversationId = " + conversationId);
            Conversation conversation = conversationRepository.findOne(conversationId);
            User connectedUser = (User) headerAccessor.getSessionAttributes().get("connectedUser");

            if (conversation == null) {
                throw new ResourceNotFoundException("La conversation n'existe pas");
            }
            if (!conversationService.isUserPartOfConversation(conversation, connectedUser)) {
                throw new UnauthorizedException("L'utilisateur ne fait pas partie de la discussion");
            }
        }

        return message;
    }
}
