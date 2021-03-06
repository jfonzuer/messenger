package com.jfonzuer.repository;

import com.jfonzuer.entities.Token;
import com.jfonzuer.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javax.persistence.Id;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by pgm on 23/10/16.
 */

@RepositoryRestResource
public interface TokenRepository extends JpaRepository<Token, Long> {
    List<Token> getAllByUser(User user);
    //List<Token> geByUserWhereExpiryDateBefore(User user, LocalDate date);
    Token getByTokenAndUser(String token, User user);
    Token getByToken(String token);
}
