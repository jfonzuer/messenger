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

    public MessageDto() {
    }

    public MessageDto(Long id, JwtUser source, String content, String sendDate, ConversationDto conversation) {
        this.id = id;
        this.source = source;
        this.content = content;
        this.sendDate = sendDate;
        this.conversation = conversation;
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

    public static class Builder {
        private Long id;
        private JwtUser source;
        private String content;
        private String sendDate;
        private ConversationDto conversation;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }
        public Builder source(JwtUser source) {
            this.source = source;
            return this;
        }
        public Builder content(String content) {
            this.content = content;
            return this;
        }
        public Builder sendDate(String sendDate) {
            this.sendDate = sendDate;
            return this;
        }
        public Builder conversation(ConversationDto conversation) {
            this.conversation = conversation;
            return this;
        }

        public MessageDto build() {
            return new MessageDto(id, source, content, sendDate, conversation);
        }
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
}
