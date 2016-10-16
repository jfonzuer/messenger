package com.jfonzuer.repository;

import com.jfonzuer.entities.Conversation;
import com.jfonzuer.entities.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by pgm on 19/09/16.
 */
public interface MessageRepository extends JpaRepository<Message, Long> {
    Page<Message> findByConversation(Conversation conversation, Pageable pageable);
    List<Message> findTop1ByOrderByIdDesc();
}
