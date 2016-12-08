package com.jfonzuer.repository;

import com.jfonzuer.entities.Conversation;
import com.jfonzuer.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.method.P;

import java.util.List;

/**
 * Created by pgm on 19/09/16.
 */
public interface ConversationRepository extends JpaRepository<Conversation, Long> {

    Page<Conversation> findAllByUserOneOrUserTwo(User userOne, User userTwo, Pageable p);
    Page<Conversation> findAllByUserOneOrUserTwoOrderByLastModifiedDesc(User userOne, User userTwo, Pageable p);

    Page<Conversation> findAllByUserOneOrUserTwoAndIsDeletedByUserOneOrderByLastModifiedDesc(User userOne, User userTwo, Boolean isDeletedByUserOne, Pageable p);
    Page<Conversation> findAllByUserOneOrUserTwoAndIsDeletedByUserTwoOrderByLastModifiedDesc(User userOne, User userTwo, Boolean isDeletedByUserTwo, Pageable p);


    Page<Conversation> findAllByUserOneAndIsDeletedByUserOneOrUserTwoAndIsDeletedByUserTwoOrderByLastModifiedDesc(User userOne, Boolean isDeletedByUserOne, User userTwo, Boolean isDeletedByUserTwo, Pageable p);

    List<Conversation> findTop1ByIdAndUserOneOrUserTwo(Long id, User userOne, User userTwo);
    Conversation findByUserOneAndUserTwoOrUserTwoAndUserOne(User userOne, User userTwo, User userOne1, User userTwo1);

    /*@Query("select c from c where u.userOne = :userOne ")
    Conversation getConversationByUserOneAndUserTwo(@Param("userOne") User userOne, @Param("userTwo") User userTwo);*/

    //Long countByUserOneAndIsReadByUserOneOrUserTwoIsReadByUserTwo(User userOne, Boolean isReadByUserOne, User userTwo, Boolean isReadByUserTwo);
    Long countByUserOneAndIsReadByUserOne(User userOne, Boolean isReadByUserOne);
    Long countByUserTwoAndIsReadByUserTwo(User userTwo, Boolean isReadByUserTwo);
}
