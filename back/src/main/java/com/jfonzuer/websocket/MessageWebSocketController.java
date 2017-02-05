package com.jfonzuer.websocket;

import com.jfonzuer.dto.MessageDto;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.security.Principal;

/**
 * Created by pgm on 31/01/17.
 */
@Controller
public class MessageWebSocketController {

    /*
    @MessageMapping("/conversation/{id}")
    @SendTo("conversation/{id}")
    public MessageDto addMessage(@Payload MessageDto dto, Principal user) {
        return  dto;
    }
    */

    @MessageMapping("/hello/{id}")
    @SendTo("/topic/greetings/{id}")
    public Greeting greeting(@DestinationVariable String id, HelloMessage message, SimpMessageHeaderAccessor headerAccessor) throws Exception {
        //System.out.println("messageHeaders = " + messageHeaders.get("ok"));
        System.out.println("headerAccessor.getSessionAttributes().get(\"handshake\"); = " + headerAccessor.getSessionAttributes().get("handshake"));
        return new Greeting("Hello, " + message.getName() + "!");
    }
}
