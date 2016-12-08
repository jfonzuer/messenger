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
    private JwtUser userOne;
    private JwtUser userTwo;
    private Boolean readByUserOne;
    private Boolean readByUserTwo;

    public ConversationDto() {
    }

    public ConversationDto(Long id, String preview, JwtUser userOne, JwtUser userTwo, Boolean readByUserOne, Boolean readByUserTwo) {
        this.id = id;
        this.preview = preview;
        this.userOne = userOne;
        this.userTwo = userTwo;
        this.readByUserOne = readByUserOne;
        this.readByUserTwo = readByUserTwo;
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

    public Boolean getReadByUserOne() {
        return readByUserOne;
    }

    public void setReadByUserOne(Boolean readByUserOne) {
        this.readByUserOne = readByUserOne;
    }

    public Boolean getReadByUserTwo() {
        return readByUserTwo;
    }

    public void setReadByUserTwo(Boolean readByUserTwo) {
        this.readByUserTwo = readByUserTwo;
    }

    @Override
    public String toString() {
        return "ConversationDto{" +
                "id=" + id +
                ", preview='" + preview + '\'' +
                ", userOne=" + userOne +
                ", userTwo=" + userTwo +
                ", readByUserOne=" + readByUserOne +
                ", readByUserTwo=" + readByUserTwo +
                '}';
    }


    public static class ConversationDtoBuilder {
        private Long id;
        private String preview;
        private JwtUser userOne;
        private JwtUser userTwo;
        private Boolean readByUserOne;
        private Boolean readByUserTwo;

        public ConversationDtoBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public ConversationDtoBuilder setPreview(String preview) {
            this.preview = preview;
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

        public ConversationDtoBuilder setReadByUserOne(Boolean readByUserOne) {
            this.readByUserOne = readByUserOne;
            return this;
        }

        public ConversationDtoBuilder setReadByUserTwo(Boolean readByUserTwo) {
            this.readByUserTwo = readByUserTwo;
            return this;
        }

        public ConversationDto createConversationDto() {
            return new ConversationDto(id, preview, userOne, userTwo, readByUserOne, readByUserTwo);
        }
    }
}
