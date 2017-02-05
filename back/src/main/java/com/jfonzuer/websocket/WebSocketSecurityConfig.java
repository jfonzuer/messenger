package com.jfonzuer.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

import static org.springframework.messaging.simp.SimpMessageType.MESSAGE;
import static org.springframework.messaging.simp.SimpMessageType.SUBSCRIBE;

/**
 * Created by pgm on 31/01/17.
 */

/*
@Configuration
public class WebSocketSecurityConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {
*/
    /*
    @Override
    protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
        messages
                .nullDestMatcher().authenticated() // Any message without a destination (i.e. anything other that Message type of MESSAGE or SUBSCRIBE) will require the user to be authenticated
                .simpSubscribeDestMatchers("/user/queue/errors").permitAll() // Anyone can subscribe to /user/queue/errors
                .simpDestMatchers("/app/**").hasRole("USER") // Any message that has a destination starting with "/app/" will be require the user to have the role ROLE_USER
                .simpSubscribeDestMatchers("/topic/greetings/*").hasRole("USER") // Any message that starts with "/topic/greetings/" that is of type SUBSCRIBE will require ROLE_USER
                .simpTypeMatchers(MESSAGE, SUBSCRIBE).denyAll() // Any other message of type MESSAGE or SUBSCRIBE is rejected. Due to 6 we do not need this step, but it illustrates how one can match on specific message types.
                .anyMessage().denyAll(); // Any other Message is rejected. This is a good idea to ensure that you do not miss any messages.
    }

    **/
    /*
    @Override
    protected boolean sameOriginDisabled() {
        return true;
    }

}
*/
