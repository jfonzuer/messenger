package com.jfonzuer.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

import java.security.Principal;

/**
 * Created by pgm on 31/01/17.
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

    /*
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/websocket");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
        stompEndpointRegistry.addEndpoint("/conversation").withSockJS().setInterceptors(new ConversationHanshakeInterceptor());
    }
    */

    /*
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.setInterceptors(new ChannelInterceptorAdapter() {

            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {

                StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

                if (StompCommand.CONNECT.equals(accessor.getCommand())) {
                    System.out.println("accessor.getHeader('Authorization') = " + accessor.getHeader("Authorization"));
                    System.out.println("accessor.getHeader('test') = " + accessor.getHeader("test"));
                    System.out.println("accessor.getDestination() = " + accessor.getDestination());

                    /*
                    Principal user = accessor.getHeader()
                    Principal user = ... ; // access authentication header(s)
                    accessor.setUser(user);
                    *//*
                }
                return message;
            }
        });
    }
    */
    @Bean
    public ConversationHanshakeInterceptor conversationHanshakeInterceptorBean() {
        return new ConversationHanshakeInterceptor();
    }
    @Bean
    public ConversationSubscribeInterceptor conversationSubscribeInterceptorBean() {
        return new ConversationSubscribeInterceptor();
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.setInterceptors(conversationSubscribeInterceptorBean());
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/hello").setAllowedOrigins("*").withSockJS().setInterceptors(conversationHanshakeInterceptorBean());
    }
}
