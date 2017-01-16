package com.jfonzuer.service;

import com.jfonzuer.repository.UserRepository;
import com.jfonzuer.security.JwtTokenUtil;
import com.jfonzuer.security.JwtUser;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.jfonzuer.entities.User;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by pgm on 17/10/16.
 */
@Service
public class UserService {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserRepository userRepository;

    public User getUserFromToken(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        return this.userRepository.findByEmail(username);
    }

    public Set<User> blockUser(User currentUser, User userToBlock) {
        Set<User> blockedUsers = currentUser.getBlockedUsers() == null ? new HashSet<>() : currentUser.getBlockedUsers();
        blockedUsers.add(userToBlock);
        currentUser.setBlockedUsers(blockedUsers);
        userRepository.save(currentUser);
        return blockedUsers;
    }

    public Set<User> unblockUser(User currentUser, User userToUnblock) {
        Set<User> blockedUsers = currentUser.getBlockedUsers() == null ? new HashSet<>() : currentUser.getBlockedUsers();
        blockedUsers.remove(userToUnblock);
        currentUser.setBlockedUsers(blockedUsers);
        userRepository.save(currentUser);
        return blockedUsers;
    }

    public void throwExceptionIfBlocked(User sender, User target) {
        if (target.getBlockedUsers().contains(sender)) {
            throw new IllegalArgumentException("Vous ne pouvez pas envoyer de message à cet utilisateur");
        }
    }

}
