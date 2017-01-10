package com.jfonzuer.dto.mapper;

import com.jfonzuer.dto.MessageDto;
import com.jfonzuer.entities.Message;
import com.jfonzuer.entities.MessageType;
import com.jfonzuer.security.JwtUser;

/**
 * Created by pgm on 20/09/16.
 */
public class MessageMapper {

    public static Message fromDto(MessageDto dto) {
        return dto != null ? new Message.MessageBuilder()
                .setId(dto.getId() == null ? null : dto.getId())
                .setContent(dto.getContent())
                .setSentDateTime(dto.getSendDate() == null ? null : DateMapper.toLocalDateTime(dto.getSendDate()))
                .setSource(UserMapper.fromDto(dto.getSource()))
                .setConversation(ConversationMapper.fromDto(dto.getConversation()))
                .createMessage() : null;
    }

    public static MessageDto toDto(Message message) {
        return message != null ? new MessageDto.MessageDtoBuilder()
                .setId(message.getId())
                .setConversation(message.getConversation() == null ? null : ConversationMapper.toDto(message.getConversation()))
                .setContent(message.getContent())
                .setSendDate(message.getSentDateTime().toString())
                .setSource(UserMapper.toLightDto(message.getSource()))
                .setUrl(message.getUrl())
                .setType(message.getType().toString())
                .createMessageDto() : null;
    }
}
