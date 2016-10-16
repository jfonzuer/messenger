package com.jfonzuer.utils;

import com.jfonzuer.dto.MessageDto;
import com.jfonzuer.entities.Conversation;
import com.jfonzuer.entities.User;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by pgm on 21/09/16.
 */
public class MessengerUtils {

    private final static int MAX_CHARACTER_OF_PREVIEW = 50;

    public static String getPreviewFromMessage(MessageDto messageDto) {
        return messageDto.getContent().length() > 50 ? messageDto.getContent().substring(0, MAX_CHARACTER_OF_PREVIEW - 1) + "..." : messageDto.getContent();
    }

    public static boolean isUserOne(User user, Conversation conversation) {
        return (conversation.getUserOne().equals(user));
    }
    public static boolean isUserTwo(User user, Conversation conversation) {
        return (conversation.getUserTwo().equals(user));
    }

    public static User getOtherUser(Conversation c, User u) {
        return Stream.of(c.getUserOne(), c.getUserTwo()).filter(a -> !a.equals(u)).collect(Collectors.toList()).get(0);
    }

    public static boolean isDeletedByBothUsers(Conversation conversation) {
        return conversation.getDeletedByUserOne() && conversation.getDeletedByUserTwo();
    }


}
