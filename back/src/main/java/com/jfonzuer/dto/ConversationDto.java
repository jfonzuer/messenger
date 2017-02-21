package com.jfonzuer.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by pgm on 20/09/16.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ConversationDto {

    private Long id;
    private String preview;
    private UserDto userOne;
    private UserDto userTwo;
    private Boolean readByUserOne;
    private Boolean readByUserTwo;

    public ConversationDto() {
    }

    public ConversationDto(Long id, String preview, UserDto userOne, UserDto userTwo, Boolean readByUserOne, Boolean readByUserTwo) {
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

    public UserDto getUserOne() {
        return userOne;
    }

    public void setUserOne(UserDto userOne) {
        this.userOne = userOne;
    }

    public UserDto getUserTwo() {
        return userTwo;
    }

    public void setUserTwo(UserDto userTwo) {
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
        private UserDto userOne;
        private UserDto userTwo;
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

        public ConversationDtoBuilder setUserOne(UserDto userOne) {
            this.userOne = userOne;
            return this;
        }

        public ConversationDtoBuilder setUserTwo(UserDto userTwo) {
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
