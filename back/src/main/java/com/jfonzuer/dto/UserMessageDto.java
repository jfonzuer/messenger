package com.jfonzuer.dto;

/**
 * Created by pgm on 02/10/16.
 */
public class UserMessageDto {

    private MessageDto message;
    private UserDto user;

    public UserMessageDto() {
    }

    public MessageDto getMessage() {
        return message;
    }

    public void setMessage(MessageDto message) {
        this.message = message;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserMessageDto{" +
                "message=" + message +
                ", user=" + user +
                '}';
    }
}
