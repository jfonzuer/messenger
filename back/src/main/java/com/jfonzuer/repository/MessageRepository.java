package com.jfonzuer.repository;

import com.jfonzuer.entities.Conversation;
import com.jfonzuer.entities.Message;
import com.jfonzuer.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by pgm on 19/09/16.
 */
public interface MessageRepository extends JpaRepository<Message, Long> {
    Page<Message> findByConversationOrderByIdDesc(Conversation conversation, Pageable pageable);
    Page<Message> findByConversationAndUserOneAndIsDeletedByUserOneOrConversationAndUserTwoAndIsDeletedByUserTwoOrderByIdAsc(Conversation conversation1, User userOne1, Boolean isDeletedByUserOne, Conversation conversation2, User userTwo, Boolean isDeletedByUserTwo, Pageable p);

    List<Message> findTop1ByOrderByIdDesc();
    List<Message> findByConversation(Conversation conversation);
    List<Message> findByConversationAndIdGreaterThanOrderByIdDesc(Conversation conversation, Long id);
}
