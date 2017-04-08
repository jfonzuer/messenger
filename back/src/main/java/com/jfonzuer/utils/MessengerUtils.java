package com.jfonzuer.utils;

import com.jfonzuer.dto.MessageDto;
import com.jfonzuer.entities.Conversation;
import com.jfonzuer.entities.Message;
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

    public final static Long FRANCE_ID = 1L;
    public final static Long BELGIUM_ID = 2L;
    public final static Long LUX_ID = 3L;
    public final static Long SWISS_ID = 4L;

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

    public static boolean isMessageDeletedByBothUsers(Message message) {
        return message.getDeletedByUserOne() && message.getDeletedByUserTwo();
    }

    public static boolean isDomina(User user) {
        return user.getType().getId().equals(DOMINA_ID);
    }

    public static boolean isSub(User user) {
        return user.getType().getId().equals(SUBMISSIVE_ID);
    }

    public static UserType getOtherType(User user) {
        return user.getType().getName().equals(DOMINA) ? UserType.Builder.anUserType().withId(SUBMISSIVE_ID).build() : UserType.Builder.anUserType().withId(DOMINA_ID).build();
    }

    public static void setConversationUnread(Conversation c, User u) {
        if (isUserOne(u, c) && c.getReadByUserTwo().equals(Boolean.TRUE)) {
            c.setReadByUserTwo(false);
            c.setReadByUserOne(true);
        }
        else if (isUserTwo(u, c) && c.getReadByUserOne().equals(Boolean.TRUE)) {
            c.setReadByUserOne(false);
            c.setReadByUserOne(true);
        }
    }

    public static void setConversationDeleted(Conversation c, User sender) {
        if (MessengerUtils.isUserOne(sender, c)) {
            c.setDeletedByUserOne(false);
        }
        else if (MessengerUtils.isUserTwo(sender, c)) {
            c.setDeletedByUserTwo(false);
        }
    }
}
