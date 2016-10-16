package com.jfonzuer.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jfonzuer.security.JwtUser;

/**
 * Created by pgm on 20/09/16.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ConversationDto {

    private Long id;
    private String preview;
    private boolean isRead;
    private JwtUser userOne;
    private JwtUser userTwo;

    public ConversationDto() {
    }

    public ConversationDto(Long id, String preview, boolean isRead, JwtUser userOne, JwtUser userTwo) {
        this.id = id;
        this.preview = preview;
        this.isRead = isRead;
        this.userOne = userOne;
        this.userTwo = userTwo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean isRead) {
        isRead = isRead;
    }

    public JwtUser getUserOne() {
        return userOne;
    }

    public void setUserOne(JwtUser userOne) {
        this.userOne = userOne;
    }

    public JwtUser getUserTwo() {
        return userTwo;
    }

    public void setUserTwo(JwtUser userTwo) {
        this.userTwo = userTwo;
    }

    @Override
    public String toString() {
        return "ConversationDto{" +
                "id='" + id + '\'' +
                ", preview='" + preview + '\'' +
                ", isRead='" + isRead + '\'' +
                ", userOne=" + userOne +
                ", userTwo=" + userTwo +
                '}';
    }

    public static class ConversationDtoBuilder {
        private Long id;
        private String preview;
        private boolean isRead;
        private JwtUser userOne;
        private JwtUser userTwo;

        public ConversationDtoBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public ConversationDtoBuilder setPreview(String preview) {
            this.preview = preview;
            return this;
        }

        public ConversationDtoBuilder setIsRead(boolean isRead) {
            this.isRead = isRead;
            return this;
        }

        public ConversationDtoBuilder setUserOne(JwtUser userOne) {
            this.userOne = userOne;
            return this;
        }

        public ConversationDtoBuilder setUserTwo(JwtUser userTwo) {
            this.userTwo = userTwo;
            return this;
        }

        public ConversationDto createConversationDto() {
            return new ConversationDto(id, preview, isRead, userOne, userTwo);
        }
    }
}
