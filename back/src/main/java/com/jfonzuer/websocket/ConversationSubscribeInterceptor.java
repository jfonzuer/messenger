package com.jfonzuer.websocket;

import com.jfonzuer.entities.Conversation;
import com.jfonzuer.entities.User;
import com.jfonzuer.exception.UnauthorizedException;
import com.jfonzuer.repository.ConversationRepository;
import com.jfonzuer.repository.UserRepository;
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

    @Autowired
    private UserRepository userRepository;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);

        if (StompCommand.SUBSCRIBE.equals(headerAccessor.getCommand()) || StompCommand.MESSAGE.equals(headerAccessor.getCommand())) {
            String destination = (String) headerAccessor.getHeader("simpDestination");
            User connectedUser = (User) headerAccessor.getSessionAttributes().get("connectedUser");

            // on récupère le paramètre id
            Long id = Long.parseLong(String.valueOf(destination.charAt(destination.length() - 1)));

            if (destination.contains("ws-conversation-broker")) {
                System.out.println("conversationId = " + id);
                Conversation conversation = conversationRepository.findOne(id);

                if (conversation == null) {
                    throw new ResourceNotFoundException("La conversation n'existe pas");
                }
                if (!conversationService.isUserPartOfConversation(conversation, connectedUser)) {
                    throw new UnauthorizedException("L'utilisateur ne fait pas partie de la discussion");
                }
            } else if (destination.contains("ws-user-broker")) {
                System.out.println("userId = " + id);
                User user = userRepository.findOne(id);
                if (user == null) {
                    throw new ResourceNotFoundException("L'utilisateur n'existe pas");
                }
                if (!user.equals(connectedUser)) {
                    throw new UnauthorizedException("Vous n'avez pas les droits.");
                }

            }


        }

        return message;
    }
}
