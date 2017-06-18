package com.jfonzuer.dto;

import java.util.List;

/**
 * Created by pgm on 16/06/17.
 */
public class ConversationMessageDto {

    private ConversationDto conversation;
    private List<MessageDto> messages;

    public ConversationMessageDto() {
    }

    public ConversationMessageDto(ConversationDto conversation, List<MessageDto> messages) {
        this.conversation = conversation;
        this.messages = messages;
    }

    public ConversationDto getConversation() {
        return conversation;
    }

    public void setConversation(ConversationDto conversation) {
        this.conversation = conversation;
    }

    public List<MessageDto> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageDto> messages) {
        this.messages = messages;
    }

    public static final class Builder {
        private ConversationDto conversation;
        private List<MessageDto> messages;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder withConversation(ConversationDto conversation) {
            this.conversation = conversation;
            return this;
        }

        public Builder withMessages(List<MessageDto> messages) {
            this.messages = messages;
            return this;
        }

        public ConversationMessageDto build() {
            ConversationMessageDto conversationMessageDto = new ConversationMessageDto();
            conversationMessageDto.setConversation(conversation);
            conversationMessageDto.setMessages(messages);
            return conversationMessageDto;
        }
    }
}
