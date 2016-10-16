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
import com.jfonzuer.utils.MessengerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

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

    private static final Logger LOGGER = LoggerFactory.getLogger(ConversationController.class);

    @Autowired
    public ConversationController(ConversationRepository conversationRepository, UserRepository userRepository, MessageRepository messageRepository) {
        this.conversationRepository = conversationRepository;
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Page<ConversationDto> getAll(Pageable p) {
        //TODO : set current user manually
        User user = userRepository.findOne(1L);
        return conversationRepository.findAllByUserOneAndIsDeletedByUserOneOrUserTwoAndIsDeletedByUserTwoOrderByLastModifiedDesc(user, false, user, false, p).map(c -> ConversationMapper.toDto(c, user));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ConversationDto add(@RequestBody UserMessageDto dto) {

        System.out.println("dto---------------------------------------------------- = " + dto);
        LOGGER.info("In conversation controller post : " + dto);

        MessageDto messageDto = dto.getMessage();

        //TODO : set current user manually
        User userOne = userRepository.findOne(1L);
        User userTwo = UserMapper.fromDto(dto.getUser());

        if (userOne.equals(userTwo)) {
            throw new IllegalArgumentException();
        }

        //Conversation conversation = conversationRepository.save(new Conversation(MessengerUtils.getPreviewFromMessage(messageDto), false, userOne, userTwo));
        Conversation conversation = conversationRepository.save(new Conversation(MessengerUtils.getPreviewFromMessage(messageDto), true, false, LocalDateTime.now(), false, false, userOne, userTwo));

        // ajout du message
        Message message = MessageMapper.fromDto(messageDto);
        message.setSentDateTime(LocalDateTime.now());
        message.setConversation(conversation);
        messageRepository.save(message);

        return ConversationMapper.toDto(conversation);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ConversationDto getConversationBetweenCurrentUserAndSpecifiedUser(@PathVariable Long id) {
        LOGGER.info(" in getConversationBetweenCurrentUserAndSpecifiedUser ");

        //TODO : set current user manually
        User currentUser = userRepository.findOne(1L);
        User specifiedUser = userRepository.findOne(id);

//        if (specifiedUser == null) {
//            throw new ResourceNotFoundException();
//        }

        List<Conversation> list = conversationRepository.findTop1ByUserOneAndUserTwoOrUserTwoAndUserOne(currentUser, specifiedUser, currentUser, specifiedUser);
        System.out.println("list = " + list);
        return (list.size() == 0) ? null : ConversationMapper.toDto(list.get(0), currentUser);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteConversation(@PathVariable Long id) {

        //TODO : set current user manually
        User currentUser = userRepository.findOne(1L);
        Conversation conversation = conversationRepository.findTop1ByIdAndUserOneOrUserTwo(id, currentUser, currentUser).get(0);

        if (MessengerUtils.isUserOne(currentUser, conversation)) {
            conversation.setDeletedByUserOne(true);
        }
        else {
            conversation.setDeletedByUserTwo(true);
        }

        if (MessengerUtils.isDeletedByBothUsers(conversation)) {
            conversationRepository.delete(conversation);
        }
        else {
            conversationRepository.save(conversation);
        }
    }
}
