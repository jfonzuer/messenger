package com.jfonzuer.websocket;

import com.jfonzuer.dto.MessageDto;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
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

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        return new Greeting("Hello, " + message.getName() + "!");
    }
}
