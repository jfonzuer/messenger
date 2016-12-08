package com.jfonzuer.dto.mapper;

import com.jfonzuer.dto.ConversationDto;
import com.jfonzuer.entities.Conversation;
import com.jfonzuer.entities.User;
import com.jfonzuer.utils.MessengerUtils;

import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by pgm on 20/09/16.
 */
public class ConversationMapper {

    public static Conversation fromDto(ConversationDto dto) {
        return dto != null ? new Conversation.ConversationBuilder()
                .setId(dto.getId())
                .createConversation() : null;
    }

    public static ConversationDto toDto(Conversation c, User u) {
        System.out.println("------------------------------------------");
        System.out.println("c.getReadByUserTwo() = " + c.getReadByUserTwo());
        System.out.println("(MessengerUtils.isUserOne(u,c) ? c.getReadByUserOne() : c.getReadByUserTwo()).toString() = " + (MessengerUtils.isUserOne(u,c) ? c.getReadByUserOne() : c.getReadByUserTwo()).toString());
        System.out.println("MessengerUtils.isUserOne(c, u) = " + MessengerUtils.isUserOne(u,c));

        return c != null ? new ConversationDto.ConversationDtoBuilder()
                .setId(c.getId())
                .setReadByUserOne(MessengerUtils.isUserOne(u,c) ? c.getReadByUserOne() : c.getReadByUserTwo())
                .setReadByUserTwo(MessengerUtils.isUserOne(u,c) ? c.getReadByUserTwo() : c.getReadByUserOne())
                .setUserOne(u == null ? null : UserMapper.toLightDto(u))
                .setUserTwo(UserMapper.toLightDto(MessengerUtils.getOtherUser(c, u)))
                .setPreview(c.getPreview() == null ? null : c.getPreview())
                .createConversationDto() : null;
    }

    // mapper minimaliste
    public static ConversationDto toDto(Conversation c) {

        return c != null ? new ConversationDto.ConversationDtoBuilder()
                .setId(c.getId())
                .createConversationDto() : null;
    }
}
