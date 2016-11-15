package com.jfonzuer.utils;

import com.jfonzuer.dto.MessageDto;
import com.jfonzuer.entities.Conversation;
import com.jfonzuer.entities.User;
import com.jfonzuer.entities.UserType;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by pgm on 21/09/16.
 */
public class MessengerUtils {

    private final static int MAX_CHARACTER_OF_PREVIEW = 50;
    public final static String DOMINA = "Dominatrice";
    public final static String SUBMISSIVE = "Soumis";
    public final static Long DOMINA_ID = 1L;
    public final static Long SUBMISSIVE_ID = 2L;

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

    public static boolean isDomina(User user) {
        return user.getType().getName() == DOMINA;
    }

    public static boolean isSub(User user) {
        return user.getType().getName() == SUBMISSIVE;
    }

    public static UserType getOtherType(User user) {
        return user.getType().getName() == DOMINA ? new UserType.UserTypeBuilder().setId(SUBMISSIVE_ID).createUserType() : new UserType.UserTypeBuilder().setId(DOMINA_ID).createUserType();
    }
}
