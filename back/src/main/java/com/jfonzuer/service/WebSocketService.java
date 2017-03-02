package com.jfonzuer.service;

import com.jfonzuer.dto.mapper.ConversationMapper;
import com.jfonzuer.entities.Conversation;
import com.jfonzuer.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * Created by pgm on 28/02/17.
 */
@Service
public class WebSocketService {

    @Value("${websocket.conversations.endpoint}")
    private String conversationEndpoint;

    private final SimpMessagingTemplate template;

    @Autowired
    public WebSocketService(SimpMessagingTemplate template) {
        this.template = template;
    }

    /**
     * Méthode permettant de renvoyer la conversation aux utilisateurs qui ont souscris à la websocket
     * @param conversation
     */
    public void sendToConversationsUsers(Conversation conversation) {
        this.template.convertAndSend(conversationEndpoint + conversation.getUserOne().getId(), ConversationMapper.toDto(conversation, conversation.getUserOne()));
        this.template.convertAndSend(conversationEndpoint + conversation.getUserTwo().getId(), ConversationMapper.toDto(conversation, conversation.getUserTwo()));
    }
}