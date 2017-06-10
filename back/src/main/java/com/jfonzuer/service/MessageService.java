package com.jfonzuer.service;

import com.jfonzuer.dto.MessageDto;
import com.jfonzuer.dto.mapper.MessageMapper;
import com.jfonzuer.entities.Conversation;
import com.jfonzuer.entities.Message;
import com.jfonzuer.entities.MessageType;
import com.jfonzuer.entities.User;
import com.jfonzuer.repository.MessageRepository;
import com.jfonzuer.storage.StorageService;
import com.jfonzuer.utils.MessengerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by pgm on 08/01/17.
 */
@Service
public class MessageService {
    
    private final MessageRepository messageRepository;
    private final StorageService storageService;

    @Autowired
    public MessageService(MessageRepository messageRepository, StorageService storageService) {
        this.messageRepository = messageRepository;
        this.storageService = storageService;
    }

    public void deleteByUserOne(Conversation conversation) {
        List<Message> messages = messageRepository.findByConversation(conversation);
        for (Message message : messages) {
            message.setDeletedByUserOne(true);
            deleteOrUpdate(message);
        }
    }

    public void deleteByUserTwo(Conversation conversation) {
        List<Message> messages = messageRepository.findByConversation(conversation);
        for (Message message : messages) {
            message.setDeletedByUserTwo(true);
            deleteOrUpdate(message);
        }
    }

    public Message saveMessage(Message message, User userOne, User userTwo) {
        message.setUserOne(userOne);
        message.setUserTwo(userTwo);
        message.setDeletedByUserOne(false);
        message.setDeletedByUserTwo(false);
        message.setSentDateTime(LocalDateTime.now());
        return messageRepository.save(message);
    }

    public Message saveImage(MessageDto dto, Conversation conversation, User user) {
        Message message = MessageMapper.fromDto(dto);
        message.setType(MessageType.IMAGE);
        message.setConversation(conversation);
        message.setSource(user);
        return saveMessage(message, conversation.getUserOne(), conversation.getUserTwo());
    }

    private void deleteOrUpdate(Message message) {
        if (MessengerUtils.isMessageDeletedByBothUsers(message)) {
            messageRepository.delete(message);
            if (message.getType().equals(MessageType.IMAGE)) {
                storageService.delete(message.getUrl());
            }
        } else {
            messageRepository.save(message);
        }
    }

    public Page<MessageDto> getByConversationAndUser(Conversation c, User u, Pageable p) {
        return messageRepository
                .findByConversationAndUserOneAndIsDeletedByUserOneOrConversationAndUserTwoAndIsDeletedByUserTwoOrderByIdAsc(c, u, false, c, u, false, p)
                .map(m -> MessageMapper.toDto(m));
    }
    
    
}
