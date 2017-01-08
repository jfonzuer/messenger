package com.jfonzuer.repository;

import com.jfonzuer.entities.Conversation;
import com.jfonzuer.entities.Localization;
import com.jfonzuer.entities.User;
import com.jfonzuer.entities.UserType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.method.P;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by pgm on 19/09/16.
 */
public interface ConversationRepository extends JpaRepository<Conversation, Long> {

    Page<Conversation> findAllByUserOneOrUserTwo(User userOne, User userTwo, Pageable p);
    Page<Conversation> findAllByUserOneOrUserTwoOrderByLastModifiedDesc(User userOne, User userTwo, Pageable p);


    @Query("select c from Conversation c where (c.userOne = :user and c.lastMessageId > c.userOneCursor) or (c.userTwo = :user and c.lastMessageId > c.userTwoCursor)")
    Page<Conversation> getAllConversations(@Param("user") User user, Pageable p);

    @Query("select c from Conversation c where (c.userOne = :userOne and c.lastMessageId > c.userOneCursor) or (c.userTwo = :userTwo and c.lastMessageId > c.userTwoCursor)")
    List<Conversation> getConversation(@Param("userOne") User userOne, @Param("userTwo") User userTwo);

    List<Conversation> findTop1ByIdAndUserOneOrUserTwo(Long id, User userOne, User userTwo);
    Conversation findByUserOneAndUserTwoOrUserTwoAndUserOne(User userOne, User userTwo, User userOne1, User userTwo1);

    Long countByUserOneAndIsReadByUserOne(User userOne, Boolean isReadByUserOne);
    Long countByUserTwoAndIsReadByUserTwo(User userTwo, Boolean isReadByUserTwo);
}
