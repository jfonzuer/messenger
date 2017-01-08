package com.jfonzuer.controllers;

import com.jfonzuer.dto.ConversationDto;
import com.jfonzuer.dto.MessageDto;
import com.jfonzuer.dto.UserMessageDto;
import com.jfonzuer.dto.mapper.ConversationMapper;
import com.jfonzuer.dto.mapper.MessageMapper;
import com.jfonzuer.dto.mapper.UserMapper;
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
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * Created by pgm on 20/09/16.
 */
@RestController
@RequestMapping("/conversations")
@CrossOrigin(origins = "*", maxAge = 3600)
//@PreAuthorize("hasRole('USER')")
public class ConversationController {

    private final ConversationRepository conversationRepository;
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    private final UserService userService;
    private final MailService mailService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ConversationController.class);

    @Autowired
    public ConversationController(ConversationRepository conversationRepository, UserRepository userRepository, MessageRepository messageRepository, UserService userService, MailService mailService) {
        this.conversationRepository = conversationRepository;
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
        this.userService = userService;
        this.mailService = mailService;
    }


    @RequestMapping(method = RequestMethod.GET)
    public Page<ConversationDto> getAll(HttpServletRequest request, Pageable p) {
        User user = userService.getUserFromToken(request);
        return conversationRepository.getAllConversations(user, p).map(c -> ConversationMapper.toDto(c, user));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ConversationDto add(HttpServletRequest request, @RequestBody UserMessageDto dto) {

        MessageDto messageDto = dto.getMessage();
        User userOne = userService.getUserFromToken(request);
        User userTwo = UserMapper.fromDto(dto.getUser());

        if (userOne.equals(userTwo)) {
            throw new IllegalArgumentException();
        }
        Conversation conversation = conversationRepository.findByUserOneAndUserTwoOrUserTwoAndUserOne(userOne, userTwo, userOne, userTwo);

        // ajout du message
        Message message = MessageMapper.fromDto(messageDto);
        message.setSentDateTime(LocalDateTime.now());


        if (conversation == null) {
            conversation = conversationRepository.save(new Conversation.ConversationBuilder()
                    .setPreview(MessengerUtils.getPreviewFromMessage(messageDto))
                    .setIsReadByUserOne(true).setIsReadByUserTwo(false)
                    .setLastModified(LocalDateTime.now())
                    .setUserOneCursor(0L)
                    .setUserTwoCursor(0L)
                    // set last message id greater than cursors
                    .setLastMessageId(1L)
                    .setUserOne(userOne)
                    .setUserTwo(userTwo)
                    .createConversation());
            message.setConversation(conversation);
            messageRepository.save(message);
        }
        // si la conversation existe (ex: conversation supprimée puis relancée)
        else {
            conversation.setPreview(MessengerUtils.getPreviewFromMessage(messageDto));
            MessengerUtils.setConversationUnread(conversation, userOne);
            conversation.setLastModified(LocalDateTime.now());
            message.setConversation(conversation);
            message = messageRepository.save(message);
            conversation.setLastMessageId(message.getId());
            conversationRepository.save(conversation);
        }

        mailService.sendAsync(() -> mailService.sendMessageNotification(request.getLocale(), userTwo, userOne));
        return ConversationMapper.toDto(conversation, userOne);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ConversationDto getConversationBetweenCurrentUserAndSpecifiedUser(HttpServletRequest request, @PathVariable Long id) {
        LOGGER.info(" in getConversationBetweenCurrentUserAndSpecifiedUser ");
        User currentUser = userService.getUserFromToken(request);
        User specifiedUser = userRepository.findOne(id);

        Conversation conversation = conversationRepository.findByUserOneAndUserTwoOrUserTwoAndUserOne(currentUser, specifiedUser, currentUser, specifiedUser);
        System.out.println("conversation = " + conversation);
        // si la conversation n'existe pas on la crée
        if (conversation == null) {
            conversation = new Conversation.ConversationBuilder()
                    .setPreview("conversation 1")
                    .setLastModified(LocalDateTime.now())
                    .setUserOne(currentUser)
                    .setUserTwo(specifiedUser)
                    .setIsReadByUserOne(true)
                    .setIsReadByUserTwo(false)
                    .createConversation();
        }

        return ConversationMapper.toDto(conversation, currentUser);
    }

    @RequestMapping(value = "/{conversationId}/{lastMessageId}", method = RequestMethod.DELETE)
    public void deleteConversation(HttpServletRequest request, @PathVariable Long conversationId, @PathVariable Long lastMessageId) {

        User currentUser = userService.getUserFromToken(request);
        Conversation conversation = conversationRepository.findTop1ByIdAndUserOneOrUserTwo(conversationId, currentUser, currentUser).get(0);

        if (MessengerUtils.isUserOne(currentUser, conversation)) {
            conversation.setUserOneCursor(lastMessageId);
        }
        else {
            conversation.setUserTwoCursor(lastMessageId);
        }
        // on trie les cursors par ordre croissant
        long[] cursors = { conversation.getUserOneCursor(), conversation.getUserTwoCursor() };
        Arrays.sort(cursors);
        // on supprime tous les messages dont l'id est inférieur au curseur minimum.
        messageRepository.deleteByConversationAndIdLessThanEqual(conversation, cursors[0]);

        conversationRepository.save(conversation);
    }

    @RequestMapping(value = "/unread", method = RequestMethod.GET)
    public Long getUnreadNumerConversations(HttpServletRequest request) {

        User user = userService.getUserFromToken(request);

        //User user = this.userService.getUserFromToken(request);
        return this.conversationRepository.countByUserOneAndIsReadByUserOne(user, false) +  this.conversationRepository.countByUserTwoAndIsReadByUserTwo(user, false);
    }
}
