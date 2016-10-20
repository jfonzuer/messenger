package com.jfonzuer.dto;

import com.jfonzuer.entities.User;
import com.jfonzuer.security.JwtUser;

/**
 * Created by pgm on 02/10/16.
 */
public class UserMessageDto {

    private MessageDto message;
    private JwtUser user;

    public UserMessageDto() {
    }

    public MessageDto getMessage() {
        return message;
    }

    public void setMessage(MessageDto message) {
        this.message = message;
    }

    public JwtUser getUser() {
        return user;
    }

    public void setUser(JwtUser user) {
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
