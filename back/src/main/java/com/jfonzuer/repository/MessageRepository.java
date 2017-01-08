package com.jfonzuer.repository;

import com.jfonzuer.entities.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by pgm on 19/09/16.
 */
public interface MessageRepository extends JpaRepository<Message, Long> {
    Page<Message> findByConversationOrderByIdDesc(Conversation conversation, Pageable pageable);
    List<Message> findTop1ByOrderByIdDesc();
    List<Message> findByConversationAndIdGreaterThanOrderByIdDesc(Conversation conversation, Long id);



    @Transactional
    Long deleteByConversationAndIdLessThanEqual(Conversation conversation, Long id);
}
