package com.jfonzuer.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jfonzuer.security.JwtUser;

/**
 * Created by pgm on 20/09/16.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MessageDto {

    private Long id;
    private JwtUser source;
    private String content;
    private String sendDate;
    private ConversationDto conversation;
    private String url;
    private String type;

    public MessageDto() {
    }

    public MessageDto(Long id, JwtUser source, String content, String sendDate, ConversationDto conversation, String url, String type) {
        this.id = id;
        this.source = source;
        this.content = content;
        this.sendDate = sendDate;
        this.conversation = conversation;
        this.url = url;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public JwtUser getSource() {
        return source;
    }

    public void setSource(JwtUser source) {
        this.source = source;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    public ConversationDto getConversation() {
        return conversation;
    }

    public void setConversation(ConversationDto conversation) {
        this.conversation = conversation;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "MessageDto{" +
                "id='" + id + '\'' +
                ", source=" + source +
                ", content='" + content + '\'' +
                ", sendDate='" + sendDate + '\'' +
                ", conversation=" + conversation +
                '}';
    }

    public static class MessageDtoBuilder {
        private Long id;
        private JwtUser source;
        private String content;
        private String sendDate;
        private ConversationDto conversation;
        private String url;
        private String type;

        public MessageDtoBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public MessageDtoBuilder setSource(JwtUser source) {
            this.source = source;
            return this;
        }

        public MessageDtoBuilder setContent(String content) {
            this.content = content;
            return this;
        }

        public MessageDtoBuilder setSendDate(String sendDate) {
            this.sendDate = sendDate;
            return this;
        }

        public MessageDtoBuilder setConversation(ConversationDto conversation) {
            this.conversation = conversation;
            return this;
        }

        public MessageDtoBuilder setUrl(String url) {
            this.url = url;
            return this;
        }

        public MessageDtoBuilder setType(String type) {
            this.type = type;
            return this;
        }

        public MessageDto createMessageDto() {
            return new MessageDto(id, source, content, sendDate, conversation, url, type);
        }
    }
}
