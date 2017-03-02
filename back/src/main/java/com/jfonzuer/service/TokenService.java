package com.jfonzuer.service;

import com.jfonzuer.entities.Token;
import com.jfonzuer.entities.User;
import com.jfonzuer.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Created by pgm on 12/03/17.
 */

@Service
public class TokenService {

    private final TokenRepository tokenRepository;

    @Autowired
    public TokenService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public void save(User user, String token) {
        // on supprime les tokens déjà créés pour cet utilisateur
        tokenRepository.getAllByUser(user).stream().forEach(t -> tokenRepository.delete(t));
        tokenRepository.save(new Token(token, user, LocalDate.now().plusDays(1L)));
    }
}
