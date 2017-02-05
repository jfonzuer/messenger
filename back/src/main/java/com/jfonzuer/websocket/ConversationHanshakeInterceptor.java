package com.jfonzuer.websocket;

import com.jfonzuer.entities.Conversation;
import com.jfonzuer.entities.User;
import com.jfonzuer.repository.UserRepository;
import com.jfonzuer.security.JwtTokenUtil;
import com.jfonzuer.service.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by pgm on 31/01/17.
 */

public class ConversationHanshakeInterceptor implements HandshakeInterceptor {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConversationService conversationService;

    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
        ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) serverHttpRequest;
        HttpServletRequest request = servletRequest.getServletRequest();

        String authToken = request.getParameter("token");
        String paramId = request.getParameter("id");
        System.out.println("jwtTokenUtil = " + jwtTokenUtil);
        System.out.println("authToken = " + authToken);
        System.out.println("paramId = " + paramId);

        boolean connection = false;

        if (authToken != null && paramId != null) {

            String username = jwtTokenUtil.
                    getUsernameFromToken(authToken);
            User userOne = userRepository.findByEmail(username);
            Long userTwoId = Long.parseLong(paramId);

            Conversation conversation = conversationService.getConversationByIdAndUser(userTwoId, userOne);

            if (conversation != null) {
                map.put("sender", userOne);
                map.put("conversation", conversation);
                connection = true;
            }
        }
        return connection;
    }

    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {
    }
}