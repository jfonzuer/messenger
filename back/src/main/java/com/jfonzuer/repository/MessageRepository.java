package com.jfonzuer.repository;

import com.jfonzuer.entities.Conversation;
import com.jfonzuer.entities.Message;
import com.jfonzuer.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by pgm on 19/09/16.
 */
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("select m from Message m where m.conversation = :conversation" +
            " and ((m.userOne = :userOne and m.isDeletedByUserOne = false) or (m.userTwo = :userTwo and m.isDeletedByUserOne = false))" +
            " order by m.id desc")
    List<Message> findMessageByConversation(@Param("userOne") User userOne, @Param("userTwo") User userTwo, @Param("conversation") Conversation conversation, Pageable p);

    @Query("select m from Message m where m.conversation = :conversation" +
            " and ((m.userOne = :userOne and m.isDeletedByUserOne = false) or (m.userTwo = :userTwo and m.isDeletedByUserOne = false))" +
            " and m.id < :messageId" +
            " order by m.id desc")
    List<Message> findPreviousMessageByConversation(@Param("userOne") User userOne, @Param("userTwo") User userTwo, @Param("conversation") Conversation conversation, @Param("messageId") Long messageId, Pageable pageable);

    List<Message> findByConversation(Conversation conversation);
}
