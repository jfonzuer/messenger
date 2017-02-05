package com.jfonzuer.service;

import com.jfonzuer.dto.ConversationDto;
import com.jfonzuer.dto.MessageDto;
import com.jfonzuer.dto.mapper.MessageMapper;
import com.jfonzuer.entities.Conversation;
import com.jfonzuer.entities.Message;
import com.jfonzuer.entities.MessageType;
import com.jfonzuer.entities.User;
import com.jfonzuer.repository.ConversationRepository;
import com.jfonzuer.repository.MessageRepository;
import com.jfonzuer.repository.UserRepository;
import com.jfonzuer.utils.MessengerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by pgm on 08/01/17.
 */
@Service
public class ConversationService {

    private final ConversationRepository conversationRepository;
    private final MessageService messageService;
    private final UserRepository userRepository;

    @Autowired
    public ConversationService(ConversationRepository conversationRepository, MessageService messageService, UserRepository userRepository) {
        this.conversationRepository = conversationRepository;
        this.messageService = messageService;
        this.userRepository = userRepository;
    }

    public Boolean isUserPartOfConversation(Conversation c, User u) {
        return c.getUserOne().equals(u) || c.getUserTwo().equals(u);
    }

    public Conversation getConversationOrCreateOne(User currentUser, User specifiedUser) {
        Conversation conversation = conversationRepository.findByUserOneAndUserTwoOrUserTwoAndUserOne(currentUser, specifiedUser, currentUser, specifiedUser);
        System.out.println("conversation = " + conversation);
        // si la conversation n'existe pas on la crée
        if (conversation == null) {
            conversation = new Conversation.ConversationBuilder()
                    .setPreview("")
                    .setLastModified(LocalDateTime.now())
                    .setUserOne(currentUser)
                    .setUserTwo(specifiedUser)
                    .setIsReadByUserOne(true)
                    .setIsReadByUserTwo(false)
                    .setIsDeletedByUserOne(false)
                    .setIsDeletedByUserTwo(false)
                    .createConversation();
        }
        return conversation;
    }

    public Conversation createConversationAndAddMessage(User userOne, User userTwo, MessageDto messageDto) {
        Conversation conversation = conversationRepository.save(new Conversation(MessengerUtils.getPreviewFromMessage(messageDto), true, false, LocalDateTime.now(), false, false, userOne, userTwo));
        Message message = MessageMapper.fromDto(messageDto);
        message.setConversation(conversation);
        message.setType(MessageType.TEXT);
        messageService.saveMessage(message, userOne, userTwo);
        return conversation;
    }

    public void deleteByIdAndUser(Long conversationId, User user) {
        Conversation conversation = conversationRepository.findByIdAndUserOneOrUserTwo(conversationId, user, user);

        if (MessengerUtils.isUserOne(user, conversation)) {
            conversation.setDeletedByUserOne(true);
            messageService.deleteByUserOne(conversation);
        } else {
            conversation.setDeletedByUserTwo(true);
            messageService.deleteByUserTwo(conversation);
        }

        if (MessengerUtils.isDeletedByBothUsers(conversation)) {
            conversationRepository.delete(conversation);
        } else {
            conversationRepository.save(conversation);
        }
    }

    public void updateConversationIsRead(Conversation c, User user) {
        if (MessengerUtils.isUserOne(user, c) && c.getReadByUserOne().equals(Boolean.FALSE)) {
            c.setReadByUserOne(true);
            conversationRepository.save(c);
        }
        else if (MessengerUtils.isUserTwo(user, c) && c.getReadByUserTwo().equals(Boolean.FALSE)) {
            c.setReadByUserTwo(true);
            conversationRepository.save(c);
        }
    }

    public Conversation getConversationByIdAndUser(Long id, User currentUser) {
        User specifiedUser = userRepository.findOne(id);

        if (specifiedUser == null) {
            throw  new ResourceNotFoundException();
        }
        return conversationRepository.findByUserOneAndUserTwoOrUserTwoAndUserOne(currentUser, specifiedUser, currentUser, specifiedUser);
    }

    public Conversation returnConversationOrThrowException(Long id) {
        Conversation conversation = conversationRepository.findOne(id);
        if (conversation == null) {
            throw new ResourceNotFoundException("La conversation n'a pu être trouvée");
        }
        return conversation;
    }

    public void updateConversation(Conversation conversation, User sender, MessageDto dto) {
        conversation.setLastModified(LocalDateTime.now());
        conversation.setPreview(MessengerUtils.getPreviewFromMessage(dto));
        MessengerUtils.setConversationUnread(conversation, sender);
        MessengerUtils.setConversationDeleted(conversation, sender);
        conversationRepository.save(conversation);
    }
}
